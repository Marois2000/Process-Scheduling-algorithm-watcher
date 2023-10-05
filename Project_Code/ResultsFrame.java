import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

/**
 * This shows the results describe in section 3 part 2 where we had to get the
 * data from the schedulers
 * 
 * @author Tyler Marois
 */
public class ResultsFrame extends JFrame implements ActionListener {

    JButton home = new JButton("Home");

    /**
     * Constructor for results frame
     */
    public ResultsFrame() {

        ArrayList<String[][]> rraverages = Driver.testRR(1000);
        String[][] fcfsData = Driver.testFCFS(1000);
        String[][] rr50Data = rraverages.get(0);
        String[][] rr100Data = rraverages.get(1);
        String[][] rr250Data = rraverages.get(2);

        String[][] rr500Data = rraverages.get(3);

        JPanel container = new JPanel();

        home.setPreferredSize(new Dimension(200, 100));
        home.setFont(new Font("Impact", Font.PLAIN, 30));
        home.setFocusPainted(false);
        home.addActionListener(this);
        home.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] fcfsCol = { "Turnaround Time", "Normalized Time" };
        String[] rrCol = { "Context Switch", "Turnaround Time", "Normalized Time" };
        container.add(new ResultsPanel(fcfsData, fcfsCol, "First Come First Serve"));
        container.add(new ResultsPanel(rr50Data, rrCol, "Round Robin 50ms"));
        container.add(new ResultsPanel(rr100Data, rrCol, "Round Robin 100ms"));
        container.add(new ResultsPanel(rr250Data, rrCol, "Round Robin 250ms"));
        container.add(new ResultsPanel(rr500Data, rrCol, "Round Robin 500ms"));
        container.setBackground(Color.decode("#4e535c"));
        container.setLayout(new FlowLayout(FlowLayout.LEADING, 100, 25));

        JScrollPane sp = new JScrollPane(container);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setPreferredSize(new Dimension(1000, 350));

        this.getContentPane().add(sp);
        this.getContentPane().add(home);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.getContentPane().setBackground(Color.decode("#4e535c"));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new Menu();
        this.dispose();
    }
}
