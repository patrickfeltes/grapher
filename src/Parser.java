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
        int value = 0;

        if (currentToken.getType() == TokenType.PLUS) {
            eat(TokenType.PLUS);
            value += term();
        } else if (currentToken.getType() == TokenType.MINUS) {
            eat(TokenType.MINUS);
            value -= term();
        } else {
            value = term();
        }

        while (currentToken.getType() == TokenType.PLUS || currentToken.getType() == TokenType.MINUS) {
            if (currentToken.getType() == TokenType.PLUS) {
                eat(TokenType.PLUS);
                value += term();
            } else {
                eat(TokenType.MINUS);
                value -= term();
            }
        }

        return value;
    }

    public int term() {
        int value = factor();

        while (currentToken.getType() == TokenType.MUL || currentToken.getType() == TokenType.DIV) {
            Token operator = currentToken;

            if (operator.getType() == TokenType.MUL) {
                eat(TokenType.MUL);
                value *= factor();
            } else {
                eat(TokenType.DIV);
                value /= factor();
            }
        }

        return value;
    }

    public int factor() {
        if (currentToken.getType() == TokenType.LPAREN) {
            eat(TokenType.LPAREN);
            int value = expression();
            eat(TokenType.RPAREN);

            return value;
        } else {
            int value = (int)currentToken.getValue();
            eat(TokenType.INTEGER);

            return value;
        }
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
