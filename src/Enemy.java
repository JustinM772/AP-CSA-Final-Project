import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Enemy {
    private int x;
    private int y;
    private BufferedImage img;
    public Enemy(int x, int y) {
        try {
            img = ImageIO.read(new File("src/lobsterPNG.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public BufferedImage getImg() {
        return img;
    }
    public void move(String direction) {
        if (direction.equals("left") && x - 1 >= -20) {
            x -= 1;
        } else if (direction.equals("right") && x + 1 <= 960) {
            x += 1;
        } else if (direction.equals("up") && y - 1 >= 0) {
            y -= 1;
        } else if (direction.equals("down") && y + 1 <= 890) {
            y += 1;
        }
    }
    public Rectangle enemyRect() {
        int height = img.getHeight();
        int width = img.getWidth();
        return new Rectangle(x, y, height, width);
    }
}