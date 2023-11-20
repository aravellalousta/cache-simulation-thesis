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

public class LRUImplementation {

    // store keys of cache
    private static Deque<String> doublyQueue;
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

    public LRUImplementation() {
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
            System.out.println("\n miss");
        } else { /*
                  * The found page may not be always the last
                  * element, even if it's an intermediate
                  * element that needs to be removed and added
                  * to the start of the Queue
                  */
            doublyQueue.remove(page);
            found = true;
            System.out.println("\n hit");
        }
        doublyQueue.push(page);
        hashSet.add(page);

        return found;
    }

    // display contents of cache
    public void display() {
        Iterator<String> itr = doublyQueue.iterator();
        while (itr.hasNext()) {
            System.out.print(itr.next() + " ");
        }
    }

    public static void updateColumnValues(FullyAssociativeCache faCache, DefaultTableModel tableModel,
            int columnIndex) {
        int rowCount = tableModel.getRowCount();
        Iterator<String> iterator = doublyQueue.iterator();

        for (int row = 0; row < rowCount; row++) {
            if (iterator.hasNext()) {
                String newValue = iterator.next();
                faCache.inputAddressAnalysis(newValue);
                String tagBits = faCache.getTagBits();

                tableModel.setValueAt(tagBits, row, columnIndex);
            }
        }
    }

}