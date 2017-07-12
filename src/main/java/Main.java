import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.OnComplete;
import akka.routing.RoundRobinPool;
import akka.util.Timeout;
import model.InducedGraph;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import services.InducedGraphServiceActor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static akka.pattern.Patterns.ask;

/**
 * Created by franciscoraya on 6/7/17.
 */
public class Main {
    private Timeout timeout;
    private ActorSystem system;
    private ActorRef inducedGraphServicePoolActor;

    public Main() {
        system = ActorSystem.create("CliqueSystem");
        timeout = new Timeout(Duration.create(135000, "seconds"));
    }


    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Número de argumentos incorrectos. Uso: Main <filename>");
            System.exit(-1);
        }
        String filename = args[0];
        Main main = new Main();
        try {
            main.execute(filename);
        } catch (FileNotFoundException e) {
            System.out.println("El fichero " + filename + "no existe");
            System.exit(-1);
        }
        catch(IOException ioe) {
            System.out.println("Error al leer el archivo");
        }
    }

    private void execute(String filename) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        inducedGraphServicePoolActor = system.actorOf(new RoundRobinPool(20).props(Props.create(InducedGraphServiceActor.class)), "inducedgraphactor");
        for (String userHandle = bufferedReader.readLine(); userHandle != null; userHandle = bufferedReader.readLine()) {
            Future<Object> futureItem = ask(inducedGraphServicePoolActor, userHandle, timeout.duration().length());
            futureItem.onComplete(new OnComplete<Object>(){
                public void onComplete(Throwable t, Object result){
                    if (result != null) {
                        List<String> clique = ((InducedGraph) result).getMaximalCliqueFromInducedGraph();
                        output(clique);
                    }
                }
            }, system.dispatcher());
        }

        bufferedReader.close();
        system.terminate();

    }


    private static void output(List<String> clique) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String node : clique) {
            stringBuilder.append(node + " ");
        }
        System.out.println(stringBuilder.toString());

    }
}
