import java.util.*;

/**
 * The Round Robin Scheduler.
 * 
 * @author Tyler Marois
 */
public class RR {
  private MinHeap heap = new MinHeap(0);
  private ArrayList<Process> processes;
  private ArrayList<Process> completedProcesses;
  private int t = 0;
  private int size;

  /**
   * constructor for Round Robin that will initialize processes afterwards
   * 
   * @param size how many processes
   */
  public RR(int size) {
    this.heap = new MinHeap(0);
    this.processes = new ArrayList<>();
    this.completedProcesses = new ArrayList<>();
    this.size = size;

    t = 0;
  }

  /**
   * Constructor that takes in ready processes
   * 
   * @param processes the ready processes
   * @param size      how many processes
   */
  public RR(ArrayList<Process> processes, int size) {
    this.processes = processes;
    this.size = size;
    for (Process process : processes) {
      heap.insert(new ProcessNode(process));
    }
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
   * until all processes are complete based off of Round Robin
   */
  public void scheduler(int contextSwitch, int timeSlice) {
    this.completedProcesses = new ArrayList<>();

    if (heap.minHeap.size() == 0) {
      fillProcesses();
    }

    ProcessNode runningProcess = null;

    if (!heap.minHeap.isEmpty()) {
      runningProcess = heap.getMin(true);
    }

    while (completedProcesses.size() != size) {

      if (!heap.minHeap.isEmpty()) {
        runningProcess = heap.getMin(true);
      }

      addToHeap();

      for (int i = 0; i < timeSlice; i++) {

        if (runningProcess != null) {
          if (runningProcess.p.getServiceTimeLeft() != 0) {
            runningProcess.p.decreseaseServiceTime();
          }
        }

        t++;
      }

      t += contextSwitch;

      if (runningProcess != null) {
        if (runningProcess.p.getServiceTimeLeft() == 0) {
          runningProcess.p.setTurnaroundTime(t, false);
          completedProcesses.add(runningProcess.p);
        } else {
          heap.insert(runningProcess);
        }
      }

    }

    calculateNormalizedT();
  }

  /**
   * adds to the heap once a process has arrived
   */
  public void addToHeap() {
    for (int i = 0; i < processes.size(); i++) {
      Process process = processes.get(i);
      if (process.getArrivalTime() < t) {
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

    //turing thinks an error with this...
    //Comparator<Process> byArrival = (Process p1, Process p2) -> p1.getArrivalTime() - p2.getArrivalTime();

    Collections.sort(processes, byArrival);
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
