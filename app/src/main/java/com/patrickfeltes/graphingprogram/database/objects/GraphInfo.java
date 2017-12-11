package com.patrickfeltes.graphingprogram.database.objects;

/**
 * GraphInfo is used by Firebase to parse graph information from the database
 */
public class GraphInfo {

    public String id;
    public String name;

    public GraphInfo() {

    }

    public GraphInfo(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
