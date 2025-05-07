import java.awt.Graphics;
import java.awt.Rectangle;

// rectangles class extends shape class, representing a rectangle shape with width/height
public class Rectangles extends Shape {
    
    // instance variables for width 
    private int width; 

    // instance variables for height 
    private int height; 

    // getter method for width
    public int getWidth() {
        return width;
    }

    // setter method for width
    public void setWidth(int width) {
        this.width = width;
    }

    // getter method for height
    public int getHeight() { 
        return height;
    }

    // setter method for height
    public void setHeight(int height) { 
        this.height = height; 
    }

    // constructor to create a rectangle with (x, y) position with width/height
    public Rectangles(int x, int y, int width, int height){ 
        // call shape constructor to set position
        super(x, y);  
        // initialize the width
        this.width = width;  
        // initialize the height
        this.height = height; 
    }

    // override the getArea method to calculate the area for rectangle
    @Override
    public double getArea() {

        // returns area of rectangle
        return width * height;
    }

    // override the draw method to render the rectangle
    @Override
    public void draw(Graphics g) {
        // set the color of the rectangle based on the shape's color (inherited from Shape)
        g.setColor(getColor());
        
        // draw the filled rectangle 
        g.fillRect(getX(), getY(), width, height);
    }

    // override the getBoundingRect method to return the bounding rectangle of the rectangle
    @Override
    public Rectangle getBoundingRect() {
        // return a rectangle representing the bounding 
        return new Rectangle(getX(), getY(), width, height);
    }

    // method to make and return a random rectangle shape
    public static Rectangles getRandomRectangleShape() {
        // generate random x/y coordinates within a 500x500 space
        int x = (int) (Math.random() * 500); 
        int y = (int) (Math.random() * 500); 
        
        // generate random width and height between 10 and 60
        int width = (int) (Math.random() * 50) + 10; 
        int height = (int) (Math.random() * 50) + 10; 
        
        // return a new Rectangles object( with random vals)
        return new Rectangles(x, y, width, height);
    }
}
