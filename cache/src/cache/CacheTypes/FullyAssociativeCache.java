package cache.CacheTypes;

import java.util.*;

import cache.LRUFullyAssociative;
/*
 * Child Class - Fully Associative Cache 
 */

public class FullyAssociativeCache extends Cache {

	private String[][] faCache;
	public String tagBits, offsetBits;
	LRUFullyAssociative LRU = new LRUFullyAssociative();
	Map<String, String> bits = new HashMap<>();

	// Constructor
	public FullyAssociativeCache(int tag, int offset) {
		super(tag, offset);
	}

	// Getter Methods
	public double getMissRate() {
		return Cache.missRate;
	}

	public String getTagBits() {
		return this.tagBits;
	}

	public String getOffsetBits() {
		return this.offsetBits;
	}

	// Initialize faCache array
	public void createArrayFA(int cacheLines) {
		// Cache structure is (tag, data)
		faCache = new String[cacheLines][2];
	}

	// Search for a specific address
	public boolean searchAddressFA(String address, int cacheLines) {
		/*
		 * Split the input address String into the Fully Associative Cache structure
		 * (tag, offset)
		 * In order to complete the search we need to compare the tag of the input
		 * address and the tag of all cache lines. We use LRU as the replacement
		 * algorithm.
		 */

		boolean flag;
		LRU.setCACHE_SIZE(cacheLines);

		String tagBits = bits.get("Tag");

		if (LRU.refer(tagBits)) {
			Cache.hitCounter++;
			flag = true;
		} else {
			Cache.missCounter++;
			flag = false;
		}
		Cache.missRate = calculateMissRate(missCounter, hitCounter);

		return flag;

	}

	// Analyze the input address and extract tag and offset bits
	public Map<String, String> inputAddressAnalysis(String input) {

		int tagLength = super.getTag();
		int offsetLength = super.getOffset();

		tagBits = input.substring(0, tagLength);
		offsetBits = input.substring(tagLength, tagLength + offsetLength);

		bits.put("Tag", tagBits);
		bits.put("Offset", offsetBits);
		return bits;
	}

	// Calculate the memory block based on the address and block size
	public static int returnMemoryBlock(int blockSize, String address) {
		int addressInDecimal = binaryToDecimal(address);
		int memoryBlock = addressInDecimal / blockSize;
		return memoryBlock;
	}
}
