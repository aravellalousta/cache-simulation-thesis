package cache;

import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import cache.CacheTypes.FullyAssociativeCache;

public class LRUFullyAssociative {

    // store keys of cache
    private static Deque<String> doublyQueue;
    private HashSet<String> hashSet;
    private int CACHE_SIZE;
    static int row;

    public LRUFullyAssociative() {
        doublyQueue = new LinkedList<>();
        hashSet = new HashSet<>();
    }

    // Getter Methods
    public Deque<String> getDoublyQueue() {
        return LRUFullyAssociative.doublyQueue;
    }

    public static int getRow() {
        return row;
    }

    // Setter methods
    public void setCACHE_SIZE(int cacheSize) {
        CACHE_SIZE = cacheSize;
    }

    /* Refer the tag within the LRU cache */
    public boolean refer(String tagBits) {
        boolean found;

        // This if statement is used to determine whether the newly added item exists in
        // the set or not.
        // If it does, it's moved to the start of the queue.
        // If it's completely new, the last item get's removed so there's space for the
        // new item to be added.

        if (!hashSet.contains(tagBits)) {
            if (doublyQueue.size() == CACHE_SIZE) {
                String last = doublyQueue.removeLast();
                hashSet.remove(last);
            }
            found = false;
        } else {
            // The found tag may not be always the last element, even if it's an
            // intermediate element, it needs to be removed and added to the start of the
            // queue
            doublyQueue.remove(tagBits);
            found = true;
        }

        // Add the tag bits to the queue and hash set
        doublyQueue.push(tagBits);
        hashSet.add(tagBits);
        return found;
    }

    // display contents of cache
    public void display() {
        Iterator<String> itr = doublyQueue.iterator();
        while (itr.hasNext()) {
            System.out.print(itr.next() + " ");
        }
    }

    public static void updateColumnValues(FullyAssociativeCache faCache,
            DefaultTableModel tableModel) {
        int rowCount = tableModel.getRowCount();
        Iterator<String> iterator = doublyQueue.iterator();
        for (int row = 0; row < rowCount; row++) {
            if (iterator.hasNext()) {
                String newValue = iterator.next();
                tableModel.setValueAt(newValue, row, 0);
                int memoryBlock = FullyAssociativeCache.returnMemoryBlock(4, newValue);
                tableModel.setValueAt("MemBlock[" + memoryBlock + "]", row, 1);
            }
        }

    }

}
