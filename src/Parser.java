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
        eat(TokenType.PLUS);
        Token right = currentToken;
        eat(TokenType.INTEGER);

        return (int)left.getValue() + (int)right.getValue();
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
