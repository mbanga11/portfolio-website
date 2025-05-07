import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.Color;
import java.awt.event.*;
import java.awt.Font;

import java.util.HashMap;

public class CalcPanel extends JPanel implements ActionListener {

   private static final long serialVersionUID = 1L;
   private static final int BTN_SIZE = 50;
   private static final int BTN_TYPE_EQ = 1;
   private static final int BTN_TYPE_NUM = 2;
   private static final int BTN_TYPE_OP = 3;
   private static final Color COLOR_DARK_GRAY = new Color(27, 27, 27);
   private static final Color COLOR_OFFWHITE = new Color(240, 240, 240);
   private static final Color COLOR_GRAY = new Color(215, 215, 215);
   private static final Color COLOR_BLUE = new Color(0, 90, 158);
   private static final Color COLOR_OFF_BLUE = new Color(35, 116, 177);

   protected JLabel display;
   protected JLabel equationDisplay;

   private String operator = "=";
   private double total = 0;
   private boolean clearDisplay = false;

   private HashMap<String, JButton> buttons = new HashMap<>();

   public CalcPanel() {
      createComponents(null);
   }

   /**
    * This will create all the JButton components and add them to the JPanel. All
    * callbacks are set.
    */
   public void createComponents(String[] buttons) {
      String[] buttonNames = { 
            "C", "x²", "√", "÷", 
            "7", "8", "9", "x", 
            "4", "5", "6", "-", 
            "1", "2", "3", "+", 
            "±", "0", ".", "=" };
      layoutComponents(buttons == null ? buttonNames : buttons);
   }

   private void layoutComponents(String[] buttonNames) {
      // Set the background of our pane to be gray
      this.setBackground(new Color(230, 230, 230));
      // Set the LayoutManager to be nothing.
      setLayout(null);

      // Adds all buttons in buttonNames
      for (int i = 0; i < buttonNames.length; i++) {
         String s = buttonNames[i];

         // checks if button already exists, then we extend
         // the button size to be bigger. This happens for digit 0
         // when we just want it to be bigger.
         if (buttons.containsKey(s)) {
            // If button exists, extend it
            JButton b = buttons.get(s);
            int dx = (i % 4 + 1) * (BTN_SIZE + 1) + 7 - b.getX();
            int dy = (i / 4 + 2) * (BTN_SIZE + 1) + 29 - b.getY();
            b.setSize(dx, dy);
         } else if (!s.equals("")) {
            JButton b = new JButton(s);
            buttons.put(s, b);
            addButton(b, i / 4 + 1, i % 4);
         }
      }

      display = new JLabel("", JLabel.RIGHT);
      equationDisplay = new JLabel("", JLabel.RIGHT);

      // Display is at the top. The text is right justified.
      // The display does not need an action listener.
      display.setBounds(8, 30, 4 * (BTN_SIZE + 1), BTN_SIZE);
      display.setFont(new Font("Monospaced", Font.BOLD, 18));
      add(display);

      equationDisplay.setBounds(8, 8, 4 * (BTN_SIZE + 1), 30);
      equationDisplay.setFont(new Font("Monospaced", Font.PLAIN, 10));
      add(equationDisplay);

      // Keyboard input
      this.addKeyListener(new KeyAdapter() {
         public void keyTyped(KeyEvent e) {
            onChar(e.getKeyChar() + "");
         }
      });
   }

   /**
    * Add each button to the JPanel by doing 3 things: 1) Set the Bounds to place
    * the component to the right location 2) Add the button to the JPanel (so Swing
    * will know about it) 3) Add the Action Listener to be "this" JPanel object
    * 
    * @param btn The Button to add
    * @param row The row location, zero-based
    * @param col The column location, zero-based
    */
   public void addButton(JButton btn, int row, int col) {
      // do some math to get the button location
      int x = col * (BTN_SIZE + 1) + 8;
      int y = row * (BTN_SIZE + 1) + 30;
      btn.setBounds(x, y, BTN_SIZE, BTN_SIZE);
      
      // add the button to the panel and set the even handler
      this.add(btn);
      btn.addActionListener(this);

      // Customize the border look and font
      btn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
      btn.setFont(new Font("Monospaced", Font.PLAIN, 14));
      
      // refuse focus so that keyboard capture will work correctly
      btn.setFocusable(false);
      
      // Customize the button colors
      final int type = getBtnType(btn.getText());
      btn.setBackground(getBtnBackColor(type));
      btn.setForeground(getBtnForeColor(type));

      // Customize colors on mouse over
      btn.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseEntered(java.awt.event.MouseEvent evt) {
            btn.setBackground(getBtnBackColorOver(type));
         }

         public void mouseExited(java.awt.event.MouseEvent evt) {
            btn.setBackground(getBtnBackColor(type));
         }
      });
   }

   private int getBtnType(String s) {
      int type = BTN_TYPE_OP;
      if (s.equals("=")) {
         type = BTN_TYPE_EQ;
      } else if (".±0123456789".contains(s)) {
         type = BTN_TYPE_NUM;
      }
      return type;
   }

   // the color of the button
   private Color getBtnBackColor(int buttonType) {
      Color clr = Color.WHITE;
      if (buttonType == BTN_TYPE_EQ) {
         clr = COLOR_BLUE;
      } else if (buttonType == BTN_TYPE_OP) {
         clr = COLOR_GRAY;
      }
      return clr;
   }

   private Color getBtnBackColorOver(int buttonType) {
      Color clr = COLOR_OFFWHITE;
      if (buttonType == BTN_TYPE_EQ) {
         clr = COLOR_OFF_BLUE;
      }
      return clr;
   }

   // Color for the text on the button
   private Color getBtnForeColor(int buttonType) {
      if (buttonType == BTN_TYPE_EQ) {
         return Color.WHITE;
      }
      return COLOR_DARK_GRAY;
   }

   /**
    * An operator button was pressed. Do the math to update the running total and
    * update the display accordingly.
    */
   private void doMath() {
      // total = total operator "display"
      String text = display.getText();
      if (text.length() > 0) {
         try {
            double num = Double.parseDouble(text);
            // operator is the last operator the user hit
            switch (operator) {
            case "+":
               double origTotal = total;
               total += num;
               System.out.printf("%f + %f = %f\n", origTotal, num, total);
               display.setText("" + total);
               break;
            case "":
            case "=":
               // Set total value to the display
               // Example: user typed: 1+3= +5=
               // when user types second '+' we set total to current display
               total = num;
               System.out.printf("Total = %f\n", total);
               // always clear the display
               display.setText("");
               break;
            case "-":
               origTotal = total;
               total -= num;
               System.out.printf("%f - %f = %f\n", origTotal, num, total);
               display.setText("" + total);
               break;
            case "/":
            case "÷":
               origTotal = total;
               total /= num;
               System.out.printf("%f / %f = %f\n", origTotal, num, total);
               display.setText("" + total);
               break;
            case "*":
            case "x":
               origTotal = total;
               total *= num;
               System.out.printf("%f * %f = %f\n", origTotal, num, total);
               display.setText("" + total);
               break;
            }
         } catch (NumberFormatException ex) {
            // invalid format of display. perhaps two decimal places?
            System.out.println("Invalid number in display");
            total = 0.0;
            display.setText("");
         }
      }
   }

   /**
    * actionPerformed is the method in the ActionListener interface. This method
    * will get called for every components action event because this program adds
    * "this" as the ActionListener. Buttons trigger an action when they are
    * clicked.
    * 
    * @param e The ActionEvent object that helps us know which button was clicked.
    */
   public void actionPerformed(ActionEvent e) {
      // Get the text from the button that was clicked
      String s = e.getActionCommand();
      onChar(s);
   }

   public void onChar(String s) {
      String text = display.getText();
      if (".0123456789".contains(s) || (text.isEmpty() && s.equals("-"))) {

         if (clearDisplay) {
            // reset display
            text = "";
            if (operator.equals("=")) {
               equationDisplay.setText("");
            }
            clearDisplay = false;
         }

         if (!s.equals(".") || !text.contains(".")) {
            // Prevent adding multiple decimal places
            text += s;
         }

         display.setText(text);
      } else if ("±√".contains(s) || "x²".equals(s)) {
         doUnaryMath(s);
      } else if (s.equals("C") || s.equals("\b")) {
         display.setText("");
         equationDisplay.setText("");
      } else {
         double num = 0;
         // if user does operator after operator
         if (!text.isEmpty()) {
            num = Double.parseDouble(text);
            doMath();
         }
         operator = s;
         clearDisplay = true;
         if (s.equals("=")) {
            equationDisplay.setText(equationDisplay.getText() + " " + num + " =");
         } else {
            equationDisplay.setText(total + " " + operator);
         }
      }
   }

   private void doUnaryMath(String op) {
      String text = display.getText();
      double n = Double.parseDouble(text);
      switch (op) {
      case "±":
         n *= -1;
         break;
      case "x²":
         n *= n;
         break;
      case "√":
         n = Math.sqrt(n);
         break;
      }
      display.setText((n % 1 == 0) ? String.valueOf((int) n) : n + "");
   }

}
