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
    public Node expression() {
        Node rootNode = null;

        Token token = currentToken;
        if (currentToken.getType() == TokenType.PLUS) {
            eat(TokenType.PLUS);
            rootNode = new UnaryOperation(token, term());
        } else if (currentToken.getType() == TokenType.MINUS) {
            eat(TokenType.MINUS);
            rootNode = new UnaryOperation(token, term());
        } else {
            rootNode = term();
        }

        while (currentToken.getType() == TokenType.PLUS || currentToken.getType() == TokenType.MINUS) {
            Token token1 = currentToken;
            if (currentToken.getType() == TokenType.PLUS) {
                eat(TokenType.PLUS);
                rootNode = new BinaryOperation(token1, rootNode, term());
            } else {
                eat(TokenType.MINUS);
                rootNode = new BinaryOperation(token1, rootNode, term());
            }
        }

        return rootNode;
    }

    /**
     * The term method evaluates a term, which is defined by the following grammar:
     * factor (MUL|DIV factor)*
     * @return the value of the term
     */
    public Node term() {
        Node termRootNode = factor();

        while (currentToken.getType() == TokenType.MUL || currentToken.getType() == TokenType.DIV) {
            Token operator = currentToken;

            if (operator.getType() == TokenType.MUL) {
                eat(TokenType.MUL);
                termRootNode = new BinaryOperation(operator, termRootNode, factor());
            } else {
                eat(TokenType.DIV);
                termRootNode = new BinaryOperation(operator, termRootNode, factor());
            }
        }

        return termRootNode;
    }

    /**
     * The factor method evaluates a factor, which is defined by the following grammar:
     * INTEGER
     * | LPAREN expression RPAREN
     * @return the value of the factor
     */
    public Node factor() {
        if (currentToken.getType() == TokenType.LPAREN) {
            eat(TokenType.LPAREN);
            Node node = expression();
            eat(TokenType.RPAREN);

            return node;
        } else {
            Token token = currentToken;
            eat(TokenType.INTEGER);

            return new NumberNode((int)token.getValue());
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
