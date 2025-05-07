import java.util.*;

public class ShuntingYard {
    public static Queue<Token> createPostFix(Queue<Token> infixQueue) {
        Queue<Token> output = new LinkedList<>(); 
        Stack<Token> operators = new Stack<>();

        //goes through every token in queue
        for (Token token : infixQueue) {
            if (token.isNumber()) {
                //if token is an number add
                output.add(token);
            } else if (token.isOperator()) {
                //if token is an operator, pop operators from the stack BASED ON PRECEDENCE
                while (!operators.isEmpty() && shouldPop(operators.peek(), token)) {
                    output.add(operators.pop());
                }
                //push current operator on stack
                operators.push(token); 
                //if token is ( push
            } else if (token.isOpenParen()) {
                operators.push(token);
                //if the token is a close para, pop operators until an open parenthesis is found<- KEYY
            } else if (token.isCloseParen()) {
                while (!operators.isEmpty() && !operators.peek().isOpenParen()) {
                    output.add(operators.pop());
                }
                //if empty pop
                if (!operators.isEmpty()) {
                    operators.pop(); 
                }
                //if its a funtion push it
            } else if (token.isFunction()) {
                operators.push(token);
            }
        }

        //pop any remaining operators from the stack to the output queue
        while (!operators.isEmpty()) {
            output.add(operators.pop());
        }
        return output;
    }

    // see if operator on top of the stack should be popped
    private static boolean shouldPop(Token top, Token current) {
        if (!top.isOperator()) {
            return false; 
        }
        //Calls helper to see the num
        int topPrecedence = getPrecedence(top.getOperator());
        //Calls helper to see the num
        int currentPrecedence = getPrecedence(current.getOperator());

        // Pop if the top operator has higher precedence or the same precedence/ is left-associative
        if (topPrecedence > currentPrecedence) {
            return true;
        }
        if (topPrecedence == currentPrecedence && isLeftAssociative(current.getOperator())) {
            return true;
        }
        return false;
    }

    // precedence of an operator
    private static int getPrecedence(String s) {
        if (s.equals("+") || s.equals("-")) {
            return 1;
        }
        if (s.equals("*") || s.equals("/") || s.equals("x") || s.equals("÷") || s.equals("%")) {
            return 2;
        }
        if (s.equals("^")) {
            return 3; 
        }
        return -1; 
    }

    // Sees if an operator is left-associative
    private static boolean isLeftAssociative(String s) {
        return !s.equals("^"); 
    }
}
