/**
 * The Lexer takes a String expression and breaks it up into tokens. Commonly known as a tokenizer
 */
public class Lexer {

    private static final char EMPTY_CHAR = '\u0000';
    private static final char SPACE = ' ';
    private String string;
    private int currentPosition;
    private Token currentToken;
    private char currentChar;

    public Lexer(String string) {
        this.string = string;
        this.currentPosition = 0;
        this.currentChar = string.charAt(currentPosition);
    }

    /**
     * Grabs the next token from the input String
     * @return The Token object of the next token
     */
    public Token getNextToken() {
        while (currentChar != EMPTY_CHAR) {
            if (currentChar == SPACE) {
                skipWhitespace();
            }

            if (currentChar == Reserved.PLUS) {
                advance();
                return new Token(TokenType.PLUS, Reserved.PLUS);
            }

            if (currentChar == Reserved.MINUS) {
                advance();
                return new Token(TokenType.MINUS, Reserved.MINUS);
            }

            if (currentChar == Reserved.MUL) {
                advance();
                return new Token(TokenType.MUL, Reserved.MUL);
            }

            if (currentChar == Reserved.DIV) {
                advance();
                return new Token(TokenType.DIV, Reserved.MUL);
            }

            if (Character.isDigit(currentChar)) {
                int result = 0;
                while (Character.isDigit(currentChar)) {
                    result += Character.getNumericValue(currentChar);
                    result *= 10;
                    advance();
                }
                result /= 10;
                return new Token(TokenType.INTEGER, result);
            }

            error();
        }

        return new Token(TokenType.EOL, null);
    }

    /**
     * Method to advance to the next character in the String
     */
    public void advance() {
        currentPosition++;
        if (currentPosition >= string.length()) {
            currentChar = EMPTY_CHAR;
        } else {
            currentChar = string.charAt(currentPosition);
        }
    }

    /**
     * A method to continue advancing until whitespace is gone
     */
    public void skipWhitespace() {
        while (currentChar != EMPTY_CHAR && currentChar == SPACE) {
            advance();
        }
    }

    public int expression() {
        currentToken = getNextToken();

        Token left = currentToken;
        this.eat(TokenType.INTEGER);

        Token operator = currentToken;
        this.eat(TokenType.PLUS);

        Token right = currentToken;
        this.eat(TokenType.INTEGER);

        return (int)left.getValue() + (int)right.getValue();
    }

    /**
     * Eat compares the current token with a given TokenType. If they match, set the current token
     * to the next token, else error out of the program
     * @param type The expected TokenType of the current token
     */
    public void eat(TokenType type) {
        if (currentToken.getType() == type) {
            currentToken = getNextToken();
        } else {
            error();
        }
    }

    public void error() {
        System.exit(1);
    }
}
