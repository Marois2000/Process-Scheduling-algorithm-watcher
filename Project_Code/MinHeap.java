import java.util.ArrayList;
import java.util.Collections;

/**
 * The data structure the RR scheduler runs off of
 * 
 * @author Tyler Marois
 */
public class MinHeap {

    public ArrayList<ProcessNode> minHeap;
    public int size;

    /**
     * Constructor for the MinHeap
     * 
     * @param heapSize how many things in the heap
     */
    public MinHeap(int heapSize) {
        minHeap = new ArrayList<>();
        size = 0;
    }

    /**
     * Reorders the heap
     * 
     * @param index The index we are reordering from
     */
    public void heapify(int index) {
        int largest = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;

        if (left < size && (minHeap.get(left).compare(minHeap.get(largest)) < 0)) {
            largest = left;
        }

        if (right < size && minHeap.get(right).compare(minHeap.get(largest)) < 0) {
            largest = right;
        }

        if (largest != index) {
            Collections.swap(minHeap, index, largest);
            heapify(largest);
        }
    }

    /**
     * Reorders the heap in the opposite direction
     * 
     * @param index index to reorder from
     */
    public void reverseHeapify(int index) {
        int parent = (index - 1) / 2;

        if (index != 0 && (minHeap.get(parent).compare(minHeap.get(index)) > 0)) {
            Collections.swap(minHeap, index, parent);
            heapify(parent);
        }
    }

    /**
     * Removes a process node from the heap
     * 
     * @param fullyRemove basically deprecated since I was worried about taking
     *                    processes out that weren't complete but I end up removing
     *                    them then adding them back if they are incomplete
     * @return ProcessNode the min thing in the heap
     */
    public ProcessNode getMin(Boolean fullyRemove) {
        ProcessNode min = minHeap.get(0);
        minHeap.set(0, minHeap.get(minHeap.size() - 1));

        if (fullyRemove) {
            minHeap.remove(minHeap.size() - 1);
        } else {

        }

        size = minHeap.size() - 1;
        heapify(0);
        return min;
    }

    /**
     * Inserts a process into the heap
     * 
     * @param node the process contained in a ProcessNode to insert in
     */
    public void insert(ProcessNode node) {
        minHeap.add(node);
        size = minHeap.size();
        reverseHeapify(size - 1);
    }
}
