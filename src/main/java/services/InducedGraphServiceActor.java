package services;

import akka.actor.*;
import akka.dispatch.OnComplete;
import akka.japi.pf.DeciderBuilder;
import akka.routing.RoundRobinPool;
import akka.util.Timeout;
import dal.FriendsRepositoryActor;
import dal.QueryException;
import model.InducedGraph;
import model.Vertex;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.List;

import static akka.actor.SupervisorStrategy.*;
import static akka.pattern.Patterns.ask;


/**
 * Created by franciscoraya on 9/7/17.
 */
public class InducedGraphServiceActor extends AbstractActor {
    private Timeout timeout;
    private ActorSystem system;
    private ActorRef friendsRepositoryPoolActor;

    public InducedGraphServiceActor() {
        system = ActorSystem.create("CliqueSystem");
        friendsRepositoryPoolActor = system.actorOf(new RoundRobinPool(20).withSupervisorStrategy(strategy).props(Props.create(FriendsRepositoryActor.class)), "friendsrepository");

        timeout = new Timeout(Duration.create(135000, "seconds"));
    }


    private static SupervisorStrategy strategy =
            new OneForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder.
                    match(QueryException.class, e -> resume()).
                    match(NullPointerException.class, e -> restart()).
                    match(IllegalArgumentException.class, e -> stop()).
                    matchAny(o -> resume()).build());

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, userHandle -> {
                    System.out.println("Received String message: " + userHandle);

                    InducedGraph graph = this.getInducedGraphByVertex(userHandle);
                    getSender().tell(graph, getSelf());
                })
                .matchAny(o -> System.out.println("received unknown message"))
                .build();
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
