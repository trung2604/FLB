import java.awt.Color; // Add this import statement
import java.awt.Graphics;
import java.util.Random;

public class Pipe {
    private int x, y;
    private final int WIDTH = 50, HEIGHT = 400;
    private final int GAP = 150;
    private final Random random = new Random();
    private boolean scored = false;

    public Pipe(int startX) {
        x = startX;
        y = random.nextInt(HEIGHT - GAP);
    }

    public void update() {
        x -= 5;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isScored() {
        return scored;
    }

    public void setScored(boolean scored) {
        this.scored = scored;
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN); // Ensure Color is recognized
        g.fillRect(x, 0, WIDTH, y);
        g.fillRect(x, y + GAP, WIDTH, HEIGHT - (y + GAP));
    }
}
