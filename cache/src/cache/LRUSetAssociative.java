package cache;

import java.util.*;

public class LRUSetAssociative {

    private HashSet<String> hashSet;
    private Deque<String> doublyQueue;
    static int row;

    // maximum capacity of set size
    private int SET_SIZE;

    // Constructor
    public LRUSetAssociative() {
        doublyQueue = new LinkedList<>();
        hashSet = new HashSet<>();
    }

    // Getter methods
    public Deque<String> getDoublyQueue() {
        return this.doublyQueue;
    }

    public static int getRow() {
        return row;
    }

    // Setter methods
    public void setSetSize(int setSize) {
        SET_SIZE = setSize;
    }

    // Refer the page within the LRU cache
    public boolean refer(String page) {
        boolean found;
        // This if statement is used to determine whether the newly added item exists in
        // the set or not.
        // If it does, it's moved to the start of the queue.
        // If it's completely new, the last item get's removed so there's space for the
        // new item to be added.

        if (!hashSet.contains(page)) {
            if (doublyQueue.size() == SET_SIZE) {
                String last = doublyQueue.removeLast();
                hashSet.remove(last);
            }
            found = false;
        } else {
            // The found page may not be always the last element, even if it's an
            // intermediate element, it needs to be removed and placed to the start of the
            // queue
            doublyQueue.remove(page);
            found = true;
        }

        // Add the tag bits to the queue and hash set
        doublyQueue.push(page);
        hashSet.add(page);

        return found;
    }

}
