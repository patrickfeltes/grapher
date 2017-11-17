package AST;

import Tokens.Token;
import Tokens.TokenType;

import java.util.Map;

public class BinaryOperation extends Node {

    private Token operator;
    private Node leftChild;
    private Node rightChild;

    public BinaryOperation(Token operator, Node leftChild, Node rightChild) {
        this.operator = operator;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    @Override
    public double evaluate(Map<String, Double> variableMap) {
        double leftVal = leftChild.evaluate(variableMap);
        double rightVal = rightChild.evaluate(variableMap);
        if (operator.getType() == TokenType.PLUS) {
            return leftVal + rightVal;
        } else if (operator.getType() == TokenType.MINUS) {
            return leftVal - rightVal;
        } else if (operator.getType() == TokenType.MUL) {
            return leftVal * rightVal;
        } else if (operator.getType() == TokenType.DIV) {
            return leftVal / rightVal;
        } else if (operator.getType() == TokenType.POW) {
            return Math.pow(leftVal, rightVal);
        }

        System.out.println("Invalid binary operator: " + operator.getValue());
        return 0;
    }

}
