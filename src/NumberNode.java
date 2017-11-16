import java.util.Map;

public class NumberNode extends Node {

    private int number;

    public NumberNode(int number) {
        this.number = number;
    }

    public int evaluate(Map<String, Integer> variableMap) {
        return number;
    }

}
