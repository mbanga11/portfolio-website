package FlappyBirdGame.src;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        // Dimensions for the game window
        int BoardWid = 360;
        int BoardH = 640;

        // Create the main JFrame
        JFrame frame = new JFrame("Flappy Bird");
        frame.setSize(BoardWid, BoardH);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add the game panel
        Flappybird flappybird = new Flappybird();
        frame.add(flappybird);
        frame.pack(); // Adjust the frame size based on Flappybird's preferred size
        flappybird.requestFocus();
        // Make the frame visible
        frame.setVisible(true);

    }
}
