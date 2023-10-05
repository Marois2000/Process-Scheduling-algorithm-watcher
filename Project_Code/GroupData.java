import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Making tables print out in the command line seemed lame to me so I made this
 * which I found more pleasing
 * 
 * @author Tyler Marois
 */
public class GroupData extends JFrame implements ActionListener {

    private String columns[] = { "Process Group", "FCFS Results", "RR 50 Results", "RR 250 Results", "RR 500 Results" };
    private JTable data;
    private JButton home = new JButton("Home");

    /**
     * Shows the group data for the processes arranged in order of turnaround time
     */
    public GroupData() {
        super("The Beef");

        data = new JTable(Driver.groupTest(1000), columns);
        data.setFont(new Font("Impact", Font.PLAIN, 20));
        data.setRowHeight(25);
        data.getColumnModel().getColumn(0).setPreferredWidth(300);
        data.getColumnModel().getColumn(1).setPreferredWidth(300);
        data.getColumnModel().getColumn(2).setPreferredWidth(300);
        data.getColumnModel().getColumn(3).setPreferredWidth(300);
        data.getColumnModel().getColumn(4).setPreferredWidth(300);

        data.getTableHeader().setFont(new Font("Impact", Font.PLAIN, 30));
        data.getTableHeader().setBackground(Color.decode("#2c70de"));

        JScrollPane sp = new JScrollPane(data);
        sp.setPreferredSize(new Dimension(1000, 550));

        home.setPreferredSize(new Dimension(100, 100));
        home.setFont(new Font("Impact", Font.PLAIN, 25));
        home.setFocusPainted(false);
        home.addActionListener(this);

        this.add(home);
        this.add(sp);
        this.getContentPane().setBackground(Color.decode("#4e535c"));
        this.setLayout(new FlowLayout());
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
