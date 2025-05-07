import java.util.LinkedList;
import java.util.Queue;

public class TestPostFix extends TestRunner {


    public void testSimpleExpression() {
        Queue<Token> tokens = new LinkedList<>();
        tokens.add(new Token("5"));
        tokens.add(new Token("7"));
        tokens.add(new Token("+"));
        double actual = PostFixEvaluator.evaluatePostFix(tokens);
        // 3rd argument is the delta (or tolerance) value
        assertEquals(12.0, actual, 0.001);
    }

    public void testTwo() {
        Queue<Token> tokens = Token.toTokenQueue("123*+");
        double actual = PostFixEvaluator.evaluatePostFix(tokens);
        assertEquals(7, actual, 0.001);
    }
        
    public void testMultiple() {
        // (1 + 2*3) * 4 - (3 + 2) = 23
        Queue<Token> tokens = Token.toTokenQueue("123*+4*32+-");
        double actual = PostFixEvaluator.evaluatePostFix(tokens);
        assertEquals(23, actual, 0.001);
    }

    public void testComplex() {
        // (((2 - 1) / 2 * 6 + (4 + 2 + 1)) / 2) ^ 2 % 3
        //   ((3 + 7) / 2)^2 % 3 = 25 % 3 = 1
        Queue<Token> tokens = Token.toTokenQueue("21-2/6*421+++2/2^3%");
        double actual = PostFixEvaluator.evaluatePostFix(tokens);
        assertEquals(1, actual, 0.001);
    }
}