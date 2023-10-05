import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * This is my Concept for showing the FCFS in action
 * It's not perfect but gives a pretty good and fun picture
 * It has all the same things from the FCFS class but rebuilt for use with
 * graphics
 * 
 * @author Tyler Marois
 */
public class FCFSSchedulerPanel extends JPanel {

    private JLabel title = new JLabel("", SwingConstants.CENTER);
    private JLabel ready = new JLabel("", SwingConstants.CENTER);
    private JLabel t = new JLabel("", SwingConstants.CENTER);
    private JLabel completed = new JLabel("", SwingConstants.CENTER);
    private ProcessPanel process;

    private ArrayList<Process> processes;
    private ArrayList<Process> completedProcesses;
    private ArrayList<Process> readyProcesses;
    private int tNum = 0;
    int size = 1000;
    int slowdown = 1;

    public boolean done = false;

    /**
     * Creates a panel that shows the FCFS scheduler in action
     * 
     * @param size     how many processes
     * @param slowdown speed of the t time based off the Thread.sleep in scheduler
     */
    public FCFSSchedulerPanel(int size, int slowdown) {
        tNum = tNum + tNum;
        this.size = size;
        this.slowdown = slowdown;
        process = new ProcessPanel(new Process(0, 0, 0));

        title.setFont(new Font("Impact", Font.PLAIN, 60));
        title.setBounds(0, 25, 600, 100);
        title.setForeground(Color.black);
        title.setText("FCFS");

        ready.setFont(new Font("Impact", Font.PLAIN, 30));
        ready.setBounds(0, 100, 600, 100);
        ready.setForeground(Color.black);

        t.setFont(new Font("Impact", Font.PLAIN, 30));
        t.setBounds(0, 175, 600, 100);
        t.setForeground(Color.black);

        completed.setFont(new Font("Impact", Font.PLAIN, 30));
        completed.setBounds(0, 250, 600, 100);
        completed.setForeground(Color.black);

        process.setBounds(150, 350, 300, 150);

        this.add(title);
        this.add(ready);
        this.add(t);
        this.add(completed);
        this.add(process);
        this.setLayout(null);

        Border border = BorderFactory.createLineBorder(Color.black, 5, true);
        this.setBorder(border);

        this.setBackground(Color.decode("#ababab"));

        this.completedProcesses = new ArrayList<>();

        this.setPreferredSize(new Dimension(600, 600));
    }

    /**
     * Calculates all the completed processes normalized Turnaround time
     */
    public void calculateNormalizedT() {
        for (Process process : completedProcesses) {
            process.setNormalizedTurnaroundTime();
        }
    }

    /**
     * calculates the average normalized turnaround time of each process
     * 
     * @return float the average normalized turnaround time
     */
    public float calculateAvgNTTime() {
        float totalTime = 0;
        for (Process process : completedProcesses) {
            totalTime += process.getNormalizedTurnaroundTime();
        }
        return totalTime / completedProcesses.size();
    }

    /**
     * Calculates the average turnaround time of each process
     * 
     * @return double the average turnaround time
     */
    public double calculateAvgTTime() {
        double totalTime = 0;
        for (Process process : completedProcesses) {
            totalTime += process.getTurnaroundTime();
        }
        return totalTime / completedProcesses.size();
    }

    /**
     * <p>
     * This is where the Scheduler happens it goes through and adds
     * processes in and decrements their service time and increases the t time
     * until all processes are complete based off of FCFS.
     * </p>
     * 
     * This Time built with graphics in mind
     */
    public void scheduler() {
        int tNum = 0;
        fillProcesses();
        readyProcesses = new ArrayList<>();

        completedProcesses = new ArrayList<>();

        if (!readyProcesses.isEmpty()) {
            process.setRunningProcess(processes.get(0));
            tNum = readyProcesses.get(0).getArrivalTime();
        }

        ready.setText("Ready Processes: " + String.valueOf(readyProcesses.size()));

        while (completedProcesses.size() != size) {
            Process runningProcess = null;
            if (!readyProcesses.isEmpty()) {
                runningProcess = readyProcesses.get(0);
            }

            t.setText("T: " + String.valueOf(tNum));
            try {
                Thread.sleep(slowdown);
            } catch (Exception e) {
            }

            if (runningProcess != null) {
                if (runningProcess.getServiceTimeLeft() == 0) {
                    runningProcess.setTurnaroundTime(tNum, false);
                    completedProcesses.add(readyProcesses.remove(0));
                }
                runningProcess.decreseaseServiceTime();
            }

            if (!readyProcesses.isEmpty()) {
                process.setRunningProcess(readyProcesses.get(0));
            }

            ready.setText("Ready Processes: " + String.valueOf(readyProcesses.size()));
            process.resetFields();
            tNum++;
            addToReady(tNum);
            completed.setText("Completed Processes: " + String.valueOf(completedProcesses.size()));
        }

        calculateNormalizedT();
        done = true;
    }

    /**
     * Add to the ready processes based off arival time.
     * 
     * @param num the T time
     */
    public void addToReady(int num) {
        for (int i = 0; i < processes.size(); i++) {
            Process process = processes.get(i);
            if (process.getArrivalTime() <= num) {
                readyProcesses.add(processes.remove(i));
            }
        }
    }

    /**
     * Fills the ready processes for the scheduler to go through
     */
    public void fillProcesses() {
        processes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int arrival = (int) Math.floor(Math.random() * 10000);
            int service = (int) Math.floor(Math.random() * 500) + 1;
            processes.add(new Process(arrival, service, i));
        }

        Comparator<Process> byArrival = new Comparator<Process>() {
            @Override
            public int compare(Process p1, Process p2) {
                return p1.getArrivalTime() - p2.getArrivalTime();
            }
        };

        // turing has an error with this...
        // Comparator<Process> byArrival = (Process p1, Process p2) -> p1.getArrivalTime() - p2.getArrivalTime();

        Collections.sort(processes, byArrival);
    }
}
