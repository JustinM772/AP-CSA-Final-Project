import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.awt.Font.BOLD;

public class GraphicsPanel extends JPanel implements KeyListener, ActionListener {
    private BufferedImage background;
    private boolean[] pressedKeys;
    private Player player;
    private ArrayList<Creature> creatures;
    private ArrayList<Enemy> enemies;
    private String enemyDirection;
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
        enemies = new ArrayList<>();
        enemyDirection = "left";
        timer = new Timer(1000, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        if (player.getHealth() > 0) {
            g.drawImage(player.getImg(), player.getX(), player.getY(), null);
            for (int i = 0; i < creatures.size(); i++) {
                g.drawImage(creatures.get(i).getImg(), creatures.get(i).getX(), creatures.get(i).getY(), null);
                if (player.playerRect().intersects(creatures.get(i).creatureRect())) {
                    player.setHealth(player.getHealth() + 1);
                    creatures.remove(creatures.get(i));
                    i--;
                }
            }
            for (int i = 0; i < enemies.size(); i++) {
                g.drawImage(enemies.get(i).getImg(), enemies.get(i).getX(), enemies.get(i).getY(), null);

            }
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).move(enemyDirection);
                if (enemies.get(i).getX() - 1 <= -20) {
                    enemyDirection = "right";
                } else if (enemies.get(i).getX() + 1 > 890) {
                    enemyDirection = "left";
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
            if (time <= 30 && time > 10) {
                g.drawString("Cannot stay in left half anymore", 800, 100);
            }
            if (time <= 10 && time > -10) {
                g.drawString("Cannot stay in right half anymore", 800, 100);
            }
            g.setFont(new Font("Arial", BOLD, 20));
            g.drawString("Health: " + player.getHealth(), 100, 100);
        }
        if (player.getHealth() <= 0) {
            g.setFont(new Font("Arial", BOLD, 25));
            g.drawString("You died", 450, 270);
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
        if (e.getSource() instanceof Timer && time > 0) {
            time--;
            player.setHealth(player.getHealth() - 1);
            int x = (int) (Math.random() * 701) + 100;
            int y = (int) (Math.random() * 401) + 100;
            creatures.add(new Creature(x, y));
            if (time <= 30 && time > 10) {
                if (player.getX() <= 480) {
                    player.setHealth(player.getHealth() - 20);
                }
            } else if (time <= 10 && time > -10) {
                if (player.getX() > 480) {
                    player.setHealth(player.getHealth() - 20);
                }
            }
            if (time % 7 == 0 && time < 40) {
                int a = (int) (Math.random() * 701) + 100;
                int b = (int) (Math.random() * 401) + 100;
                enemies.add(new Enemy(a, b));
            }
        }
    }

}