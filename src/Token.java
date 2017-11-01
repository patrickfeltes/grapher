/**
 * The Token class represents the "objects" that make up various expressions. Lexer will break up
 * an input String into various Tokens of types defined in TokenType
 */
public class Token {

    protected TokenType type;
    protected Object value;

    public Token(TokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

}
