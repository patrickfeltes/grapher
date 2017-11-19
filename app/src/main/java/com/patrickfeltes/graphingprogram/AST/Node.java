package com.patrickfeltes.graphingprogram.AST;

import java.util.Map;

public abstract class Node {

    public abstract double evaluate(Map<String, Double> variableMap);

}
