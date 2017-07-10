package mothers;

import model.InducedGraph;
import model.Vertex;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by franciscoraya on 10/7/17.
 */
public class InducedGraphMother {
    public static InducedGraph getTestInstance() {
        Vertex v = new Vertex("s01", new ArrayList<String>(Arrays.asList("s02", "s03", "s04", "s05", "s06", "s07")));
        Vertex v2 = new Vertex("s02", new ArrayList<String>(Arrays.asList("s01", "s03", "s04", "s05")));
        Vertex v3 = new Vertex("s03", new ArrayList<String>(Arrays.asList("s02", "s04", "s01", "s05")));
        Vertex v4 = new Vertex("s04", new ArrayList<String>(Arrays.asList("s02", "s03", "s01", "s05")));
        Vertex v5 = new Vertex("s05", new ArrayList<String>(Arrays.asList("s01", "s04", "s03", "s02")));
        Vertex v6 = new Vertex("s06", new ArrayList<String>(Arrays.asList("s01", "s04", "s03")));
        Vertex v7 = new Vertex("s07", new ArrayList<String>(Arrays.asList("s01", "s05", "s03")));

        InducedGraph graph = new InducedGraph();
        graph.addVertex(v);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);
        graph.addVertex(v6);
        return graph;
    }


    public static InducedGraph getOneNodeInstance() {
        Vertex v = new Vertex("s01", new ArrayList<>());
        InducedGraph graph = new InducedGraph();
        graph.addVertex(v);
        return graph;
    }




    public static InducedGraph getCliqueGraph() {
        Vertex v = new Vertex("s01", new ArrayList<>(Arrays.asList("s02", "s03", "s04")));
        Vertex v2 = new Vertex("s02", new ArrayList<>(Arrays.asList("s01", "s03", "s04")));
        Vertex v3 = new Vertex("s03", new ArrayList<>(Arrays.asList("s02", "s01", "s04")));
        Vertex v4 = new Vertex("s04", new ArrayList<>(Arrays.asList("s02", "s01", "s03")));
        InducedGraph graph = new InducedGraph();
        graph.addVertex(v);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        return graph;
    }

    public static InducedGraph getNoCliqueGraph() {
        InducedGraph graph = InducedGraphMother.getCliqueGraph();
        Vertex v = new Vertex("s05", new ArrayList<>(Arrays.asList("s02", "s04")));
        graph.addVertex(v);
        return graph;
    }
}
