import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void binaryAddition() throws Exception {
        assertEquals(8, new Parser(new Tokenizer("6 + 2")).parse().evaluate(null), 0.0);
    }

    @Test
    public void binarySubtraction() throws Exception {
        assertEquals(4, new Parser(new Tokenizer("6 - 2")).parse().evaluate(null), 0.0);
    }

    @Test
    public void binaryMultiplication() throws Exception {
        assertEquals(12, new Parser(new Tokenizer("6 * 2")).parse().evaluate(null), 0.0);
    }

    @Test
    public void binaryDivision() throws Exception {
        assertEquals(3, new Parser(new Tokenizer("6 / 2")).parse().evaluate(null), 0.0);
    }

    @Test
    public void simpleParentheses() throws Exception {
        assertEquals(32, new Parser(new Tokenizer("(6 + 2) * 4")).parse().evaluate(null), 0.0);
    }

    @Test
    public void allBasicOperations() throws Exception {
        assertEquals(9, new Parser(new Tokenizer("6 + 2 * 3 / 2")).parse().evaluate(null), 0.0);
    }

    @Test
    public void nestedParentheses() throws Exception {
        assertEquals(112, new Parser(new Tokenizer("((12 + 2) * 4) * 2")).parse().evaluate(null), 0.0);
    }

    @Test
    public void leadingNegative() throws Exception {
        assertEquals(-4, new Parser(new Tokenizer("-6 + 2")).parse().evaluate(null), 0.0);
    }

    @Test
    public void basicPower() throws Exception {
        assertEquals(32, new Parser(new Tokenizer("2 ^ 5")).parse().evaluate(null), 0.0);
        assertEquals(64, new Parser(new Tokenizer("2 ^ 6")).parse().evaluate(null), 0.0);
        assertEquals(27, new Parser(new Tokenizer("3 ^ 3")).parse().evaluate(null), 0.0);
    }

    @Test
    public void nestedPowers() throws Exception {
        assertEquals(16, new Parser(new Tokenizer("2 ^ (2 ^ 2)")).parse().evaluate(null), 0.0);
        assertEquals(65536, new Parser(new Tokenizer("2 ^ (2 ^ (2 ^ 2))")).parse().evaluate(null), 0.0);
    }

    @Test
    public void otherOperationsInPowers() throws Exception {
        assertEquals(16, new Parser(new Tokenizer("2 ^ (12 - 12 + 2 * 2 / 2 * 4 / 2)")).parse().evaluate(null), 0.0);
    }

    @Test
    public void simpleVariableTest() throws Exception {
        Map<String, Double> variableMap = new HashMap<>();
        variableMap.put("x", 1.0);
        assertEquals(1, new Parser(new Tokenizer("x")).parse().evaluate(variableMap), 0.0);
    }

    @Test
    public void operationsWithVariable() throws Exception {
        Map<String, Double> variableMap = new HashMap<>();
        variableMap.put("x", 2.0);
        assertEquals(11, new Parser(new Tokenizer("2 * x + x + 2 ^ x + x / 2")).parse().evaluate(variableMap), 0.0);
    }

    @Test
    public void doublesTest() throws Exception {
        assertEquals(1.0, new Parser(new Tokenizer("0.5 * 2")).parse().evaluate(null), 0.0);
        assertEquals(0.7, new Parser(new Tokenizer("0.2 + 0.7 - 0.2")).parse().evaluate(null), 0.0);
        assertEquals(2.0, new Parser(new Tokenizer("4 ^ 0.5")).parse().evaluate(null), 0.0);
    }

    @Test
    public void testIrrationals() throws Exception {
        assertEquals(Math.PI, new Parser(new Tokenizer("pi")).parse().evaluate(null), 0.0);
        assertEquals(Math.E, new Parser(new Tokenizer("e")).parse().evaluate(null), 0.0);
    }

    @Test
    public void testTrigFunctions() throws Exception {
        assertEquals(Math.cos(Math.PI / 2), new Parser(new Tokenizer("cos(pi/2)")).parse().evaluate(null), 0.0);
        assertEquals(Math.cos(1), new Parser(new Tokenizer("cos(1)")).parse().evaluate(null), 0.0);
        assertEquals(Math.sin(Math.PI / 2), new Parser(new Tokenizer("sin(pi/2)")).parse().evaluate(null), 0.0);
        assertEquals(Math.sin(1), new Parser(new Tokenizer("sin(1)")).parse().evaluate(null), 0.0);
        assertEquals(Math.tan(Math.PI), new Parser(new Tokenizer("tan(pi)")).parse().evaluate(null), 0.0);
        assertEquals(Math.tan(1), new Parser(new Tokenizer("tan(1)")).parse().evaluate(null), 0.0);
        assertEquals(1 / Math.cos(Math.PI), new Parser(new Tokenizer("sec(pi)")).parse().evaluate(null), 0.0);
        assertEquals(1 / Math.cos(1), new Parser(new Tokenizer("sec(1)")).parse().evaluate(null), 0.0);
        assertEquals(1 / Math.sin(Math.PI / 2), new Parser(new Tokenizer("csc(pi / 2)")).parse().evaluate(null), 0.0);
        assertEquals(1 / Math.sin(1), new Parser(new Tokenizer("csc(1)")).parse().evaluate(null), 0.0);
        assertEquals(1 / Math.tan(Math.PI), new Parser(new Tokenizer("cot(pi)")).parse().evaluate(null), 0.0);
        assertEquals(1 / Math.tan(1), new Parser(new Tokenizer("cot(1)")).parse().evaluate(null), 0.0);
    }

    @Test
    public void testLog() throws Exception {
        assertEquals(Double.NaN, new Parser(new Tokenizer("log(-1)")).parse().evaluate(null), 0.0);
        assertEquals(1.0, new Parser(new Tokenizer("log(e)")).parse().evaluate(null), 0.0);
        assertEquals(Math.log(1), new Parser(new Tokenizer("log(1)")).parse().evaluate(null), 0.0);
    }

    @Test
    public void testSqrt() throws Exception {
        assertEquals(2.0, new Parser(new Tokenizer("sqrt(4.0)")).parse().evaluate(null), 0.0);
        assertEquals(Math.sqrt(2.3), new Parser(new Tokenizer("sqrt(2.3)")).parse().evaluate(null), 0.0);
    }
}