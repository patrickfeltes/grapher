import AST.*;
import Tokens.Token;
import Tokens.TokenType;

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

    public Node parse() {
        Node node = expression();

        if (currentToken.getType() != TokenType.EOL) {
            System.exit(1);
        }

        return node;
    }

    /**
     * Expression takes a generic expression and converts it to a syntax tree.
     * An expression is defined according to the following grammar:
     * expression: (PLUS|MINUS) term (PLUS|MINUS term)*
     * @return the abstract syntax tree of the expression
     */
    private Node expression() {
        Node expressionRootNode;

        Token token = currentToken;
        if (currentToken.getType() == TokenType.PLUS) {
            eat(TokenType.PLUS);
            expressionRootNode = new UnaryOperation(token, term());
        } else if (currentToken.getType() == TokenType.MINUS) {
            eat(TokenType.MINUS);
            expressionRootNode = new UnaryOperation(token, term());
        } else {
            expressionRootNode = term();
        }

        while (currentToken.getType() == TokenType.PLUS || currentToken.getType() == TokenType.MINUS) {
            Token token1 = currentToken;
            if (currentToken.getType() == TokenType.PLUS) {
                eat(TokenType.PLUS);
                expressionRootNode = new BinaryOperation(token1, expressionRootNode, term());
            } else {
                eat(TokenType.MINUS);
                expressionRootNode = new BinaryOperation(token1, expressionRootNode, term());
            }
        }

        return expressionRootNode;
    }

    /**
     * The term method creates a syntax tree that represents the term.
     * A term is defined by the following grammar:
     * term: factor (MUL|DIV factor)*
     * @return the syntax tree representation of a term
     */
    private Node term() {
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
     * The factor method creates a syntax tree for a factor.
     * A factor is defined by the following grammar:
     * factor: powerPart (POW powerPart)*
     * @return
     */
    private Node factor() {
        Node powerRootNode = powerPart();

        while (currentToken.getType() == TokenType.POW) {
            Token operator = currentToken;

            eat(TokenType.POW);
            powerRootNode = new BinaryOperation(operator, powerRootNode, powerPart());
        }

        return powerRootNode;
    }

    /**
     * The powerPart method creates a syntax tree representing a powerPart.
     * A powerPart is defined by the following grammar:
     * powerPart:
     * PLUS powerPart
     * | MINUS powerPart
     * | INTEGER
     * | VAR
     * | FUNC LPAREN expression RPAREN
     * | LPAREN expression RPAREN
     * @return the syntax tree representing a powerPart
     */
    private Node powerPart() {
        if (currentToken.getType() == TokenType.LPAREN) {
            eat(TokenType.LPAREN);
            Node node = expression();
            eat(TokenType.RPAREN);
            return node;
        } else if (currentToken.getType() == TokenType.VAR) {
            Token token = currentToken;
            eat(TokenType.VAR);

            return new VariableNode((String)token.getValue());
        } else if (currentToken.getType() == TokenType.FUNC) {
            Token token = currentToken;
            eat(TokenType.FUNC);
            eat(TokenType.LPAREN);
            Node inside = expression();
            eat(TokenType.RPAREN);
            return new FunctionNode((String)token.getValue(), inside);
        } else if (currentToken.getType() == TokenType.PLUS) {
            Token token = currentToken;
            eat(TokenType.PLUS);
            return new UnaryOperation(token, powerPart());
        } else if (currentToken.getType() == TokenType.MINUS) {
            Token token = currentToken;
            eat(TokenType.MINUS);
            return new UnaryOperation(token, powerPart());
        } else {
            Token token = currentToken;
            eat(TokenType.DOUBLE);

            return new NumberNode((double)token.getValue());
        }
    }

    /**
     * Eat compares the current token with a given Tokens.TokenType. If they match, set the current token
     * to the next token, else error out of the program
     * @param type The expected Tokens.TokenType of the current token
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
