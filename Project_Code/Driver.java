import java.util.*;

/**
 * Runs the tests described in the project description namely parts 2 and 3 in
 * section 3
 * 
 * @author Tyler Marois
 */
public class Driver {

  /**
   * Tests the First Come First Serve Scheduler
   * 
   * @param times How many times to test the scheduler
   * @return String[][] This data fills a table
   */
  public static String[][] testFCFS(int times) {
    FCFS fcfs;
    double averageNormalfcfs = 0;
    double avgTurnaroundfcfs = 0;
    for (int i = 0; i < times; i++) {
      fcfs = new FCFS();
      fcfs.scheduler();
      avgTurnaroundfcfs += fcfs.calculateAvgTTime();
      averageNormalfcfs += fcfs.calculateAvgNTTime();
    }

    String[][] data = new String[1][2];

    data[0][0] = String.format("%.4f", (avgTurnaroundfcfs / 1000) / 1000).toString();
    data[0][1] = String.format("%.4f", (averageNormalfcfs / 1000) / 1000).toString();

    return data;
  }

  /**
   * Tests the Round Robin Scheduler with every context time and time slice
   * 
   * @param times amount of times to test
   * @return ArrayList<String[][]> The data to be shown in a table
   */
  public static ArrayList<String[][]> testRR(int times) {
    RR rr;

    int contextSwitch[] = { 0, 5, 10, 15, 20, 25 };
    int timeSlice[] = { 50, 100, 250, 500 };

    ArrayList<Double> tTimeAverages = new ArrayList<>();
    ArrayList<Double> ntTimeAverages = new ArrayList<>();
    ArrayList<String[][]> rrAverages = new ArrayList<>();

    System.out.println("Round Robin Tables");

    for (int timeSliceTime : timeSlice) {
      tTimeAverages = new ArrayList<>();
      ntTimeAverages = new ArrayList<>();
      String data[][] = new String[6][3];
      for (int contextSwitchTime : contextSwitch) {
        double averageNormalRR = 0;
        double avgTurnaroundRR = 0;

        for (int i = 0; i < times; i++) {
          rr = new RR(1000);
          rr.scheduler(contextSwitchTime, timeSliceTime);
          averageNormalRR += rr.calculateAvgNTTime();
          avgTurnaroundRR += rr.calculateAvgTTime();
        }

        tTimeAverages.add(avgTurnaroundRR / 1000);
        ntTimeAverages.add(averageNormalRR / 1000);
      }

      for (int i = 0; i < 6; i++) {
        data[i][0] = String.valueOf(contextSwitch[i]);
        data[i][1] = String.format("%.4f", tTimeAverages.get(i) / 1000).toString();
        data[i][2] = String.format("%.4f", ntTimeAverages.get(i) / 1000).toString();
      }

      rrAverages.add(data);
    }

    return rrAverages;
  }

  /**
   * returns the test results when the processes are grouped by turnaround time
   * 
   * @param times amount of times to test to get a good average
   * @return String[][] The data to fill a table
   */
  public static String[][] groupTest(int times) {
    ArrayList<Double> fcfsAvgTotal = new ArrayList<>();
    ArrayList<Double> rr50AvgTotal = new ArrayList<>();
    ArrayList<Double> rr250AvgTotal = new ArrayList<>();
    ArrayList<Double> rr500AvgTotal = new ArrayList<>();

    for (int i = 0; i < times; i++) {
      FCFS fcfs = new FCFS();
      fcfs.scheduler();

      RR rr50 = new RR(1000);
      RR rr250 = new RR(1000);
      RR rr500 = new RR(1000);
      rr50.scheduler(10, 50);
      rr250.scheduler(10, 250);
      rr500.scheduler(10, 500);

      ArrayList<Process> fcfsCompleted = groupTestSorter(fcfs.getCompletedProcesses());
      ArrayList<Process> rr50Completed = groupTestSorter(rr50.getCompletedProcesses());
      ArrayList<Process> rr250Completed = groupTestSorter(rr250.getCompletedProcesses());
      ArrayList<Process> rr500Completed = groupTestSorter(rr500.getCompletedProcesses());

      ArrayList<Double> fcfsAvg = groupTestAverager(fcfsCompleted);
      ArrayList<Double> rr50Avg = groupTestAverager(rr50Completed);
      ArrayList<Double> rr250Avg = groupTestAverager(rr250Completed);
      ArrayList<Double> rr500Avg = groupTestAverager(rr500Completed);

      if (i == 0) {
        for (int j = 0; j < 20; j++) {
          fcfsAvgTotal.add(fcfsAvg.get(j));
          rr50AvgTotal.add(rr250Avg.get(j));
          rr250AvgTotal.add(rr250Avg.get(j));
          rr500AvgTotal.add(rr500Avg.get(j));
        }
      } else {
        for (int j = 0; j < 20; j++) {
          fcfsAvgTotal.set(j, fcfsAvg.get(j) + fcfsAvgTotal.get(j));
          rr50AvgTotal.set(j, rr50Avg.get(j) + rr50AvgTotal.get(j));
          rr250AvgTotal.set(j, rr250Avg.get(j) + rr250AvgTotal.get(j));
          rr500AvgTotal.set(j, rr500Avg.get(j) + rr500AvgTotal.get(j));
        }
      }
    }

    String[][] data = new String[20][5];

    for (int i = 0; i < 20; i++) {

      data[i][0] = String.valueOf(i + 1);
      data[i][1] = String.format("%.4f", fcfsAvgTotal.get(i) / times).toString();
      data[i][2] = String.format("%.4f", rr50AvgTotal.get(i) / times).toString();
      data[i][3] = String.format("%.4f", rr250AvgTotal.get(i) / times).toString();
      data[i][4] = String.format("%.4f", rr500AvgTotal.get(i) / times).toString();
    }

    return data;
  }

  /**
   * Helper for groupTests that sorts all thr processes by turnaround time.
   * 
   * @param processes the processes to be sorted
   * @return ArrayList<Process> sorted processes
   */
  public static ArrayList<Process> groupTestSorter(ArrayList<Process> processes) {
    Comparator<Process> byNTime = new Comparator<Process>() {

      @Override
      public int compare(Process p1, Process p2) {
        return Double.compare(p1.getNormalizedTurnaroundTime(), p2.getNormalizedTurnaroundTime());
      }
    };

    //Apparently this is an error when ran on turing...
    // Comparator<Process> byNTime = (Process p1, Process p2) -> Double.compare(p1.getNormalizedTurnaroundTime(),
    //     p2.getNormalizedTurnaroundTime());

    Collections.sort(processes, byNTime);

    return processes;
  }

  /**
   * Helper for groupTests that averages the sorted groups of processes
   * 
   * @param processes processes to be averaged
   * @return ArrayList<Double> averages in order of group
   */
  public static ArrayList<Double> groupTestAverager(ArrayList<Process> processes) {
    ArrayList<Double> groupedAverages = new ArrayList<>();
    double groupAverage = 0;
    for (int i = 0; i < processes.size(); i++) {
      groupAverage += processes.get(i).getNormalizedTurnaroundTime() / 1000;
      if (i % 50 == 49) {
        groupedAverages.add(groupAverage / 20);
        groupAverage = 0;
      }
    }
    return groupedAverages;
  }
}
