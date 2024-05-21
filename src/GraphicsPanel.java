import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class GraphicsPanel extends JPanel implements KeyListener, ActionListener {
    private BufferedImage background;
    private boolean[] pressedKeys;
    private Player player;
    private Creature creature;
    private Timer timer;
    private int time;


    public GraphicsPanel(String name) {
        try {
            background = ImageIO.read(new File("src/background.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        player = new Player(0,0);
        creature = new Creature(0, 400);
        pressedKeys = new boolean[128];
        time = 0;
        timer = new Timer(1000,this);
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();


    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background,0,0, null);
        g.drawImage(player.getImg(), player.getX(), player.getY(), null);
        g.drawImage(creature.getImg(), creature.getX(), creature.getY(), null);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Score: ", 20, 40);
        g.drawString("Player Health: " + player.getHealth(), 20, 70);
        g.drawString("Time: " + time, 20, 100);
        if (pressedKeys[65]) {
            player.move("left");
        }
        if (pressedKeys[68]) {
            player.move("right");
        }
        if (pressedKeys[87]) {
            player.move("up");
        }
        if (pressedKeys[83]) {
            player.move("down");
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