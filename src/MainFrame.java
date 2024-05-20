import javax.swing.*;

public class MainFrame implements Runnable{
    private GraphicsPanel panel;

    public MainFrame() {
        JFrame frame = new JFrame("Final Project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(960, 580);
        panel = new GraphicsPanel();
        frame.add(panel);
        frame.setVisible(true);
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (true) {
            panel.repaint();
        }
    }
}
