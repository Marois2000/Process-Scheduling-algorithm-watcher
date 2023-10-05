/**
 * My nodes that have a field containing a Process to add to the min heap
 * 
 * @author Tyler Marois
 */
public class ProcessNode {
    Process p;
    ProcessNode left;
    ProcessNode right;

    /**
     * Constructor for the process node
     * 
     * @param p the initializer for the process field
     */
    public ProcessNode(Process p) {
        this.p = p;
    }

    /**
     * Compares 2 nodes based off of their service time left
     * 
     * @param otherProcess the process to compare to
     * @return int based off the difference between the processes
     */
    public int compare(ProcessNode otherProcess) {
        return this.p.getServiceTimeLeft() - otherProcess.p.getServiceTimeLeft();
    }
}
