package com.patrickfeltes.graphingprogram.AST;

import com.patrickfeltes.graphingprogram.Tokens.Token;
import com.patrickfeltes.graphingprogram.Tokens.TokenType;

import java.util.Map;

public class UnaryOperation extends Node {

    private Token operator;
    private Node child;

    public UnaryOperation(Token operator, Node child) {
        this.operator = operator;
        this.child = child;
    }

    @Override
    public double evaluate(Map<String, Double> variableMap) {
        if (operator.getType() == TokenType.PLUS) {
            return child.evaluate(variableMap);
        } else if (operator.getType() == TokenType.MINUS) {
            return -child.evaluate(variableMap);
        }

        System.out.println("Invalid binary operator: " + operator.getValue());
        System.exit(1);
        return Double.NaN;
    }
}