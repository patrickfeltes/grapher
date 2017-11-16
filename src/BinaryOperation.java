
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
    public int evaluate() {
        if (operator.getType() == TokenType.PLUS) {
            return leftChild.evaluate() + rightChild.evaluate();
        } else if (operator.getType() == TokenType.MINUS) {
            return leftChild.evaluate() - rightChild.evaluate();
        } else if (operator.getType() == TokenType.MUL) {
            return leftChild.evaluate() * rightChild.evaluate();
        } else if (operator.getType() == TokenType.DIV) {
            return leftChild.evaluate() / rightChild.evaluate();
        } else if (operator.getType() == TokenType.POW) {
            return (int)Math.pow(leftChild.evaluate(), rightChild.evaluate());
        }

        System.out.println("Invalid binary operator: " + operator.getValue());
        return 0;
    }

}
