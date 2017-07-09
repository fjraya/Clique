package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by franciscoraya on 9/7/17.
 */
public class InducedGraph {
    private List<Vertex> vertexs;


    public InducedGraph() {
        this.vertexs = new ArrayList<>();
    }

    public void addVertex(Vertex vertex) {
        this.vertexs.add(vertex);
    }

    public List<Vertex> getVertexs() {
        return this.vertexs;
    }


}
