

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public class Main extends JFrame  {
   // To eliminate a warning shown in Eclipse, add this constant
   private static final long serialVersionUID = 1L;

   public static final int WIDTH = 800;
   public static final int HEIGHT = 800;
   
   private MyPanel panel;
   
   public static void main(String[] args) throws InterruptedException {
      Main theGUI = new Main();
      
      // Starts the UI Thread and creates the the UI in that thread.
      // Uses a Lambda Expression to call the createFrame method.
      // Use theGUI as the semaphore object
      SwingUtilities.invokeLater( () -> theGUI.createFrame() );
   }
   
  
   /**
    * Create the main JFrame and all animation JPanels.
    */
   public void createFrame() {
      // Sets the title found in the Title Bar of the JFrame
      this.setTitle("Bouncing Shapes");
      // Sets the size of the main Window
      this.setSize(800, 800);
      // Allows the application to properly close when the
      // user clicks on the Red-X.
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      this.setLayout(null);
        
      addMenuBar();
      
      this.panel = new MyPanel();
      
      // The panel needs to have its bounds set correctly or else it
      // won't be sized correctly and won't be visible.

      panel.setBounds(0, 0, WIDTH, HEIGHT);
      add(panel);
      panel.setVisible(true);

      // Set this JFrame to be visible
      this.setVisible(true);
      panel.startDrawing();
   }  
  
   /**
    * Add some menu options to control the experience.
    */
   private void addMenuBar() {
      
      JMenuBar bar = new JMenuBar();
      // Add the menu bar to the JFrame
      this.setJMenuBar(bar);
      
      // Create Animation menu
      JMenu menu = new JMenu("Animation");
      menu.setMnemonic('A');
      JMenuItem item = new JMenuItem("Slower animation", 'S');
      item.addActionListener(e -> panel.slowAnimation());
      item.setAccelerator(KeyStroke.getKeyStroke('-', InputEvent.CTRL_DOWN_MASK));
      menu.add(item);
      item = new JMenuItem("Faster animation", 'F');
      item.addActionListener(e -> panel.speedAnimation());
      item.setAccelerator(KeyStroke.getKeyStroke('=', InputEvent.CTRL_DOWN_MASK));
      menu.add(item);
      item = new JMenuItem("More Gravity", 'G');
      item.addActionListener(e -> Shape.moreGravity());
      menu.add(item);
      item = new JMenuItem("Less Gravity", 'L');
      item.addActionListener(e -> Shape.lessGravity());
      menu.add(item);
      item = new JMenuItem("Stop Animation", 'T');
      item.addActionListener(e -> panel.pauseAnimation());
      menu.add(item);
      item = new JMenuItem("Start Animation", 'A');
      item.addActionListener(e -> panel.restartAnimation());
      menu.add(item);
      
      bar.add(menu);
      
      // Create Shapes menu
      menu = new JMenu("Shapes");
      menu.setMnemonic('S');
      item = new JMenuItem("Add", 'A');
      item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
      item.addActionListener(e -> panel.addShape());
      menu.add(item);
      item = new JMenuItem("Remove", 'R');
      item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
      item.addActionListener(e -> panel.removeShape());
      menu.add(item);
      item = new JMenuItem("Calculate Area", 'C');
      item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
      item.addActionListener(e -> panel.showTotalArea());
      menu.add(item);

      bar.add(menu);
   }
   
}