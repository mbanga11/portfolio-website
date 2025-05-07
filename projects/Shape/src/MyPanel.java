
// MyPanel class extends AnimatedPanel, makes custom panel that makes random shapes
public class MyPanel extends AnimatedPanel {

    // override the getRandomShape method to return a random shape 
    @Override
    public Shape getRandomShape() {
        // generate a random integer between 0 - 3 to select one of the shape types
        int randomChoice = (int) (Math.random() * 4); 
        
        // used switch statement to return a random shape based on the randomChoice above
        switch (randomChoice) {
            case 0:
                // return a random rectangle by using random method in shape class
                return Rectangles.getRandomRectangleShape();
            case 1:
                // return a random circle by using random method in shape class
                return Circle.getRandomCircle();
            case 2:
                // return a random square by using random method in shape class
                return Square.getRandomSquare();
            case 3:
                // return a random triangle by using random method in shape class
                return Triangle.getRandomTriangle();
            default:
                // in case of an invalid choice (never happens), return null
                return null; 
        }
    }

    // override the getShapeClassNames method to return an array of shape class names as strings
    @Override
    public String[] getShapeClassNames() {
        // return the names of all available shape classes in an array of strings
        return new String[] { "Rectangles", "Circle", "Square", "Triangle" };
    }
}