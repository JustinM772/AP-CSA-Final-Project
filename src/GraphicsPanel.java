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
    private ArrayList<Creature> creatures;
    private Timer timer;
    private int time = 50;

    public GraphicsPanel(String name) {
        try {
            background = ImageIO.read(new File("src/background.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        pressedKeys = new boolean[128];
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        player = new Player(400, 400);
        creatures = new ArrayList<>();
        timer = new Timer(1000, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        g.drawImage(player.getImg(), player.getX(), player.getY(), null);
        if (player.getHealth() > 0) {
            for (int i = 0; i < creatures.size(); i++) {
                g.drawImage(creatures.get(i).getImg(), creatures.get(i).getX(), creatures.get(i).getY(), null);
                if (player.playerRect().intersects(creatures.get(i).creatureRect())) {
                    player.setHealth(player.getHealth() + 1);
                    creatures.remove(creatures.get(i));
                    i--;
                }
            }
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
        g.drawString("Health: " + player.getHealth(), 100, 100);
        g.drawString("x: " + player.getX(), 300, 100);
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
        if (e.getSource() instanceof Timer && time > 0) {
            time--;
            player.setHealth(player.getHealth() - 1);
            int x = (int) (Math.random() * 701) + 100;
            int y = (int) (Math.random() * 401) + 100;
            creatures.add(new Creature(x, y));
        }
    }

}