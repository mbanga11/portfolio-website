package FlappyBirdGame.src;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class Flappybird extends JPanel implements ActionListener, KeyListener {
    int BoardWid = 360;
    int BoardH = 640;

    // Images
    Image BackroundImg;
    Image BirdImg;
    Image topPip;
    Image botPip;

    // Bird variables
    int Birdx = BoardWid / 8;
    int Birdy = BoardH / 2;
    int BW = 50;
    int BH = 25;

    class Bird {
        int x = Birdx;
        int y = Birdy;
        int W = BW;
        int H = BH;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    // Pipe variables
    int Px = BoardWid;
    int Py = 0;
    int PipeW = 64;
    int PipeH = 512;

    class Pipe {
        int x = Px;
        int y = Py;
        int width = PipeW;
        int height = PipeH;
        Image img;
        boolean passed = false;

        Pipe(Image img) {
            this.img = img;
        }
    }

    // Game logic
    Bird bird;
    int VX = -4;
    int VY = 0;
    int grav = 1;
    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer gameLoop;
    Timer placePipe;

    boolean GameOver = false;

    Double score = 0.0;

    // Restart Button
    JButton restartButton;

    Flappybird() {
        setPreferredSize(new Dimension(BoardWid, BoardH));
        setFocusable(true);
        addKeyListener(this);

        // Load images
        BackroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        BirdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPip = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        botPip = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        bird = new Bird(BirdImg);
        pipes = new ArrayList<>();

        // Restart Button setup
        restartButton = new JButton("Restart Game");
        restartButton.setBounds(BoardWid / 4, BoardH - 100, BoardWid / 2, 50);
        restartButton.setVisible(false);
        // color of button 
        // restartButton.setBackground(Color.GREEN); // Set background color
        // restartButton.setForeground(Color.WHITE); // Set text color
        // restartButton.setBorderPainted(false); // Remove border if needed
        // restartButton.setOpaque(true); // Ensure background color is applied
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });

        this.setLayout(null); // Allow button positioning
        this.add(restartButton); // Add the button to the panel

        // Game timers
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();

        placePipe = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        placePipe.start();
    }

    public void placePipes() {
        int randomPipeY = -PipeH / 4 - random.nextInt(PipeH / 2); // Randomized y position for the top pipe
        int OS = BoardH / 4;
        Pipe top = new Pipe(topPip);
        top.x = BoardWid;
        top.y = randomPipeY;

        Pipe bottom = new Pipe(botPip);
        bottom.x = BoardWid;
        bottom.y = randomPipeY + PipeH + OS; // Add a gap below the top pipe

        pipes.add(top);
        pipes.add(bottom);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Background
        g.drawImage(BackroundImg, 0, 0, BoardWid, BoardH, null);
    
        // Bird
        g.drawImage(BirdImg, bird.x, bird.y, bird.W, bird.H, null);
    
        // Pipes
        for (Pipe pipe : pipes) {
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }
    
        // Score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
    
        if (GameOver) {
            // Background box
            int boxWidth = 240;
            int boxHeight = 110;
            int boxX = (BoardWid - boxWidth) / 2; // Center horizontally
            int boxY = (BoardH - boxHeight) / 2; // Center vertically
    
            g.setColor(Color.WHITE);
            g.fillRect(boxX, boxY, boxWidth, boxHeight);
    
            // Game Over Text
            g.setColor(Color.BLACK);
            String gameOverText = "Game Over";
            String scoreText = "Score: " + (int) Math.floor(score);
            FontMetrics metrics = g.getFontMetrics();
    
            // Center the text inside the box
            int textPadding = 10; // Equal padding top and bottom
            int gameOverX = boxX + (boxWidth - metrics.stringWidth(gameOverText)) / 2;
            int gameOverY = boxY + textPadding + metrics.getAscent();
    
            int scoreX = boxX + (boxWidth - metrics.stringWidth(scoreText)) / 2;
            int scoreY = gameOverY + textPadding + metrics.getHeight();
    
            g.drawString(gameOverText, gameOverX, gameOverY);
            g.drawString(scoreText, scoreX, scoreY);
        } else {
            g.drawString(String.valueOf((int) Math.floor(score)), 10, 35);
        }
    }
    
    
    
    


    public void move() {
        // Bird movement
        VY += grav;
        bird.y += VY;
        bird.y = Math.max(bird.y, 0);

        // Pipe movement
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += VX;

            // Check for collisions
            if (collision(bird, pipe)) {
                GameOver = true;
            }
        }

        // Check if bird falls below the screen
        if (bird.y > BoardH) {
            GameOver = true;
        }

        for (Pipe pipe : pipes) {
            if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                pipe.passed = true;
                score += 0.5; // Increment score by 0.5 for each passed pipe
            }
        }
    }

    public boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width &&
               a.x + a.W > b.x &&
               a.y < b.y + b.height &&
               a.y + a.H > b.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();

        if (GameOver) {
            placePipe.stop();
            gameLoop.stop();
            restartButton.setVisible(true); // Show Restart button
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !GameOver) {
            VY = -9; // Jump effect
        }

        // if (GameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
        //     restartGame(); // Restart the game when space is pressed after "Game Over"
        // }
    }

    // Method to restart the game
    private void restartGame() {
        bird.y = BoardH / 2;
        VY = 0;
        pipes.clear();
        score = 0.0;
        GameOver = false;
        gameLoop.start();
        placePipe.start();
        restartButton.setVisible(false); // Hide Restart button
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

}
