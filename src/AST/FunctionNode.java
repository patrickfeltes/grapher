package AST;

import Tokens.Reserved;

import java.util.Map;

import static java.lang.Double.NaN;

public class FunctionNode extends Node {

    private String function;
    private Node inside;

    public FunctionNode(String function, Node inside) {
        this.function = function;
        this.inside = inside;
    }

    public double evaluate(Map<String, Double> variableMap) {
        if (this.function.equalsIgnoreCase(Reserved.COS)) {
            return Math.cos(inside.evaluate(variableMap));
        } else if (this.function.equalsIgnoreCase(Reserved.SIN)) {
            return Math.sin(inside.evaluate(variableMap));
        } else if (this.function.equalsIgnoreCase(Reserved.TAN)) {
            return Math.tan(inside.evaluate(variableMap));
        } else if (this.function.equalsIgnoreCase(Reserved.CSC)) {
            return 1.0 / Math.sin(inside.evaluate(variableMap));
        } else if (this.function.equalsIgnoreCase(Reserved.SEC)) {
            return 1.0 / Math.cos(inside.evaluate(variableMap));
        } else if (this.function.equalsIgnoreCase(Reserved.COT)) {
            return 1.0 / Math.tan(inside.evaluate(variableMap));
        } else if (this.function.equalsIgnoreCase(Reserved.LOG)) {
            return Math.log(inside.evaluate(variableMap));
        } else if (this.function.equalsIgnoreCase(Reserved.SQRT)) {
            return Math.sqrt(inside.evaluate(variableMap));
        }

        System.out.println("Invalid function");
        System.exit(1);
        return NaN;
    }

}
