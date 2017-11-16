/**
 * The Tokenizer takes a String expression and splits it up into individual tokens for the parser
 * to use.
 */
public class Tokenizer {

    private static final char EMPTY_CHAR = '\u0000';
    private static final char SPACE = ' ';
    private String string;
    private int currentPosition;
    private char currentChar;

    public Tokenizer(String string) {
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
                return new Token(TokenType.DIV, Reserved.DIV);
            }

            if (currentChar == Reserved.LPAREN) {
                advance();
                return new Token(TokenType.LPAREN, Reserved.LPAREN);
            }

            if (currentChar == Reserved.RPAREN) {
                advance();
                return new Token(TokenType.RPAREN, Reserved.RPAREN);
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
    private void advance() {
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
    private void skipWhitespace() {
        while (currentChar != EMPTY_CHAR && currentChar == SPACE) {
            advance();
        }
    }

    private void error() {
        System.exit(1);
    }
}
