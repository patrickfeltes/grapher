package com.patrickfeltes.graphingprogram.Tokens;

/**
 * The Tokens.Token class represents the "objects" that make up various expressions. Lexer will break up
 * an input String into various Tokens of types defined in Tokens.TokenType
 */
public class Token {

    private TokenType type;
    private Object value;

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
