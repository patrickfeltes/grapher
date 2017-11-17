package AST;

import java.util.Map;

import static java.lang.Double.NaN;
import static Tokens.Reserved.*;

public class FunctionNode extends Node {

    private String function;
    private Node inside;

    public FunctionNode(String function, Node inside) {
        this.function = function;
        this.inside = inside;
    }

    public double evaluate(Map<String, Double> variableMap) {
        if (this.function.equalsIgnoreCase(COS)) {
            return Math.cos(inside.evaluate(variableMap));
        } else if (this.function.equalsIgnoreCase(SIN)) {
            return Math.sin(inside.evaluate(variableMap));
        } else if (this.function.equalsIgnoreCase(TAN)) {
            return Math.tan(inside.evaluate(variableMap));
        } else if (this.function.equalsIgnoreCase(CSC)) {
            return 1.0 / Math.sin(inside.evaluate(variableMap));
        } else if (this.function.equalsIgnoreCase(SEC)) {
            return 1.0 / Math.cos(inside.evaluate(variableMap));
        } else if (this.function.equalsIgnoreCase(COT)) {
            return 1.0 / Math.tan(inside.evaluate(variableMap));
        } else if (this.function.equalsIgnoreCase(LN)) {
            return Math.log(inside.evaluate(variableMap));
        } else if (this.function.equalsIgnoreCase(SQRT)) {
            return Math.sqrt(inside.evaluate(variableMap));
        } else if (this.function.equalsIgnoreCase(SINH)) {
            return Math.sinh(inside.evaluate(variableMap));
        } else if (this.function.equalsIgnoreCase(COSH)) {
            return Math.cosh(inside.evaluate(variableMap));
        } else if (this.function.equalsIgnoreCase(TANH)) {
            return Math.tanh(inside.evaluate(variableMap));
        } else if (this.function.equalsIgnoreCase(SECH)) {
            double x= inside.evaluate(variableMap);
            return 2.0 / (Math.pow(Math.E, x) + Math.pow(Math.E, -x));
        } else if (this.function.equalsIgnoreCase(CSCH)) {
            double x = inside.evaluate(variableMap);
            return 2.0 / (Math.pow(Math.E, x) - Math.pow(Math.E, -x));
        } else if (this.function.equalsIgnoreCase(COTH)) {
            double x = inside.evaluate(variableMap);
            return (Math.pow(Math.E, 2 * x) + 1) / (Math.pow(Math.E, 2 * x) - 1);
        } else if (this.function.equalsIgnoreCase(ARCCOS)) {
            return Math.acos(inside.evaluate(variableMap));
        } else if (this.function.equalsIgnoreCase(ARCSIN)) {
            return Math.asin(inside.evaluate(variableMap));
        } else if (this.function.equalsIgnoreCase(ARCTAN)) {
            return Math.atan(inside.evaluate(variableMap));
        } else if (this.function.equalsIgnoreCase(ARCSEC)) {
            return Math.acos(1 / inside.evaluate(variableMap));
        } else if (this.function.equalsIgnoreCase(ARCCSC)) {
            return Math.asin(1 / inside.evaluate(variableMap));
        } else if (this.function.equalsIgnoreCase(ARCCOT)) {
            return Math.atan(1 / inside.evaluate(variableMap));
        }

        System.out.println("Invalid function");
        System.exit(1);
        return NaN;
    }

}
