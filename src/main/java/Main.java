import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.util.Timeout;
import dal.FriendsRepository;
import dal.QueryException;
import dal.SocialNetWorkWrapperException;
import dal.TwitterSocialNetworkWrapper;
import model.InducedGraph;
import model.Vertex;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import services.InducedGraphService;

import java.util.List;

import static akka.pattern.Patterns.ask;

/**
 * Created by franciscoraya on 6/7/17.
 */
public class Main {
    public static void main (String [] args) throws Exception {
        InducedGraphService inducedGraphService = new InducedGraphService();
        InducedGraph graph = inducedGraphService.getInducedGraphByVertex("soyelrayan");
        List<Vertex> vertexs = graph.getVertexs();
        for (Vertex vertex: vertexs) {
            System.out.println(vertex.getName());
            System.out.println(vertex.getNeighbours());
            System.out.println("=============================");
        }
    }
}
