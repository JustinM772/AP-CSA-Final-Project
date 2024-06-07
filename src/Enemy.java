import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Enemy {
    private double x;
    private double y;
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
        return (int) x;
    }
    public int getY() {
        return (int) y;
    }
    public BufferedImage getImg() {
        return img;
    }
    public void move(String direction) {
        if (direction.equals("left") && x - 1 >= -20) {
            x--;
        } else if (direction.equals("right") && x + 1 <= 960) {
            x++;
        }
    }
    public void follow(int playerX, int playerY) {
        if (playerX < x && playerY < y) {
            x-= 0.3;
            y-= 0.3;
        } else if (playerX < x && playerY == y) {
            x-= 0.3;
        } else if (playerX < x && playerY > y) {
            x-= 0.3;
            y+= 0.3;
        } else if (playerX == x && playerY < y) {
            y-= 0.3;
        } else if (playerX == x && playerY > y) {
            y+= 0.3;
        } else if (playerX > x && playerY < y) {
            x+= 0.3;
            y-= 0.3;
        } else if (playerX > x && playerY == y) {
            x+= 0.3;
        } else if (playerX > x && playerY > y) {
            x+= 0.3;
            y+= 0.3;
        }
    }
    public Rectangle enemyRect() {
        int height = img.getHeight();
        int width = img.getWidth();
        return new Rectangle((int) x, (int) y, height, width);
    }
}