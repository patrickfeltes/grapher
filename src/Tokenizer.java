import Tokens.Reserved;
import Tokens.Token;
import Tokens.TokenType;

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
     * @return The Tokens.Token object of the next token
     */
    public Token getNextToken() {
        while (currentChar != EMPTY_CHAR) {
            if (currentChar == SPACE) {
                skipWhitespace();
            }

            if (Character.isAlphabetic(currentChar)) {
                String variableName = "";
                while (Character.isAlphabetic(currentChar)) {
                    variableName += currentChar;
                    advance();
                }
                String lowerCaseName = variableName.toLowerCase();
                if (lowerCaseName.equals(Reserved.SIN) || lowerCaseName.equals(Reserved.COS) ||
                        lowerCaseName.equals(Reserved.TAN) || lowerCaseName.equals(Reserved.CSC) ||
                        lowerCaseName.equals(Reserved.SEC) || lowerCaseName.equals(Reserved.COT) ||
                        lowerCaseName.equals(Reserved.LOG) ||
                        lowerCaseName.equalsIgnoreCase(Reserved.SQRT)) {
                    return new Token(TokenType.FUNC, lowerCaseName);
                } else if (lowerCaseName.equals(Reserved.PI)) {
                    return new Token(TokenType.DOUBLE, Math.PI);
                } else if (lowerCaseName.equals(Reserved.E)) {
                    return new Token(TokenType.DOUBLE, Math.E);
                }

                return new Token(TokenType.VAR, variableName);
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

            if (currentChar == Reserved.POW) {
                advance();
                return new Token(TokenType.POW, Reserved.POW);
            }

            if (Character.isDigit(currentChar)) {
                return getNumber();
            }

            error();
        }

        return new Token(TokenType.EOL, null);
    }

    private Token getNumber() {
        String value = "";
        boolean hasDecimal = false;
        while (Character.isDigit(currentChar)) {
            value += currentChar;
            advance();
            if (currentChar == '.') {
                if (hasDecimal) error(); // two decimal points in the number
                hasDecimal = true;
                value += currentChar;
                advance();
            }
        }

        return new Token(TokenType.DOUBLE, Double.parseDouble(value));
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
