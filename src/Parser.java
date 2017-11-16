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

    /**
     * Expression takes a generic expression and evaluates it. An expression is defined according to
     * the following grammar:
     * (PLUS|MINUS) term (PLUS|MINUS term)*
     * @return the value of the generic expression
     */
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

    /**
     * The term method evaluates a term, which is defined by the following grammar:
     * factor (MUL|DIV factor)*
     * @return the value of the term
     */
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

    /**
     * The factor method evaluates a factor, which is defined by the following grammar:
     * INTEGER
     * | LPAREN expression RPAREN
     * @return the value of the factor
     */
    public int factor() {
        if (currentToken.getType() == TokenType.LPAREN) {
            eat(TokenType.LPAREN);
            int value = expression();
            eat(TokenType.RPAREN);

            return value;
        } else {
            Token token = currentToken;
            eat(TokenType.INTEGER);

            return (int)token.getValue();
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
