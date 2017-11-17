package AST;

import java.util.Map;

public class VariableNode extends Node {

    private String variableName;

    public VariableNode(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public double evaluate(Map<String, Double> variableMap) {
        if (variableMap.containsKey(variableName)) {
            return variableMap.get(variableName);
        } else {
            System.exit(1);
            return Double.NaN;
        }
    }

}
