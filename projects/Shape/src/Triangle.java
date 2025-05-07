import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;

// represents a triangle shape, extends Shape class
public class Triangle extends Shape {

    // instance variables for the base
    private int base; 
    // instance variables for the height
    private int height; 

    //getter method for the base
    public int getBase() { 
        return base; 
    }

    //setter method for the base
    public void setBase(int base) { 
        this.base = base; 
    }

    //getter method for the height
    public int getHeight() {
        return height; 
    }

    //setter method for the height
    public void setHeight(int height) { 
        this.height = height; 
    }

    // constructor to create a triangle with x, y , base, and height
    public Triangle(int x, int y, int base, int height){ 
        // call the parent class constructor to set position
        super(x, y);  
        // initialize the base of the triangle
        this.base = base;  
        // initialize the height of the triangle
        this.height = height;  
    }

    // override the getArea method to calculate the area of the triangle
    @Override
    public double getArea() {
        // returns area 
        return 0.5 * base * height;
    }

    // override the draw method to make the triangle 
    @Override
    public void draw(Graphics g) {
        // set the color of the circle based on the shape's color (inherited from Shape)
        g.setColor(getColor());
        
       
        // x-coordinates of the triangle's three corners
        int[] xPoints = {getX(), getX() + base / 2, getX() - base / 2}; 

        // y-coordinates of the triangle's three corners
        int[] yPoints = {getY(), getY() + height, getY() + height}; 


        // create a Polygon object to draw filled triangle
        g.fillPolygon(new Polygon(xPoints, yPoints, 3));
    }

    // override the getBoundingRect method to return the bounding rectangle of a triangle
    @Override
    public Rectangle getBoundingRect() {
        // bounding rectangle uses triangle's position, width (base), and height
        return new Rectangle(getX() - base / 2, getY(), base, height);
    }

    // static method to generate and return a random triangle 
    public static Triangle getRandomTriangle() {
        // generate random x and y coordinates within a 500x500 space
        int x = (int) (Math.random() * 500); 
        int y = (int) (Math.random() * 500); 
        
        // generate random base and height between 10-60
        int base = (int) (Math.random() * 50) + 10; 
        int height = (int) (Math.random() * 50) + 10; 
        
        // return a new Triangle object with the random values
        return new Triangle(x, y, base, height);
    }
}
