import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Player {
    private BufferedImage img;
    private int xCoord;
    private int yCoord;
    private int health;
    public Player(int xCoord, int yCoord) {
        try {
            img = ImageIO.read(new File("src/Cactus Man.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        health = 100;
    }
    public int getX() {
        return xCoord;
    }
    public int getY() {
        return yCoord;
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
            xCoord -= 1;
        }
        if (direction.equals("right")) {
            xCoord += 1;
        }
        if (direction.equals("up")) {
            yCoord -= 1;
        }
        if (direction.equals("down")) {
            yCoord += 1;
        }
    }
    public Rectangle playerRect() {
        int height = img.getHeight();
        int width = img.getWidth();
        Rectangle r = new Rectangle((int) xCoord, (int) yCoord, height, width);
        return r;
    }
}