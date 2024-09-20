import javax.swing.JFrame;

public class FlappyBirdGame extends JFrame {
    public FlappyBirdGame() {
        setTitle("Flappy Bird");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new GamePanel());
    }

    public static void main(String[] args) {
        new FlappyBirdGame().setVisible(true);
    }
}
