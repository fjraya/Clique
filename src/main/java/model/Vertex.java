package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by franciscoraya on 9/7/17.
 */
public class Vertex {
    private String name;
    private List<String> neighbours;

    public Vertex(String name, List<String> neighbours) {
        this.name = name;
        this.neighbours = neighbours;
    }



    public String getName() {
        return name;
    }



    public List<String> getNeighboursAsArray()
    {
        return this.neighbours;
    }

    public int getDegree() {
        return neighbours.size();
    }

}
