import java.awt.*;
import javax.swing.*;

/**
 * Creates a blue box in the process watcher that contains info about the
 * running process
 * 
 * @author Tyler Marois
 */
public class ProcessPanel extends JPanel {

    private Process runningProcess;
    private JLabel pid = new JLabel("", SwingConstants.CENTER);
    private JLabel arrTime = new JLabel("", SwingConstants.CENTER);
    private JLabel servTime = new JLabel("", SwingConstants.CENTER);

    /**
     * Constructor for the ProcessPanel
     * 
     * @param runningProcess The running process
     */
    public ProcessPanel(Process runningProcess) {
        this.runningProcess = runningProcess;
        pid.setText("Process ID: " + String.valueOf(runningProcess.getPid()));
        arrTime.setText("Arrival Time: " + String.valueOf(runningProcess.getArrivalTime()));
        servTime.setText("Service Time Left: " + String.valueOf(runningProcess.getServiceTimeLeft()));

        pid.setFont(new Font("Impact", Font.PLAIN, 30));

        arrTime.setFont(new Font("Impact", Font.PLAIN, 30));

        servTime.setFont(new Font("Impact", Font.PLAIN, 30));

        this.add(pid);
        this.add(arrTime);
        this.add(servTime);
        this.setLayout(new GridLayout(3, 1));
        this.setBackground(Color.decode("#7abff0"));
    }

    /**
     * Blank constructor to update fields later
     */
    public ProcessPanel() {
        this.add(pid);
        this.add(arrTime);
        this.add(servTime);
        this.setLayout(new GridLayout(3, 1));
        this.setBackground(Color.decode("#7abff0"));
    }

    /**
     * Sets the running process
     * 
     * @param runningProcess the running process
     */
    public void setRunningProcess(Process runningProcess) {
        this.runningProcess = runningProcess;
    }

    /**
     * Updates the labels all based off the running process
     */
    public void resetFields() {
        pid.setText("Process ID: " + String.valueOf(runningProcess.getPid()));
        arrTime.setText("Arrival Time: " + String.valueOf(runningProcess.getArrivalTime()));
        servTime.setText("Service Time Left: " + String.valueOf(runningProcess.getServiceTimeLeft()));
    }
}
