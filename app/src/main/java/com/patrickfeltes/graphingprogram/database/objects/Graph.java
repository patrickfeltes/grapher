package com.patrickfeltes.graphingprogram.database.objects;

import java.util.List;

/**
 * Graph is used by Firebase to parse Graph objects
 */
public class Graph {

    public List<String> equations;
    public String name;

    public Graph() {

    }

    public Graph(List<String> equations, String name) {
        this.equations = equations;
        this.name = name;
    }

}
