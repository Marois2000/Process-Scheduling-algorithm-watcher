import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Fun little loading animation since some of the frames take a few seconds to
 * load
 * 
 * @author Tyler Marois
 */
public class Loading extends JPanel implements ActionListener {

    private int x = 0;
    public Timer timer;

    /**
     * Constructor for the loading animation
     */
    public Loading() {
        this.timer = new Timer(1, this);
        timer.start();
        this.setPreferredSize(new Dimension(300, 25));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.green);
        g2d.fillRect(x, 0, 30, 25);
        x++;
        if (x > 300)
            x = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    /**
     * Stops the timer once the frame closes so it doesn't continue hogging memory
     */
    public void stop() {
        timer.stop();
    }
}
