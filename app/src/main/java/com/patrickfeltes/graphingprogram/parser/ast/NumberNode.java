package com.patrickfeltes.graphingprogram.parser.ast;

import java.util.Map;

public class NumberNode extends Node {

    private double number;

    public NumberNode(double number) {
        this.number = number;
    }

    public double evaluate(Map<String, Double> variableMap) {
        return number;
    }

}
