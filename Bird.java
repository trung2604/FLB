import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Bird {
    private int x, y, velocity;
    private BufferedImage birdImage;
    private final int WIDTH = 40 , HEIGHT = 30;

    public Bird(int startX, int startY) {
        x = startX;
        y = startY;
        velocity = 0;

        // Load the bird image
        try {
            birdImage = ImageIO.read(new File("C:\\Users\\Admin\\OneDrive - Hanoi University of Science and Technology\\test\\resources\\bird.png"));
            System.out.println("Image loaded successfully");
        } catch (IOException e) {
            System.out.println("Image not found");
            e.printStackTrace();
        }
    }

    public void flap() {
        velocity = -10;
    }

    public void update() {
        velocity += 1;  // Gravity effect
        y += velocity;
        if (y > 600 - HEIGHT) { // Ground collision
            y = 600 - HEIGHT;
        } else if (y < 0) { // Ceiling collision
            y = 0;
        }
    }

    public void draw(Graphics g) {
        if (birdImage != null) {
            g.drawImage(birdImage, x, y, WIDTH, HEIGHT, null);
        } else {
            g.fillOval(x, y, WIDTH, HEIGHT); // Draw oval if image is missing
        }
    }

    // Getter methods for position
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
