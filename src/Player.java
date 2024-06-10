import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Player {
    private BufferedImage img;
    private BufferedImage leftImg;
    private BufferedImage rightImg;
    private double xCoord;
    private double yCoord;
    private int health;
    private boolean facingLeft;
    public Player(int xCoord, int yCoord) {
        try {
            leftImg = ImageIO.read(new File("src/Cactus Man.png"));
            rightImg = flip(leftImg);
            img = leftImg;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        health = 100;
        this.facingLeft = true;
    }
    public int getX() {
        return (int)xCoord;
    }
    public int getY() {
        return (int)yCoord;
    }
    public void setX(int newX) {
        xCoord = newX;
    }
    public void setY(int newY) {
        yCoord = newY;
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
                if (!facingLeft) {
                    img = leftImg;
                    facingLeft = true;
                }
            }
        } else if (direction.equals("right")) {
            if (xCoord + 1 <= 940) {
                xCoord += 2;
                if (facingLeft) {
                    img = rightImg;
                    facingLeft = false;
                }
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
    private BufferedImage flip(BufferedImage image) {
        int height = image.getHeight();
        int width = image.getWidth();
        BufferedImage newImage = new BufferedImage(width, height, image.getType());
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, width, 0, 0, height, null);
        g.dispose();
        return newImage;
    }

}
