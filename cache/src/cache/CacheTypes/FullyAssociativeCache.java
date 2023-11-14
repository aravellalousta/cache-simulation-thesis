package cache.CacheTypes;

import java.util.*;

import cache.LRUImplementation;

public class FullyAssociativeCache extends Cache {

	private String[][] faCache;
	public String tagBits, offsetBits;

	public FullyAssociativeCache(int tag, int offset) {
		super(tag, offset);
	}

	public void createArrayFA(int cacheLines) {
		// Cache structure is (tag, data)
		faCache = new String[cacheLines][2];
	}

	public String getTagBits() {
		return this.tagBits;
	}

	public String getOffsetBits() {
		return this.offsetBits;
	}

	public void printFA(Deque<String> doublyQueue) {
		Iterator<String> itr = doublyQueue.iterator();
		while (itr.hasNext()) {
			System.out.print("queue " + itr + itr.next() + " ");
		}
	}

	public boolean searchAddressFA(String address, int cacheLines) {
		/*
		 * Split the input address String into the Fully Associative Cache structure
		 * (tag, offset)
		 * In order to complete the search we need to compare the tag of the input
		 * address and the tag of all cache lines. The line is indicated
		 * by the splitting of the address based on the structure above.
		 */

		LRUImplementation LRU = new LRUImplementation(cacheLines);
		Deque<String> doublyQueue = LRU.getDoublyQueue();

		boolean found = false;

		LRU.refer(address);

		printFA(doublyQueue);
		// LRU.display();

		return true;
	}

	public Map<String, String> inputAddressAnalysis(String input) {

		Map<String, String> bits = new HashMap<>();

		int tagLength = super.getTag();
		int offsetLength = super.getOffset();

		tagBits = input.substring(0, tagLength);
		offsetBits = input.substring(tagLength, tagLength + offsetLength);

		bits.put("Tag", tagBits);
		bits.put("Offset", offsetBits);
		// bits.forEach((key, value) -> System.out.println(key + " " + value));
		return bits;
	}
}
