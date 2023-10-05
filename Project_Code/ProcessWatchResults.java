import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.SwingConstants;

/**
 * The results based off the 2 processes you just watched.
 * Sadly there is an odd bug with this that a simply couldn't find :(
 * The numbers are only accurate when ran with size 1000 I can't find why but
 * since the project is based off 1000 size anyway I thought it would be okay
 * 
 * @author Tyler Marois
 */
public class ProcessWatchResults extends JFrame implements ActionListener {

    private JLabel fcfsTTime = new JLabel("", SwingConstants.CENTER);
    private JLabel fcfsNTTime = new JLabel("", SwingConstants.CENTER);
    private JLabel rrTTime = new JLabel("", SwingConstants.CENTER);
    private JLabel rrNTTime = new JLabel("", SwingConstants.CENTER);
    private JButton home = new JButton("Home");

    /**
     * Constructor for the results
     * 
     * @param fTTime  The FCFS turnaround time
     * @param fNTTime The FCFS normalized turnaround time
     * @param rTTime  The Round Robin turnaround time
     * @param rNTTime The Round Robin normalized turnaround time
     */
    public ProcessWatchResults(double fTTime, double fNTTime, double rTTime, double rNTTime) {
        home.setPreferredSize(new Dimension(200, 50));
        home.setFont(new Font("Impact", Font.PLAIN, 25));
        home.setFocusPainted(false);
        home.addActionListener(this);

        fcfsTTime.setFont(new Font("Impact", Font.PLAIN, 30));
        fcfsTTime.setForeground(Color.black);
        fcfsTTime.setText("FCFS Turnaround Time: " + String.format("%.5f", fTTime / 1000).toString());

        fcfsNTTime.setFont(new Font("Impact", Font.PLAIN, 30));
        fcfsNTTime.setForeground(Color.black);
        fcfsNTTime.setText("FCFS Normalized Turnaround Time: " + String.format("%.5f", fNTTime / 1000).toString());

        rrTTime.setFont(new Font("Impact", Font.PLAIN, 30));
        rrTTime.setForeground(Color.black);
        rrTTime.setText("RR Turnaround Time: " + String.format("%.5f", rTTime / 1000).toString());

        rrNTTime.setFont(new Font("Impact", Font.PLAIN, 30));
        rrNTTime.setForeground(Color.black);
        rrNTTime.setText("RR Normalized Turnaround Time: " + String.format("%.5f", rNTTime / 1000).toString());

        this.add(fcfsTTime);
        this.add(fcfsNTTime);
        this.add(rrTTime);
        this.add(rrNTTime);
        this.add(home);
        this.getContentPane().setBackground(Color.decode("#4e535c"));
        this.setLayout(new GridLayout(5, 1));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == home) {
            this.dispose();
            new Menu();
        }
    }
}
