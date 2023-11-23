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
        // System.out.println("Refering page : " + page + " to see if it exists");
        boolean found;
        if (!hashSet.contains(page)) {
            // System.out.println("Page doesn't exist \n");
            if (doublyQueue.size() == CACHE_SIZE) {
                String last = doublyQueue.removeLast();
                hashSet.remove(last);
            }
            found = false;
            // System.out.println("MISS");
        } else { /*
                  * The found page may not be always the last
                  * element, even if it's an intermediate
                  * element that needs to be removed and added
                  * to the start of the Queue
                  */
            // System.out.println("Page exists \n");

            doublyQueue.remove(page);
            found = true;
            // System.out.println("HIT");
        }

        System.out.println("Adding the page");
        doublyQueue.push(page);
        hashSet.add(page);
        display();

        System.out.println("\n---------------------");
        return found;
    }

    // display contents of cache
    public void display() {
        Iterator<String> itr = doublyQueue.iterator();
        while (itr.hasNext()) {
            System.out.print(itr.next() + " ");
        }
    }

    public static void updateColumnValues(FullyAssociativeCache faCache, DefaultTableModel tableModel) {
        int rowCount = tableModel.getRowCount();
        Iterator<String> iterator = doublyQueue.iterator();
        int i = 0;
        for (int row = 0; row < rowCount; row++) {
            if (iterator.hasNext()) {
                String newValue = iterator.next();
                // faCache.inputAddressAnalysis(newValue);
                String tagBits = faCache.getTagBits();
                System.out.println("NEW VALUE: " + i + "is" + newValue);

                System.out.println("TAGBITS: " + tagBits);

                i++;
                tableModel.setValueAt(newValue, row, 0);

                int memoryBlock = FullyAssociativeCache.returnMemoryBlock(4, newValue);
                tableModel.setValueAt("MemBlock[" + memoryBlock + "]", row, 1);
            }
        }
        System.out.println("\n---------------------");

    }

}
