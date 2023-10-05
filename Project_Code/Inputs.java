import java.awt.*;
import javax.swing.*;

/**
 * This Gets inputs to give to the process watcher
 * 
 * @author Tyler Marois
 */
public class Inputs extends JPanel {

    public JTextField size = new JTextField();
    public JTextField speed = new JTextField();
    private JLabel sizeLabel = new JLabel("Size of Test:", SwingConstants.CENTER);
    private JLabel speedLabel = new JLabel("Speed of Test(ms):", SwingConstants.CENTER);

    /**
     * Constructor for the inputs panel
     */
    public Inputs() {
        size.setPreferredSize(new Dimension(100, 20));

        speed.setPreferredSize(new Dimension(100, 20));

        this.add(sizeLabel);
        this.add(size);
        this.add(speedLabel);
        this.add(speed);
    }
}
