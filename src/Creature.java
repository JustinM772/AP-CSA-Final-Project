import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Creature {
    private BufferedImage img;
    private int xCoord;
    private int yCoord;
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
            if (xCoord - 1 >= -20) {
                xCoord -= 1;
            }
        } else if (direction.equals("right")) {
            if (xCoord + 1 <= 950) {
                xCoord += 1;
            }
        } else if (direction.equals("up")) {
            if (yCoord - 1 >= -10) {
                yCoord -= 1;
            }
        } else if (direction.equals("down")) {
            if (yCoord + 1 <= 940) {
                yCoord += 1;
            }
        }
    }
    public Rectangle creatureRect() {
        int height = img.getHeight();
        int width = img.getWidth();
        Rectangle r = new Rectangle(xCoord, yCoord, width, height);
        return r;
    }
}
