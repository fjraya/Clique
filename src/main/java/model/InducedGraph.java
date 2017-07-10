package model;

import services.InducedGraphService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by franciscoraya on 9/7/17.
 */
public class InducedGraph {
    private HashMap<String, Vertex> vertexs;


    public InducedGraph() {
        this.vertexs = new HashMap<>();
    }

    public void addVertex(Vertex vertex) {
        this.vertexs.put(vertex.getName(), vertex);
    }



    public boolean isClique() {
        Set<String> vertexNames = vertexs.keySet();
        int minDegree = 99999;
        int maxDegree = -9999;
        int nNodes = 0;
        for(String vertexName: vertexNames) {
            int degree = vertexs.get(vertexName).getDegree();
            if (degree > maxDegree) maxDegree = degree;
            if (degree < minDegree) minDegree = degree;
            nNodes ++;
        }
        return (maxDegree == minDegree) && ((nNodes == maxDegree +1)||(nNodes == 1));
    }


    public Vertex getVertexWithMinimumDegree() {
        Set<String> vertexNames = vertexs.keySet();
        int minDegree = 99999;
        String minimumVertexName = "";
        for(String vertexName: vertexNames) {
            int degree = vertexs.get(vertexName).getDegree();
            if (degree < minDegree) {
                minDegree = degree;
                minimumVertexName = vertexName;
            }
        }
        return vertexs.get(minimumVertexName);
    }


    public List<String> getMaximalCliqueFromInducedGraph() {
        List<String> anchor = new ArrayList<>();
        List<String> cand = new ArrayList<>(vertexs.keySet());
        List<String> not = new ArrayList<>();
        List<List<String>> cliques = new ArrayList<>();
        enumerateClique(anchor, cand, not, cliques);
        int maximal = 0;
        List<String> maximalClique = new ArrayList<>();
        for (List<String> clique: cliques) {
            if (clique.size()  > maximal) {
                maximal = clique.size();
                maximalClique = clique;
            }
        }
        maximalClique.sort(String::compareToIgnoreCase);
        return maximalClique;
    }

    private void enumerateClique(List<String> anchor, List<String> cand, List<String> not, List<List<String>> cliques) {
        InducedGraph graph = this.fromVertexArray(cand);
        if (graph.isClique()) {
            anchor.addAll(cand);
            cliques.add(anchor);
        }
        else {
            while (!graph.isClique()) {
                List<String> newAnchor = new ArrayList<>(anchor);
                List<String> newCand = new ArrayList<>(cand);
                List<String> newNot = new ArrayList<>(not);
                Vertex v = this.getVertexWithMinimumDegree();
                newAnchor.add(v.getName());
                newCand.retainAll(v.getNeighboursAsArray());
                newNot.retainAll(v.getNeighboursAsArray());
                enumerateClique(newAnchor, newCand, newNot, cliques);
                cand.remove(v.getName());
                not.add(v.getName());
                graph = this.fromVertexArray(cand);
            }
            anchor.addAll(cand);
            cliques.add(anchor);
        }
    }

    private  InducedGraph fromVertexArray(List<String> vertexs) {
        InducedGraph graph = new InducedGraph();
        for (String vertex: vertexs) {
            Vertex node = this.getVertex(vertex);
            List<String> neighbours = new ArrayList<>(node.getNeighboursAsArray());
            neighbours.retainAll(vertexs);
            Vertex newVertex = new Vertex(vertex, neighbours);
            graph.addVertex(newVertex);
        }
        return graph;
    }

    private Vertex getVertex(String vertex) {
        return this.vertexs.get(vertex);
    }


}
