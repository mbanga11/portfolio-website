

// class that represents a square shape, extends rectangle
public class Square extends Rectangles {

    // constructor to set position and side length
    public Square(int x, int y, int sideLength) {
        super(x, y, sideLength, sideLength);
    }

    // generates a random square
    public static Square getRandomSquare() {
         // generate random x/y coordinates within a 500x500 space
        int x = (int) (Math.random() * 500); 
        int y = (int) (Math.random() * 500); 

        // generate random sidelength between 10-60
        int sideLength = (int) (Math.random() * 50) + 10; 
        
        //returns the random square
        return new Square(x, y, sideLength);
        
    }
}
