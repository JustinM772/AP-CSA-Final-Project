import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class GraphicsPanel extends JPanel implements KeyListener {
    private BufferedImage background;
    private boolean[] pressedKeys;
    private Player player;


    public GraphicsPanel() {
        try {
            background = ImageIO.read(new File());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        pressedKeys = new boolean[128];
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (pressedKeys[65]) {
            player.faceLeft();
            player.moveLeft();
        }
        if (pressedKeys[68]) {
            player.faceRight();
            player.moveRight();
        }
        if (pressedKeys[87]) {
            player.moveUp();
        }
        if (pressedKeys[83]) {
            player.moveDown();
        }
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        pressedKeys[key] = true;
    }


    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        pressedKeys[key] = false;
    }

    public void actionPerformed(ActionEvent e) {

    }

}