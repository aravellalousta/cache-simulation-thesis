package cache.CacheTypes;

import java.util.*;

import cache.LRUImplementation;

public class FullyAssociativeCache extends Cache {

	private String[][] faCache;
	public String tagBits, offsetBits;
	LRUImplementation LRU = new LRUImplementation();

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
		System.out.println("QUEUE STATUS IS: ");
		while (itr.hasNext()) {
			System.out.print(itr.next() + " ");
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

		LRU.setCACHE_SIZE(cacheLines);
		Deque<String> doublyQueue = LRU.getDoublyQueue();
		printFA(doublyQueue);

		if (LRU.refer(address)) {
			return false;
		} else {
			return true;
		}

		// LRU.display();

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

	public static int returnMemoryBlock(int blockSize, String address) {
		int addressInDecimal = binaryToDecimal(address);
		int memoryBlock = addressInDecimal / blockSize;
		return memoryBlock;
	}
}
