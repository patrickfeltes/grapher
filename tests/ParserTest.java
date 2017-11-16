import org.junit.Test;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void binaryAddition() throws Exception {
        assertEquals(8, new Parser(new Tokenizer("6 + 2")).expression().evaluate());
    }

    @Test
    public void binarySubtraction() throws Exception {
        assertEquals(4, new Parser(new Tokenizer("6 - 2")).expression().evaluate());
    }

    @Test
    public void binaryMultiplication() throws Exception {
        assertEquals(12, new Parser(new Tokenizer("6 * 2")).expression().evaluate());
    }

    @Test
    public void binaryDivision() throws Exception {
        assertEquals(3, new Parser(new Tokenizer("6 / 2")).expression().evaluate());
    }

    @Test
    public void simpleParentheses() throws Exception {
        assertEquals(32, new Parser(new Tokenizer("(6 + 2) * 4")).expression().evaluate());
    }

    @Test
    public void allBasicOperations() throws Exception {
        assertEquals(9, new Parser(new Tokenizer("6 + 2 * 3 / 2")).expression().evaluate());
    }

    @Test
    public void nestedParentheses() throws Exception {
        assertEquals(112, new Parser(new Tokenizer("((12 + 2) * 4) * 2")).expression().evaluate());
    }

    @Test
    public void leadingNegative() throws Exception {
        assertEquals(-4, new Parser(new Tokenizer("-6 + 2")).expression().evaluate());
    }
}