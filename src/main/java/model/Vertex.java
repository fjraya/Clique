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

    public void addNeighbour(String name)
    {
        this.neighbours.add(name);
    }

    public String getName() {
        return name;
    }

    public String getNeighbours()
    {
        StringBuilder result =  new StringBuilder("");
        for (String nie: neighbours) {
            result.append(nie + ",");
        }
        return result.toString();
    }

}
