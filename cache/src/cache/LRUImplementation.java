package cache;

import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class LRUImplementation {

    // store keys of cache
    private Deque<String> doublyQueue;

    public Deque<String> getDoublyQueue() {
        return this.doublyQueue;
    }

    // store references of key in cache
    private HashSet<String> hashSet;

    // maximum capacity of cache
    private final int CACHE_SIZE;

    public LRUImplementation(int capacity) {
        doublyQueue = new LinkedList<>();
        hashSet = new HashSet<>();
        CACHE_SIZE = capacity;
    }

    /* Refer the page within the LRU cache */
    public void refer(String page) {
        if (!hashSet.contains(page)) {
            if (doublyQueue.size() == CACHE_SIZE) {
                String last = doublyQueue.removeLast();
                hashSet.remove(last);
            }
        } else { /*
                  * The found page may not be always the last
                  * element, even if it's an intermediate
                  * element that needs to be removed and added
                  * to the start of the Queue
                  */
            doublyQueue.remove(page);
        }
        doublyQueue.push(page);
        hashSet.add(page);
    }

    // display contents of cache
    public void display() {
        Iterator<String> itr = doublyQueue.iterator();
        while (itr.hasNext()) {
            System.out.print(itr.next() + " ");
        }
    }
}
