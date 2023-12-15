package cache.CacheTypes;

import java.util.*;

import cache.LRUFullyAssociative;

public class FullyAssociativeCache extends Cache {

	private String[][] faCache;
	public String tagBits, offsetBits;
	LRUFullyAssociative LRU = new LRUFullyAssociative();
	Map<String, String> bits = new HashMap<>();

	public FullyAssociativeCache(int tag, int offset) {
		super(tag, offset);
	}

	public double getMissRate() {
		return Cache.missRate;
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

	public boolean searchAddressFA(String address, int cacheLines) {
		/*
		 * Split the input address String into the Fully Associative Cache structure
		 * (tag, offset)
		 * In order to complete the search we need to compare the tag of the input
		 * address and the tag of all cache lines. The line is indicated
		 * by the splitting of the address based on the structure above.
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

	public Map<String, String> inputAddressAnalysis(String input) {

		int tagLength = super.getTag();
		int offsetLength = super.getOffset();

		tagBits = input.substring(0, tagLength);
		offsetBits = input.substring(tagLength, tagLength + offsetLength);

		bits.put("Tag", tagBits);
		bits.put("Offset", offsetBits);
		return bits;
	}

	public static int returnMemoryBlock(int blockSize, String address) {
		int addressInDecimal = binaryToDecimal(address);
		int memoryBlock = addressInDecimal / blockSize;
		return memoryBlock;
	}
}
