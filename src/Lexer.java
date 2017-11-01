/**
 * The Lexer takes a String expression and breaks it up into tokens. Commonly known as a tokenizer
 */
public class Lexer {

    private String string;
    private int currentPosition;
    private Token currentToken;

    public Lexer(String string) {
        this.string = string;
        this.currentPosition = 0;
    }

    /**
     * Grabs the next token from the input String
     * @return The Token object of the next token
     * @throws Exception if there is a syntax error in the input String
     */
    public Token getNextToken() {
        // if we are past the end of the string, return an EOL token
        if (currentPosition >= string.length()) {
            return new Token(TokenType.EOL, null);
        }

        char currentChar = string.charAt(currentPosition);

        if (currentChar == Reserved.PLUS) {
            currentPosition++;
            return new Token(TokenType.PLUS, Reserved.PLUS);
        } else if (currentChar == Reserved.MINUS) {
            currentPosition++;
            return new Token(TokenType.MINUS, Reserved.MINUS);
        } else if (currentChar == Reserved.MUL) {
            currentPosition++;
            return new Token(TokenType.MUL, Reserved.MUL);
        } else if (currentChar == Reserved.DIV) {
            currentPosition++;
            return new Token(TokenType.DIV, Reserved.MUL);
        } else if (Character.isDigit(currentChar)) {
            currentPosition++;
            return new Token(TokenType.INTEGER, Character.getNumericValue(currentChar));
        }

        error();
        return null;
    }

    public int expression() {
        this.currentToken = this.getNextToken();

        Token left = this.currentToken;
        this.eat(TokenType.INTEGER);

        Token operator = this.currentToken;
        this.eat(TokenType.PLUS);

        Token right = this.currentToken;
        this.eat(TokenType.INTEGER);

        return (int)left.getValue() + (int)right.getValue();
    }

    /**
     * Eat compares the current token with a given TokenType. If they match, set the current token
     * to the next token, else error out of the program
     * @param type The expected TokenType of the current token
     */
    public void eat(TokenType type) {
        if (this.currentToken.getType() == type) {
            this.currentToken = this.getNextToken();
        } else {
            error();
        }
    }

    public void error() {
        System.exit(1);
    }
}
