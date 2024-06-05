import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Player {
    private BufferedImage img;
    private double xCoord;
    private double yCoord;
    private int health;
    public Player(int xCoord, int yCoord) {
        try {
            img = ImageIO.read(new File("src/cat_5-removebg-preview.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        health = 100;
    }
    public int getX() {
        return (int)xCoord;
    }
    public int getY() {
        return (int)yCoord;
    }
    public BufferedImage getImg() {
        return img;
    }
    public int getHealth() {
        return health;
    }
    public void setHealth(int newHealth) {
        health = newHealth;
    }
    public void move(String direction) {
        if (direction.equals("left")) {
            if (xCoord - 1 >= 0) {
                xCoord -= 2;
            }
        } else if (direction.equals("right")) {
            if (xCoord + 1 <= 940) {
                xCoord += 2;
            }
        } else if (direction.equals("up")) {
            if (yCoord - 1 >= 0) {
                yCoord -= 2;
            }
        } else if (direction.equals("down")) {
            if (yCoord + 1 <= 940) {
                yCoord += 2;
            }
        }
    }
    public Rectangle playerRect() {
        int height = img.getHeight();
        int width = img.getWidth();
        return new Rectangle((int)xCoord, (int)yCoord, width, height);
    }
}
