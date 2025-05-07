import java.util.Queue;

public class TestShuntingYard extends TestRunner {

   public void testTwoOps() {
      Queue<Token> infix = Token.toTokenQueue("1+2x3");
      Queue<Token> postfix = ShuntingYard.createPostFix(infix);
      String expected = "[1.0, 2.0, 3.0, x, +]";

      assertEquals(expected, postfix.toString());
   }

   public void testSimple() {
      Queue<Token> infix = Token.toTokenQueue("5+7");
      Queue<Token> postfix = ShuntingYard.createPostFix(infix);
      String expected = "[5.0, 7.0, +]";

      assertEquals(expected, postfix.toString());
   }
   
   public void testSequence() {
      Queue<Token> infix = Token.toTokenQueue("5+7+2+1-3+2");
      Queue<Token> postfix = ShuntingYard.createPostFix(infix);
      String expected = "[5.0, 7.0, +, 2.0, +, 1.0, +, 3.0, -, 2.0, +]";

      assertEquals(expected, postfix.toString());
   }

   public void testThreeOps() {
      Queue<Token> infix = Token.toTokenQueue("1+2x3-4");
      Queue<Token> postfix = ShuntingYard.createPostFix(infix);
      String expected = "[1.0, 2.0, 3.0, x, +, 4.0, -]";

      assertEquals(expected, postfix.toString());
   }

   public void testOneParenDeep() {
      Queue<Token> infix = Token.toTokenQueue("(1+2)x3/(1+2)");
      Queue<Token> postfix = ShuntingYard.createPostFix(infix);
      String expected = "[1.0, 2.0, +, 3.0, x, 1.0, 2.0, +, /]";

      assertEquals(expected, postfix.toString());
   }

   public void testTwoParenDeep() {
      Queue<Token> infix = Token.toTokenQueue("((1+2)x3+1)x2");
      Queue<Token> postfix = ShuntingYard.createPostFix(infix);
      String expected = "[1.0, 2.0, +, 3.0, x, 1.0, +, 2.0, x]";

      assertEquals(expected, postfix.toString());
   }

   public void testWithExtraParens() {
      Queue<Token> infix = Token.toTokenQueue("(((((1+2)x3-1))))x2");
      Queue<Token> postfix = ShuntingYard.createPostFix(infix);
      String expected = "[1.0, 2.0, +, 3.0, x, 1.0, -, 2.0, x]";

      assertEquals(expected, postfix.toString());
   }

   public void testExponent() {
      Queue<Token> infix = Token.toTokenQueue("(1+2)^3-4^5x3");
      Queue<Token> postfix = ShuntingYard.createPostFix(infix);
      String expected = "[1.0, 2.0, +, 3.0, ^, 4.0, 5.0, ^, 3.0, x, -]";

      assertEquals(expected, postfix.toString());
   }
   
   public void testRightAssociative() {
      Queue<Token> infix = Token.toTokenQueue("2^3^2");
      Queue<Token> postfix = ShuntingYard.createPostFix(infix);
      String expected = "[2.0, 3.0, 2.0, ^, ^]";

      assertEquals(expected, postfix.toString());
   }
   
   public void testSqrt() {
      Queue<Token> infix = Token.toTokenQueue("√√4");
      Queue<Token> postfix = ShuntingYard.createPostFix(infix);
      String expected = "[4.0, √, √]";

      assertEquals(expected, postfix.toString());
   }
   
   public void testSqrtOfLog() {
      // √ ln 4
      Queue<Token> infix = Token.toTokenQueue("√");
      infix.add(new Token("ln"));
      infix.add(new Token(4));
      Queue<Token> postfix = ShuntingYard.createPostFix(infix);
      String expected = "[4.0, ln, √]";

      assertEquals(expected, postfix.toString());
   }

}