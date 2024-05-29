import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Creature {
    private BufferedImage img;
    private int xCoord;
    private int yCoord;
    private int health;
    public Creature(int xCoord, int yCoord) {
        try {
            img = ImageIO.read(new File("src/helmetfishPNG.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        this.xCoord = xCoord;
        this.yCoord = yCoord;
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
    public void move(String direction) {
        if (direction.equals("left")) {
            if (xCoord - 10 >= -20) {
                xCoord -= 10;
            }
        } else if (direction.equals("right")) {
            if (xCoord + 10 <= 890) {
                xCoord += 10;
            }
        } else if (direction.equals("up")) {
            if (yCoord - 10 >= 0) {
                yCoord -= 10;
            }
        } else if (direction.equals("down")) {
            if (yCoord + 10 <= 450) {
                yCoord += 10;
            }
        }
    }
    public Rectangle creatureRect() {
        int height = img.getHeight();
        int width = img.getWidth();
        Rectangle r = new Rectangle(xCoord, yCoord, height, width);
        return r;
    }
}
