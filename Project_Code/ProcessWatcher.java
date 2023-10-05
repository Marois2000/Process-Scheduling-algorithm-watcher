import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * The window that shows two processes running simultaneously, I did this by
 * running each process in seperate thread so they can be watched at the same
 * time to compare
 */
public class ProcessWatcher extends JFrame implements ActionListener {

    private FCFSSchedulerPanel fcfs;
    private RRSchedulerPanel rr;
    private Thread fcfsThread;
    private Thread rrThread;

    private JButton home = new JButton("Home");
    private JButton results = new JButton("Results");

    /**
     * Constructor for Process Watcher
     * 
     * @param size     How many processes in each scheduler
     * @param slowdown How fast the t time happens in ms
     */
    public ProcessWatcher(int size, int slowdown) {
        super("You Want to See?");

        fcfs = new FCFSSchedulerPanel(size, slowdown);
        rr = new RRSchedulerPanel(size, slowdown);

        home.setPreferredSize(new Dimension(100, 400));
        home.setFont(new Font("Impact", Font.PLAIN, 25));
        home.setFocusPainted(false);
        home.addActionListener(this);

        results.setPreferredSize(new Dimension(125, 400));
        results.setFont(new Font("Impact", Font.PLAIN, 25));
        results.setFocusPainted(false);
        results.addActionListener(this);

        this.add(fcfs);
        this.add(rr);
        this.add(home);
        this.add(results);
        this.getContentPane().setBackground(Color.decode("#4e535c"));
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        fcfsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                fcfs.scheduler();
            }
        });
        fcfsThread.start();

        rrThread = new Thread(new Runnable() {
            @Override
            public void run() {
                rr.scheduler(10, 50);
            }
        });
        rrThread.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == home) {
            fcfsThread.interrupt();
            ;
            rrThread.interrupt();
            ;
            this.dispose();
            new Menu();
        } else if (e.getSource() == results && fcfs.done && rr.done) {
            new ProcessWatchResults(fcfs.calculateAvgTTime(), fcfs.calculateAvgNTTime(), rr.calculateAvgTTime(),
                    rr.calculateAvgNTTime());
            this.dispose();
        }
    }
}
