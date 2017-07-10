package services;

import akka.actor.*;
import akka.dispatch.ExecutionContexts;
import akka.dispatch.Futures;
import akka.dispatch.OnComplete;
import akka.japi.pf.DeciderBuilder;
import akka.routing.RoundRobinPool;
import akka.util.Timeout;
import dal.FriendsRepositoryActor;
import dal.QueryException;
import model.InducedGraph;
import model.Vertex;
import scala.concurrent.Await;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static akka.actor.SupervisorStrategy.*;
import static akka.pattern.Patterns.ask;


/**
 * Created by franciscoraya on 9/7/17.
 */
public class InducedGraphService {
    private Timeout timeout;
    private ActorSystem system;
    private ActorRef friendsRepositoryPoolActor;



    public InducedGraphService() {
        system = ActorSystem.create("CliqueSystem");
        friendsRepositoryPoolActor = system.actorOf(new RoundRobinPool(20).props(Props.create(FriendsRepositoryActor.class)), "friendsrepository");

        timeout = new Timeout(Duration.create(135000, "seconds"));
    }

    public InducedGraph getInducedGraphByVertex(String vertex) throws Exception {
        InducedGraph graph = new InducedGraph();
        Future<Object> future = ask(friendsRepositoryPoolActor, vertex, timeout.duration().length());
        List<String> neighbours = (List<String>) Await.result(future, timeout.duration());
        graph.addVertex(new Vertex(vertex, neighbours));

        for (String neighbour: neighbours) {
            Future<Object> futureItem = ask(friendsRepositoryPoolActor, neighbour, timeout.duration().length());
            futureItem.onComplete(new OnComplete<Object>(){
                public void onComplete(Throwable t, Object result){
                    ((List<String>) result).retainAll(neighbours);
                    graph.addVertex(new Vertex(neighbour, (List<String>) result));
                }
            }, system.dispatcher());
        }
        return graph;

    }





}
