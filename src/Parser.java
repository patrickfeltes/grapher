/**
 * The parser class handles the actual parsing of tokens into something meaningful that the program
 * can use.
 */
public class Parser {

    private Tokenizer tokenizer;
    private Token currentToken;

    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
        this.currentToken = tokenizer.getNextToken();
    }

    public int expression() {
        Token left = currentToken;
        eat(TokenType.INTEGER);
        Token operator = currentToken;

        if (operator.getType() == TokenType.PLUS) {
            eat(TokenType.PLUS);
        } else if (operator.getType() == TokenType.MINUS) {
            eat(TokenType.MINUS);
        } else if (operator.getType() == TokenType.MUL) {
            eat(TokenType.MUL);
        } else {
            eat(TokenType.DIV);
        }

        Token right = currentToken;
        eat(TokenType.INTEGER);

        int value;

        if (operator.getType() == TokenType.PLUS) {
            value = (int)left.getValue() + (int)right.getValue();
        } else if (operator.getType() == TokenType.MINUS) {
            value = (int)left.getValue() - (int)right.getValue();
        } else if (operator.getType() == TokenType.MUL) {
            value = (int)left.getValue() * (int)right.getValue();
        } else {
            value = (int)left.getValue() / (int)right.getValue();
        }

        return value;
    }

    /**
     * Eat compares the current token with a given TokenType. If they match, set the current token
     * to the next token, else error out of the program
     * @param type The expected TokenType of the current token
     */
    private void eat(TokenType type) {
        if (currentToken.getType() == type) {
            currentToken = tokenizer.getNextToken();
        } else {
            error();
        }
    }

    private void error() {
        System.exit(1);
    }

}
