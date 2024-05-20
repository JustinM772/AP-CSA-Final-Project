import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class WelcomePanel extends JPanel implements ActionListener {

    private JTextField textField;
    private JButton submitButton;
    private JFrame enclosingFrame;

    public WelcomePanel(JFrame frame) {
        enclosingFrame = frame;
        textField = new JTextField(10);
        submitButton = new JButton("Submit");
        add(textField);
        add(submitButton);
        submitButton.addActionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.RED);
        g.drawString("Enter your name:", 50, 30);
        textField.setLocation(50, 50);
        submitButton.setLocation(50, 100);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            String playerName = textField.getText();
            MainFrame f = new MainFrame(playerName);
            enclosingFrame.setVisible(false);
        }
    }
}

