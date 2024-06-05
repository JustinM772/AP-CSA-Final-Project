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
    private String restrictedArea;
    private Timer timer;
    private int time = 50;
    private int num;
    private ArrayList<String> directions;

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
        restrictedArea = "None";
        player = new Player(400, 400);
        creatures = new ArrayList<>();
        enemies = new ArrayList<>();
        directions = new ArrayList<>();
        timer = new Timer(1000, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        g.setFont(new Font("Arial", BOLD, 19));
        g.drawRect(150, 75, player.getHealth(), 25);
        g.drawLine(150, 75, 250, 75);
        g.drawLine(250, 75, 250, 100);
        g.drawLine(250, 100, 150, 100);
        g.drawLine(150, 100, 150, 75);
        g.setColor(Color.GREEN);
        g.fillRect(150, 75, player.getHealth(), 25);
        g.setColor(Color.BLACK);
        g.drawRect(750, 75, 150, 75);
        g.fillRect(750, 75, 150, 75);
        g.setColor(Color.WHITE);
        g.drawString("Health", 75, 100);
        g.drawString("Restricted Area: ", 750, 95);
        g.setColor(Color.RED);
        g.drawString(restrictedArea, 804, 125);
        if (player.getHealth() > 0 && time > 0) {
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
                if (player.playerRect().intersects(enemies.get(i).enemyRect())) {
                    player.setHealth(player.getHealth() - 20);
                    enemies.remove(enemies.get(i));
                    i--;
                }
            }
            for (int i = 0; i < enemies.size(); i++) {
                if (i == 0) {
                    enemies.get(i).follow(player.getX(), player.getY());
                } else {
                    enemies.get(i).move(directions.get(i));
                    if (enemies.get(i).getX() - 1 <= -20) {
                        directions.set(i, "right");
                    } else if (enemies.get(i).getX() + 1 > 960) {
                        directions.set(i, "left");
                    }
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
            g.drawString("Time: " + time, 500, 100);
        }
        if (player.getHealth() <= 0) {
            g.setFont(new Font("Arial", BOLD, 25));
            g.drawString("You died", 450, 270);
        } else if(time <= 0&& player.getHealth() > 0) {
            g.setFont(new Font("Arial", BOLD, 25));
            g.drawString("You win!", 450, 270);
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
            int x = (int) (Math.random() * 801) + 100;
            int y = (int) (Math.random() * 801) + 100;
            creatures.add(new Creature(x, y));
            if (time == 30) {
                num = (int) (Math.random() * 4) + 1;
            }
            if (num == 1) {
                restrictedArea = "LEFT";
            } else if (num == 2) {
                restrictedArea = "RIGHT";
            } else if (num == 3) {
                restrictedArea = "TOP";
            } else if (num == 4) {
                restrictedArea = "BOTTOM";
            }
            if (time == 10) {
                num = (int) (Math.random() * 4) + 1;
            }
            if (time <= 30 && time > 10) {
                if (num == 1) {
                    if (player.getX() <= 480) {
                        player.setHealth(player.getHealth() - 20);
                        restrictedArea = "LEFT";
                    }
                } else if (num == 2) {
                    if (player.getX() > 480) {
                        player.setHealth(player.getHealth() - 20);
                        restrictedArea = "RIGHT";
                    }
                } else if (num == 3) {
                    if (player.getY() <= 480) {
                        player.setHealth(player.getHealth() - 20);
                        restrictedArea = "TOP";
                    }
                } else if (num == 4) {
                    if (player.getY() > 480) {
                        player.setHealth(player.getHealth() - 20);
                        restrictedArea = "BOTTOM";
                    }
                }
            } else if (time <= 10 && time > -10) {
                if (num == 1) {
                    if (player.getX() <= 480) {
                        player.setHealth(player.getHealth() - 20);
                        restrictedArea = "LEFT";
                    }
                } else if (num == 2) {
                    if (player.getX() > 480) {
                        player.setHealth(player.getHealth() - 20);
                        restrictedArea = "RIGHT";
                    }
                } else if (num == 3) {
                    if (player.getY() <= 480) {
                        player.setHealth(player.getHealth() - 20);
                        restrictedArea = "TOP";
                    }
                } else if (num == 4) {
                    if (player.getY() > 480) {
                        player.setHealth(player.getHealth() - 20);
                        restrictedArea = "BOTTOM";
                    }
                }
            }
            if (time % 8 == 0 && time < 40) {
                int a = (int) (Math.random() * 901) + 100;
                int b = (int) (Math.random() * 901) + 100;
                enemies.add(new Enemy(a, b));
                directions.add("left");
            }
        }
    }

}