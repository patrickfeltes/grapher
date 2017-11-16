
public class UnaryOperation extends Node {

    private Token operator;
    private Node child;

    public UnaryOperation(Token operator, Node child) {
        this.operator = operator;
        this.child = child;
    }

    @Override
    public int evaluate() {
        if (operator.getType() == TokenType.PLUS) {
            return child.evaluate();
        } else if (operator.getType() == TokenType.MINUS) {
            return -child.evaluate();
        }

        System.out.println("Invalid binary operator: " + operator.getValue());
        return 0;
    }
}
