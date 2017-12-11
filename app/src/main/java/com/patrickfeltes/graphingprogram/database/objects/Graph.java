package com.patrickfeltes.graphingprogram.database.objects;

import java.util.List;

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
