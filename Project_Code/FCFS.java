import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The First Come First Serve Scheduler.
 * 
 * @author Tyler Marois
 */
public class FCFS {

    private ArrayList<Process> processes;
    private ArrayList<Process> completedProcesses;
    private int t = 0;

    /**
     * Empty constructor for FCFS, will create processes later
     */
    public FCFS() {
        t = t + t;
    }

    /**
     * Constructor that will use given processes
     * 
     * @param processes given processes to run
     */
    public FCFS(ArrayList<Process> processes) {
        this.processes = processes;
        scheduler();
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
     * This is where the Scheduler happens it goes through and adds
     * processes in and decrements their service time and increases the t time
     * until all processes are complete based off of FCFS
     */
    public void scheduler() {
        int t = 0;
        if (processes == null) {
            fillProcesses();
        }

        completedProcesses = new ArrayList<>();
        while (!processes.isEmpty()) {
            Process runningProcess = processes.get(0);

            if (runningProcess.getServiceTimeLeft() == 0) {
                runningProcess.setTurnaroundTime(t, false);
                completedProcesses.add(processes.remove(0));
            }
            runningProcess.decreseaseServiceTime();
            t++;
        }

        calculateNormalizedT();
    }

    /**
     * Fills the ready processes for the scheduler to go through
     */
    public void fillProcesses() {
        processes = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
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
        // Comparator<Process> byArrival = (Process p1, Process p2) ->
        // p1.getArrivalTime() - p2.getArrivalTime();

        Collections.sort(processes, byArrival);
    }

    /**
     * gets the processes that aren't completed but are ready
     * 
     * @return ArrayList<Process> the ready processes
     */
    public ArrayList<Process> getProcesses() {
        return processes;
    }

    /**
     * gets the processes that are complete
     * 
     * @return ArrayList<Process> the complete processes
     */
    public ArrayList<Process> getCompletedProcesses() {
        return completedProcesses;
    }
}
