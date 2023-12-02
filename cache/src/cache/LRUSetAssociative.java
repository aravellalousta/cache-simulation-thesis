package cache;

import java.util.*;
import javax.swing.table.DefaultTableModel;
import cache.CacheTypes.SetAssociativeCache;

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

        return found;
    }

    public void display() {
        Iterator<String> itr = doublyQueue.iterator();
        while (itr.hasNext()) {
            System.out.print(itr.next() + " ");
        }
    }

}
