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
    private int restrictedAreaNum;
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
        g.drawRect(750, 75, player.getHealth(), 25);
        g.drawLine(750, 75, 850, 75);
        g.drawLine(850, 75, 850, 100);
        g.drawLine(850, 100, 750, 100);
        g.drawLine(750, 100, 750, 75);
        g.setColor(Color.GREEN);
        g.fillRect(750, 75, player.getHealth(), 25);
        g.setColor(Color.BLACK);
        g.drawRect(150, 75, 150, 75);
        g.fillRect(150, 75, 150, 75);
        g.setColor(Color.WHITE);
        g.drawString( "Health", 675, 100);
        g.drawString("Restricted Area: ", 150, 95);
        g.setColor(Color.RED);
        g.drawString(restrictedArea, 204, 125);
        if (player.getHealth() > 0 && time > -10000) {
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
                    player.setHealth(player.getHealth() - 15);
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
            if (restrictedArea.equals("LEFT") || restrictedArea.equals("RIGHT")) {
                g.drawLine(500, 0, 500, 1000);
            }
            if (restrictedArea.equals("TOP") || restrictedArea.equals("BOTTOM")) {
                g.drawLine(0, 500, 1000, 500);
            }
        }
        if (player.getHealth() <= 0) {
            g.setFont(new Font("Arial", BOLD, 25));
            g.drawString("You died", 450, 270);
            g.drawString("Press space to retry", 400,  300);
            if (pressedKeys[32]) {
                restrictedArea = "None";
                player.setX(400);
                player.setY(400);
                player.setHealth(100);
                time = 50;
                creatures = new ArrayList<>();
                enemies = new ArrayList<>();
                directions = new ArrayList<>();
                restrictedAreaNum = 0;
            }
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
        if (e.getSource() instanceof Timer && time > -10000) {
            time--;
            player.setHealth(player.getHealth() - 1);
            int x = (int) (Math.random() * 801) + 100;
            int y = (int) (Math.random() * 801) + 100;
            creatures.add(new Creature(x, y));
            if (time == 35) {
                restrictedAreaNum = (int) (Math.random() * 4) + 1;
            }
            if (restrictedAreaNum == 1) {
                restrictedArea = "LEFT";
            } else if (restrictedAreaNum == 2) {
                restrictedArea = "RIGHT";
            } else if (restrictedAreaNum == 3) {
                restrictedArea = "TOP";
            } else if (restrictedAreaNum == 4) {
                restrictedArea = "BOTTOM";
            }
            if (time == 10) {
                restrictedAreaNum = (int) (Math.random() * 4) + 1;
            }
            if (time <= 30 && time > 10) {
                if (restrictedAreaNum == 1) {
                    if (player.getX() <= 500) {
                        player.setHealth(player.getHealth() - 15);
                        restrictedArea = "LEFT";
                    }
                } else if (restrictedAreaNum == 2) {
                    if (player.getX() > 500) {
                        player.setHealth(player.getHealth() - 15);
                        restrictedArea = "RIGHT";
                    }
                } else if (restrictedAreaNum == 3) {
                    if (player.getY() <= 500) {
                        player.setHealth(player.getHealth() - 15);
                        restrictedArea = "TOP";
                    }
                } else if (restrictedAreaNum == 4) {
                    if (player.getY() > 500) {
                        player.setHealth(player.getHealth() - 15);
                        restrictedArea = "BOTTOM";
                    }
                }
            } else if (time <= 10 && time > -10) {
                if (restrictedAreaNum == 1) {
                    if (player.getX() <= 500) {
                        player.setHealth(player.getHealth() - 15);
                        restrictedArea = "LEFT";
                    }
                } else if (restrictedAreaNum == 2) {
                    if (player.getX() > 500) {
                        player.setHealth(player.getHealth() - 15);
                        restrictedArea = "RIGHT";
                    }
                } else if (restrictedAreaNum == 3) {
                    if (player.getY() <= 500) {
                        player.setHealth(player.getHealth() - 15);
                        restrictedArea = "TOP";
                    }
                } else if (restrictedAreaNum == 4) {
                    if (player.getY() > 500) {
                        player.setHealth(player.getHealth() - 15);
                        restrictedArea = "BOTTOM";
                    }
                }
            } else if (time < 10) {
                restrictedArea = "None";
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