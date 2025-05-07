// DO NOT CHANGE THIS CLASS
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public abstract class AnimatedPanel extends JPanel {

   public static final int FLOOR = Main.HEIGHT - 100;
   public static final long REPAINT_DELAY = 10; 
   public static long DELAY = 100; 
   private List<Shape> shapes;
   private Timer animateTimer;
   private Timer paintTimer;
   
   // this is to be implemented by the Student in MyPanel
   public abstract Shape getRandomShape();
   public abstract String[] getShapeClassNames();
   
   public AnimatedPanel() {      
      this.setBackground(Color.WHITE);
      shapes = new ArrayList<Shape>();
   }
   
   public void startDrawing() {
      restartAnimation();
      startPainting();
   }
   
   public void slowAnimation() {
      // no slower than 500 ms delay
      AnimatedPanel.DELAY = Math.min(500, AnimatedPanel.DELAY + 10);
      restartAnimation();
   }
   
   public void speedAnimation() {
      // no faster than 10 ms delay
      AnimatedPanel.DELAY = Math.max(10, AnimatedPanel.DELAY - 10);
      restartAnimation();
   }
   
   public void pauseAnimation() {
      if (animateTimer != null) {
         animateTimer.cancel();
         animateTimer = null;
      }
   }
   
   public void restartAnimation() {
      // just in case, make sure our old timer is gone
      pauseAnimation();
      
      // create and schedule
      animateTimer = new Timer();
      animateTimer.schedule(new TimerTask() {
         @Override
         public void run() {
            for (Shape shape : shapes) {
               shape.move();
            }
         }
      }, 0, AnimatedPanel.DELAY);
   }
   
   private void startPainting() {
      
      // create and schedule a painting timer for every 10 ms
      // on slow machines, or with lots of objects, this could be too fast
      paintTimer = new Timer();
      paintTimer.schedule(new TimerTask() {
         @Override
         public void run() {
            // we don't actually paint now, we trigger a notification
            // that we want to be repainted
            if (shapes.size() > 0) {
               repaint();
            }
         }
      }, 0, AnimatedPanel.REPAINT_DELAY);
   }
   
   public void addShape() {
      shapes.add(getRandomShape());
   }
   
   public void removeShape() {
      if (shapes.size() > 0) {
         int index = (int) (Math.random() * shapes.size());
         shapes.remove(index);
         repaint();
      }
   }
   
   public void showTotalArea() {
      double area = 0.0;
      for (Shape shape : shapes) {
         area += shape.getArea();
      }
      JOptionPane.showMessageDialog(this,  String.format("Total area is: %.2f", area));
   }
   
   public static int getFloor(int x) {
      return 100 * x / Main.WIDTH + (FLOOR - 100);
   }
   
   @Override
   public void paintComponent(Graphics g){
      // the superclass paintComponent will clear the drawing canvas for us
      super.paintComponent(g);
      
      // no need to clear everything. But this is how we'd do it.
      // g.clearRect(0, 0, this.getWidth(), this.getHeight());
      
      // draw the floor
      g.setColor(Color.BLUE);
      g.drawLine(0, FLOOR-100, Main.WIDTH, FLOOR);

      // draw all the shapes
      for (Shape shape : shapes) {
         shape.draw(g);
      }
   }
}
