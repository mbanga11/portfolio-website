// DO NOT CHANGE THIS CLASS
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Shape {

   private int x;
   private int y;
   private int yVelocity;
   private int xVelocity;
   private static int gravity = -3;
   
   private Color color;
   

   public Shape(int x, int y) {
      this.x = x;
      this.y = y;
      this.color = Shape.getRandColor();
   }
   
   public abstract double getArea();
   public abstract void draw(Graphics g); 
   public abstract Rectangle getBoundingRect();
   
   public static Color getRandColor() {
      int r = (int) (Math.random() * 255);
      int rgbInc = (int) (Math.random() * 255);
      int g = (r + rgbInc) % 256;
      int b = (g + rgbInc) % 256;
      return new Color(r, g, b);
   }
   
   public static void moreGravity() {
      // don't let gravity get too strong
      gravity = Math.max(Shape.gravity - 1, -10);
   }
   
   public static void lessGravity() {
      // don't let gravity get to zero
      gravity = Math.min(Shape.gravity + 1, -1);
   }
   
   public void move() {
      // accelerate
      yVelocity -= Shape.gravity;
      
      // move
      y += yVelocity;
      x += xVelocity;
      
      Rectangle rect = getBoundingRect();
      // See if we need to bounce off the floor
      if ((int) rect.getMaxY() >= AnimatedPanel.getFloor(x)) {
         // calculate where our new Y should be so we don't crash through the floor
         int deltaFromY = (int) rect.getMaxY() - y;
         y = AnimatedPanel.getFloor(x) - deltaFromY;
         
         // change our y-direction
         yVelocity = -yVelocity;
         xVelocity += 1;
      }
      
      if ((int) rect.getMaxX() >= Main.WIDTH || (int) rect.getMinX() <= 0) {
         xVelocity = -xVelocity;
      }
   }
   
   public int getX() {
      return this.x;
   }
   
   public int getY() {
      return this.y;
   }
   
   public void setX(int x) {
      this.x = x;
   }

   public void setY(int y) {
      this.y = y;
   }
   
   public Color getColor() {
      return this.color;
   }
}
