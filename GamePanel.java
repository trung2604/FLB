import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Bird bird;
    private ArrayList<Pipe> pipes;
    private int score;
    private Timer timer;
    private boolean gameOver;
    
    // Sound players
    private SoundPlayer backgroundMusic;
    private SoundPlayer jumpSound;
    private SoundPlayer scoreSound;
    private SoundPlayer deathSound;

    public GamePanel() {
        initGame(); // Initialize the game components

        timer = new Timer(20, this);
        timer.start();
        
        addKeyListener(this);
        setFocusable(true);
    }

    private void initGame() {
        bird = new Bird(100, 300);
        pipes = new ArrayList<>();
        pipes.add(new Pipe(400));  // Initial pipe
        score = 0;
        gameOver = false;

        // Initialize sound players
        backgroundMusic = new SoundPlayer();
        jumpSound = new SoundPlayer();
        scoreSound = new SoundPlayer();
        deathSound = new SoundPlayer();

        // Play background music in a loop
        backgroundMusic.loopSound("C:\\Users\\Admin\\OneDrive - Hanoi University of Science and Technology\\test\\Sound\\flappybirdsound.wav");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            bird.update();
            for (Pipe pipe : pipes) {
                pipe.update();
                
                // Check for collision
                if (checkCollision(pipe)) {
                    gameOver = true;
                    timer.stop();
                    backgroundMusic.stopSound(); // Stop background music
                    deathSound.playSound("C:\\Users\\Admin\\OneDrive - Hanoi University of Science and Technology\\test\\Sound\\flapdie.wav"); // Play death sound
                }
                
                // Update score if bird passes the pipe
                if (pipe.getX() + 50 < bird.getX() && !pipe.isScored()) {
                    score++;
                    pipe.setScored(true);
                    scoreSound.playSound("C:\\Users\\Admin\\OneDrive - Hanoi University of Science and Technology\\test\\Sound\\flappoint.wav"); // Play score sound
                }
            }
            
            // Add new pipes and remove off-screen pipes
            if (pipes.get(pipes.size() - 1).getX() < 200) {
                pipes.add(new Pipe(400));
            }
            if (pipes.get(0).getX() < -50) {
                pipes.remove(0);
            }
            
            repaint();
        }
    }

    private boolean checkCollision(Pipe pipe) {
        int birdLeft = bird.getX();
        int birdRight = bird.getX() + 40;  // Bird's width
        int birdTop = bird.getY();
        int birdBottom = bird.getY() + 30; // Bird's height

        int pipeLeft = pipe.getX();
        int pipeRight = pipe.getX() + 50; // Pipe's width
        int pipeTop = pipe.getY();
        int pipeBottom = pipe.getY() + 150; // Pipe's gap size

        boolean collidesHorizontally = birdRight > pipeLeft && birdLeft < pipeRight;
        boolean collidesVertically = birdTop < pipeTop || birdBottom > pipeBottom;

        return collidesHorizontally && collidesVertically || birdBottom >= 540; // Hits ground
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, getWidth(), getHeight());

        bird.draw(g);
        for (Pipe pipe : pipes) {
            pipe.draw(g);
        }

        // Draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Score: " + score, 10, 25);

        // Game over message
        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.setColor(Color.RED);
            g.drawString("Game Over", getWidth() / 2 - 120, getHeight() / 2 - 24);
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            g.setColor(Color.WHITE);
            g.drawString("Press SPACE to restart", getWidth() / 2 - 140, getHeight() / 2 + 24);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (gameOver) {
                initGame(); // Restart the game if it is over
                timer.start(); // Restart the timer
            } else {
                bird.flap(); // Flap the bird if the game is running
                jumpSound.playSound("C:\\Users\\Admin\\OneDrive - Hanoi University of Science and Technology\\test\\Sound\\flapsound.wav"); // Play jump sound
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
