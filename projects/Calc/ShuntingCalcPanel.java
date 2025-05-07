import java.util.LinkedList;
import java.util.Queue;

public class ShuntingCalcPanel extends CalcPanel {

	private boolean clearDisplay = false;
	private String curToken = "";
	private Queue<Token> queue = new LinkedList<>();

	@Override
	public void createComponents(String[] names) {
	   String[] buttonNames = {
	         "C", "√", "ln", "⌫",
	         "^", "(", ")", "÷",
	         "7", "8", "9", "x",
	         "4", "5", "6", "-",
	         "1", "2", "3", "+",
	         "0", "0", ".", "="
	   };
		super.createComponents(buttonNames);
	}
	
	@Override
	public void onChar(String s) {
		String displayText = display.getText();
		if (".0123456789^/*()+-x÷√ln".contains(s)) {
		   // build our queue of token
		   if (".0123456789".contains(s) || (curToken.isEmpty() && s.equals("-"))) {
		       // let the token grow if it is a number
		      curToken += s;
		   } else {
		      // enqueue the operator just added
		      // BUT first, enqueue any current number
		      if (curToken.length() > 0) {
		         Token cur = new Token(curToken);
		         queue.add(cur);
		      }
		      // clear the current number
		      curToken = "";
		      // now enqueue 
		      queue.add(new Token(s));
		   }
			if (clearDisplay) {
				displayText = "";
				equationDisplay.setText("");
				clearDisplay = false;
			}

			display.setText(displayText + s);
		} else if (s.equals("⌫") || s.equals("\b")) {
			if (displayText.length() > 0) {
			   if (curToken.length() > 0) {
			      curToken = curToken.substring(0, curToken.length() - 1);
			   } else if (queue.size() > 0) {
			      // the Queue interface doesn't allow removal of the last item
			      // so we need to downcast it to the LinkedList.
			      LinkedList<Token> ll = (LinkedList<Token>) queue;
			      ll.removeLast();
			   }
				int i = 1;
				int n = displayText.length();
				if (n > 1 && displayText.substring(n - 2).equals("ln")) {
					i = 2;
				}
				display.setText(displayText.substring(0, n - i));
			}
		} else if (s.equals("=")) {
		   // first enqueue any current number
         if (curToken.length() > 0) {
            Token cur = new Token(curToken);
            queue.add(cur);
         }
			equationDisplay.setText(Token.exprString(queue) + " =");
			
			Queue<Token> q = ShuntingYard.createPostFix(queue);
			queue.clear();
			if (q == null) {
				equationDisplay.setText("");
				display.setText("Invalid expression");
			} else {
			   double result = PostFixEvaluator.evaluatePostFix(q);
				display.setText(String.valueOf(result));
			}
			clearDisplay = true;
			curToken = "";
		} else if (s.equals("C")) {
			display.setText("");
			queue.clear();
			curToken = "";
		} else {
			System.out.println("ERROR: INVALID BUTTON: " + s);
			System.out.println("HOW DID THIS HAPPEN??");
		}
	}
}