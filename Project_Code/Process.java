/**
 * The Process class is the object that gets run through a scheduler
 * 
 * @author Tyler Marois
 */
public class Process {

    private int arrivalTime;
    private int serviceTime;
    private int serviceTimeLeft;
    private int turnaroundTime;
    private double normalizedTurnaroundTime;
    private int pid;

    /**
     * Constructor for a Process
     *
     * @param arrivalTime the processes arival time
     * @param serviceTime the processes service time
     * @param pid         the processes ID
     */
    public Process(int arrivalTime, int serviceTime, int pid) {
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.serviceTimeLeft = serviceTime;
        this.pid = pid;
    }

    /**
     * Gets this processes ID
     * 
     * @return int pid the processes ID
     */
    public int getPid() {
        return this.pid;
    }

    /**
     * Gets this processes Arrival Time
     * 
     * @return int arrivalTime
     */
    public int getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets this processes Arrival Time
     * 
     * @param arrivalTime
     */
    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Gets this processes Service Time
     * 
     * @return int serviceTime
     */
    public int getServiceTime() {
        return serviceTime;
    }

    /**
     * Sets this processes Service Time
     * 
     * @param serviceTime
     */
    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    /**
     * Gets this processes service time left
     * 
     * @return int
     */
    public int getServiceTimeLeft() {
        return serviceTimeLeft;
    }

    /**
     * Decrements this processes service time left
     */
    public void decreseaseServiceTime() {
        this.serviceTimeLeft--;
    }

    /**
     * Sets this Processes turnaround time
     * 
     * @param t  time value
     * @param RR tells which scheduler is using this method
     */
    public void setTurnaroundTime(int t, Boolean RR) {
        if (RR) {
            turnaroundTime = t;
        } else {
            turnaroundTime = t - arrivalTime;
        }
    }

    /**
     * Gets this processes turnaround time
     * 
     * @return int
     */
    public int getTurnaroundTime() {
        return this.turnaroundTime;
    }

    /**
     * Gets this processes turnaround time
     * 
     * @return double
     */
    public double getNormalizedTurnaroundTime() {
        return normalizedTurnaroundTime;
    }

    /**
     * Sets this processes Normalized Turnaround time
     */
    public void setNormalizedTurnaroundTime() {
        this.normalizedTurnaroundTime = Math.round((double) (turnaroundTime / serviceTime) * 100.0) / 100.0;
    }
}
