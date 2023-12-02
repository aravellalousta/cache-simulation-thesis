package cache;

import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import cache.CacheTypes.*;
import cache.CacheTypes.FullyAssociativeCache;

public class LRUSetAssociative {

    // store keys of cache
    private Deque<String> doublyQueue;
    static int row;

    public Deque<String> getDoublyQueue() {
        return this.doublyQueue;
    }

    public void setCACHE_SIZE(int cacheSize) {
        CACHE_SIZE = cacheSize;
    }

    public static int getRow() {
        return row;
    }

    // store references of key in cache
    private HashSet<String> hashSet;

    // maximum capacity of cache
    private int CACHE_SIZE;

    public LRUSetAssociative() {
        doublyQueue = new LinkedList<>();
        hashSet = new HashSet<>();
    }

    /* Refer the page within the LRU cache */
    public boolean refer(String page) {
        boolean found;
        if (!hashSet.contains(page)) {
            if (doublyQueue.size() == CACHE_SIZE) {
                String last = doublyQueue.removeLast();
                hashSet.remove(last);
            }
            found = false;
        } else { /*
                  * The found page may not be always the last
                  * element, even if it's an intermediate
                  * element that needs to be removed and added
                  * to the start of the Queue
                  */
            doublyQueue.remove(page);
            found = true;
        }
        doublyQueue.push(page);
        hashSet.add(page);
        System.out.println("-----------");
        display();
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
            DefaultTableModel tableModel, Deque<String> doublyQueue) {
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
