import java.util.Queue;
import java.util.Stack;

public class PostFixEvaluator {

    public static double evaluatePostFix(Queue<Token> tokens) {
        Stack<Double> stack = new Stack<>(); 
        // loop through all tokens in queue
        while (!tokens.isEmpty()) {
            // remove the next token
            Token token = tokens.poll(); 
            // if its a number, push onto stack
            if (token.isNumber()) {
                
                stack.push(token.getNumber()); 
            } 
            // if operator 
            else if (token.isOperator()) {
                // check to see at least two operands in the stack
                if (stack.size() < 2) throw new IllegalArgumentException("invalid postfix expression: not enough operands");
                double b = stack.pop();
                double a = stack.pop();

                // perform operation based on the operator
                switch (token.getOperator()) {
                    case "+":
                        stack.push(a + b);
                        break;

                    case "-":
                        stack.push(a - b);
                        break;

                    case "*":
                        stack.push(a * b);
                        break;

                    case "/":
                        if (b == 0) throw new ArithmeticException("division by zero is undefined");
                        stack.push(a / b);
                        break;
                    
                    case "^":
                        stack.push(Math.pow(a, b));
                        break;
                    
                    case "%":
                        stack.push(a % b);
                        break;
                   
                    default:
                        throw new IllegalArgumentException("unknown operator");
                }
             // check if the token is function
            }  else if (token.isFunction()) {
                double value = stack.pop();

                // perform function based on the token
                switch (token.getFunction()) {
                    case "√":
                        if (value < 0) throw new ArithmeticException("cannot calculate square root of a negative number");
                        stack.push(Math.sqrt(value));
                        break;
       
                    case "ln":
                        if (value <= 0) throw new ArithmeticException("cannot calculate natural logarithm of a non-positive number");
                        stack.push(Math.log(value));
                        break;

                    default:
                        throw new IllegalArgumentException("unknown function");
                }
            }
        }
        // return the final result from the stack
        return stack.pop();
    }
}
