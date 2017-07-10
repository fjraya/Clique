import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.util.Timeout;
import dal.*;
import model.InducedGraph;
import model.Vertex;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import services.InducedGraphService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static akka.pattern.Patterns.ask;

/**
 * Created by franciscoraya on 6/7/17.
 */
public class Main {
    public static void main (String [] args) throws Exception {

        InducedGraphService inducedGraphService = new InducedGraphService();
        InducedGraph graph1 = inducedGraphService.getInducedGraphByVertex("soyelrayan");

        Vertex v = new Vertex("s01", new ArrayList<String>(Arrays.asList("s02", "s03", "s04", "s05", "s06")));
        Vertex v2 = new Vertex("s02", new ArrayList<String>(Arrays.asList("s01", "s03", "s04", "s05")));
        Vertex v3 = new Vertex("s03", new ArrayList<String>(Arrays.asList("s02", "s04", "s01", "s05")));
        Vertex v4 = new Vertex("s04", new ArrayList<String>(Arrays.asList("s02", "s03", "s01", "s05")));
        Vertex v5 = new Vertex("s05", new ArrayList<String>(Arrays.asList("s01", "s04", "s03", "s02")));
        Vertex v6 = new Vertex("s06", new ArrayList<String>(Arrays.asList("s01", "s04", "s03")));
        InducedGraph graph = new InducedGraph();
        graph.addVertex(v);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);
        graph.addVertex(v6);
        List<String> result = graph.getMaximalCliqueFromInducedGraph();
        output(result);

    }
    private static void output(List<String> clique) {
        StringBuilder stringBuilder = new StringBuilder();
        for(String node: clique) {
            stringBuilder.append(node + " ");
        }
        System.out.println(stringBuilder.toString());

    }
}
