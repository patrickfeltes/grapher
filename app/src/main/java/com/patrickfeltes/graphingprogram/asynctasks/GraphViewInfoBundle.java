package com.patrickfeltes.graphingprogram.asynctasks;

/**
 * GraphViewInfoBundle is used to bundle all necessary information about the graph view to
 * send to the PlotTask
 */
public class GraphViewInfoBundle {

    private double minX;
    private double maxX;
    private int partitions;
    private String equation;

    public GraphViewInfoBundle(double minX, double maxX, int partitions, String equation) {
        this.minX = minX;
        this.maxX = maxX;
        this.partitions = partitions;
        this.equation = equation;
    }

    public double getMinX() {
        return minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public int getPartitions() {
        return partitions;
    }

    public String getEquation() {
        return equation;
    }
}
