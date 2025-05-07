import java.util.Queue;
import java.util.LinkedList;

public class Token {

   // special character: ×
   private final static String mathOperators = "+-/*%^x÷";

   private String operator;
   private Double number;

   public Token() {
      operator = null;
      number = null;
   }

   public Token(String unknown) {
      try {
         number = Double.parseDouble(unknown);
      } catch (NumberFormatException e) {
         operator = unknown;
      }
   }

   public Token(double num) {
      number = num;
   }

   public boolean isOpenParen() {
      return operator != null && operator.equals("(");
   }
   
   public boolean isCloseParen() {
      return operator != null && operator.equals(")");
   }
   
   public boolean isOperator() {
      // Solution update
      boolean isOp = operator != null && operator.length() == 1 && mathOperators.contains(operator);
      // System.out.printf(" %s isOperator=%b\n", this, isOp);
      return isOp;
   }

   public boolean isNumber() {
      return number != null;
   }

   public double getNumber() {
      return number.doubleValue();
   }

   public String getOperator() {
      return operator;
   }

   @Override
   public String toString() {
      return operator != null ? operator : "" + number;
   }

   @Override
   public boolean equals(Object other) {
      if (getClass() != other.getClass()) {
         return false;
      }
      String second = ((Token) other).toString();
      return this.toString().equals(second);
   }

   /**
    * Convert a queue of Tokens to an Expression in the same order that the tokens
    * appear, separated by spaces. Does not change the queue.
    *
    * For example: let queue be: 15, -, 2, + 10 returns: 15.0 - 2.0 + 10.0
    *
    * @param queue A Queue of Tokens to convert to an expression string
    * @return The expression formed from the Queue of Tokens.
    */
   public static String exprString(Queue<Token> queue) {
      String result = "";
      for (Token token : queue) {
         result += token + " ";
      }
      // Remove the last trailing space
      if (queue.size() > 0) {
         result = result.substring(0, result.length() - 1);
      }
      return result;
   }

   /*
    * Added as a part of the Solution
    */
   public boolean isFunction() {
      final String[] functions = { "log", "ln", "sqrt", "√" };
      if (operator == null) {
         return false;
      }
      for (String fun : functions) {
         if (fun.equalsIgnoreCase(operator)) {
            return true;
         }
      }
      return false;
   }

   public String getFunction() {
      return operator;
   }

   /**
    * Converts a simple expression represented as a String into a queue of Tokens.
    * The expression is of single digit numbers only and has no spaces.
    * 
    * @param expr The expression as a String
    * @return A Queue<Token> created from the expression
    */
   public static Queue<Token> toTokenQueue(String expr) {
      Queue<Token> q = new LinkedList<Token>();
      for (char ch : expr.toCharArray()) {
         Token token = new Token("" + ch);
         q.add(token);
      }
      return q;
   }

}
