import java.awt.Graphics;
import java.awt.Rectangle;

//Circle that extends from shape class
//This class makes a circle shape with a uniqiue rad
public class Circle extends Shape{
    //instance variable that stores the rad of the circle
    private int rad; 

    //getter for the rad for circle 
    public int getRadius() { 
        return rad; 
    }

    //setter for the rad of the circle
    public void setRadius(int rad) {
         this.rad = rad;
    }

    //a constructor that creates a circle at X,Y and the given rad
    public Circle(int x, int y, int rad){ 
        super(x,y); 
        this.rad = rad;
    }

    //overides the getArea method in shapes to get the area of a circle 
    @Override
    public double getArea() {
       
        return Math.PI * rad * rad;
       
    }


    //override the draw method to draw and color a circle on the panel 
    @Override
    public void draw(Graphics g) {
        // set the color of the circle based on the shape's color (inherited from Shape)
        g.setColor(getColor());

        // draw the filled oval using the specified (x, y)  and diameter (radius * 2)
        g.fillOval(getX(), getY(), rad * 2, rad * 2);
    }


     // Override getBoundingRect method to return the bounding rectangle of the circle
    @Override
    public Rectangle getBoundingRect() {
        // The bounding rectangle for a circle is a square whose side length is diamter 
        return new Rectangle(getX(), getY(), rad * 2, rad * 2);

        
    }

    //method to get random circle with random x, y and rad 
    public static Circle getRandomCircle() {
        //gets random x and y with a range of 500x500
        int x = (int) (Math.random() * 500); 
        int y = (int) (Math.random() * 500); 

        //gets the random rad in a range of 10 - 40 
        int radius = (int) (Math.random() * 30) + 10; 
        return new Circle(x, y, radius);
    }

    
}
