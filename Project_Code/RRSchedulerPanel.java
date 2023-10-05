import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * This is my Concept for showing the Round Robin in action
 * It's not perfect but gives a pretty good and fun picture
 * It has all the same things from the Round Robin class but rebuilt for use
 * with graphics
 * 
 * @author Tyler Marois
 */
public class RRSchedulerPanel extends JPanel {

    private JLabel title = new JLabel("", SwingConstants.CENTER);
    private JLabel ready = new JLabel("", SwingConstants.CENTER);
    private JLabel t = new JLabel("", SwingConstants.CENTER);
    private JLabel completed = new JLabel("", SwingConstants.CENTER);

    private ProcessPanel process;

    private ArrayList<Process> processes;
    private ArrayList<Process> completedProcesses;
    private MinHeap heap = new MinHeap(0);
    private int tNum = 0;
    private int size = 1000;
    private int slowdown = 1;
    public Boolean done = false;

    /**
     * Constructor for the RR scheduler panel
     * 
     * @param size     How many processes
     * @param slowdown Time of T in milliseconds
     */
    public RRSchedulerPanel(int size, int slowdown) {
        this.size = size;
        this.slowdown = slowdown;
        process = new ProcessPanel(new Process(0, 0, 0));

        title.setFont(new Font("Impact", Font.PLAIN, 60));
        title.setBounds(0, 25, 600, 100);
        title.setForeground(Color.black);
        title.setText("RR");

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
     * until all processes are complete based off of Round Robin.
     * </p>
     * This Time built with graphics in mind
     * 
     * @param contextSwitch Time it takes for processes to switch
     * @param timeSlice     time each process can run for
     */
    public void scheduler(int contextSwitch, int timeSlice) {
        this.completedProcesses = new ArrayList<>();

        if (heap.minHeap.size() == 0) {
            fillProcesses();
        }

        while (completedProcesses.size() != size) {
            ProcessNode runningProcess = null;

            if (!heap.minHeap.isEmpty()) {
                runningProcess = heap.getMin(true);
                process.setRunningProcess(runningProcess.p);
            }

            addToHeap();

            for (int i = 0; i < timeSlice; i++) {
                ready.setText("Ready Processes: " + String.valueOf(heap.minHeap.size()));
                t.setText("T: " + String.valueOf(tNum));
                if (runningProcess != null) {
                    process.resetFields();

                    if (runningProcess.p.getServiceTimeLeft() != 0) {
                        runningProcess.p.decreseaseServiceTime();
                    }
                }

                try {
                    Thread.sleep(slowdown);
                } catch (Exception e) {
                }

                tNum++;
                addToHeap();
            }

            tNum += contextSwitch;

            if (runningProcess != null) {
                if (runningProcess.p.getServiceTimeLeft() == 0) {
                    runningProcess.p.setTurnaroundTime(tNum, true);
                    completedProcesses.add(runningProcess.p);
                } else {
                    heap.insert(runningProcess);
                }
            }
            completed.setText("Completed Processes: " + String.valueOf(completedProcesses.size()));
        }

        calculateNormalizedT();
        done = true;
    }

    /**
     * adds to the heap once a process has arrived
     */
    public void addToHeap() {
        for (int i = 0; i < processes.size(); i++) {
            Process process = processes.get(i);
            if (process.getArrivalTime() < tNum) {
                heap.insert(new ProcessNode(process));
                processes.remove(i);
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

        //turing has an error with this...
        //Comparator<Process> byArrival = (Process p1, Process p2) -> p1.getArrivalTime() - p2.getArrivalTime();

        Collections.sort(processes, byArrival);
    }
}
