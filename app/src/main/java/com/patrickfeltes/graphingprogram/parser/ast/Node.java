package com.patrickfeltes.graphingprogram.parser.ast;

import java.util.Map;

public abstract class Node {

    public abstract double evaluate(Map<String, Double> variableMap);

}
