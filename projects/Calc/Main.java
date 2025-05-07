import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

class Main extends JFrame {

   private JPanel calcPanel;
   private JPanel shuntingCalcPanel;
   private JPanel currentPanel;
   public static final int WIDTH = 220;
   public static final int HEIGHT = 386;
   
   private static String packageName;

   public static void main(String[] args) {
      packageName = Main.class.getPackage().getName();
      if (packageName.length() > 0) {
         packageName += ".";
      }
      // create an instance of the Main class
      // if we use a lambda, it would be 'final' because it is referenced in the
      // lambda expression.
      Main main = new Main();

      // Starts the UI Thread and creates the the UI in that thread.
      // Use a method pointer to call the createFrame method.
      SwingUtilities.invokeLater(main::createFrame);

      // The main thread will now end since we aren't doing anymore work
   }

   /**
    * Create the JFrame that holds the JPanel(s). Create the JPanels that holds all
    * the components. Create all the components, position them and add
    * ActionListeners. Add a menu bar to the JFrame.
    */
   public void createFrame() {
      // Sets the title found in the Title Bar of the JFrame
      this.setTitle("Calc");
      // Sets the size of the main Window
      this.setSize(WIDTH, HEIGHT);
      // Allows the application to properly close when the
      // user clicks on the Red-X
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      JMenuBar bar = new JMenuBar();
      JMenu menu = new JMenu("Show");
      JMenuItem item = new JMenuItem("Basic", 'B');
      item.addActionListener(this::changePanel);
      menu.add(item);
      item = new JMenuItem("Shunting Yard", 'Y');
      item.addActionListener(this::changePanel);
      menu.add(item);
      bar.add(menu);

      // Add testing menus
      menu = new JMenu("Tests");
      item = new JMenuItem("PostFix", 'P');
      item.addActionListener(a -> TestRunner.runTests(packageName + "TestPostFix"));
      menu.add(item);
      item = new JMenuItem("Shunting Yard", 'Y');
      item.addActionListener(a -> TestRunner.runTests(packageName + "TestShuntingYard"));
      menu.add(item);
      bar.add(menu);
      
      this.setJMenuBar(bar);
      
      // Put all the buttons and functionality into a JPanel
      // so that the JFrame can be extended more easily.
      calcPanel = new CalcPanel();
      shuntingCalcPanel = new ShuntingCalcPanel();
      currentPanel = calcPanel;

      // Without setting bounds here, then one of the panels
      // will be of size zero and not show up
      calcPanel.setBounds(0, 0, WIDTH + 10, HEIGHT + 10);
      this.add(calcPanel);
      this.add(shuntingCalcPanel);

      // Show the calcPanel and main frame
      this.setVisible(true);
      calcPanel.setVisible(true);
      shuntingCalcPanel.setVisible(false);
      calcPanel.requestFocusInWindow();
      this.setSize(WIDTH + 10, HEIGHT + 10);
   }

   public void changePanel(ActionEvent e) {
      String s = e.getActionCommand();
      currentPanel.setVisible(false);
      if (s.toLowerCase().startsWith("basic")) {
         currentPanel = calcPanel;
         this.setSize(WIDTH + 10, HEIGHT + 10);
      } else {
         currentPanel = shuntingCalcPanel;
         this.setSize(WIDTH + 10, HEIGHT + 65);
      }
      currentPanel.setVisible(true);
      currentPanel.requestFocusInWindow();
   }

}