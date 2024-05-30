import javax.swing.*;

public class MainFrame implements Runnable{
    private GraphicsPanel panel;

    public MainFrame(String name) {
        JFrame frame = new JFrame("Final Project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1016, 1039);
        frame.setLocation(500,0);
        panel = new GraphicsPanel(name);
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
