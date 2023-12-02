package cache.CacheTypes;

import java.util.HashMap;
import java.util.Map;

import cache.LRUSetAssociative;

public class SetAssociativeCache extends Cache {

	private int set;
	public String[][] saCache;
	// = {
	// { "0000", "01100000" },
	// { "0000", "00100000" },
	// { "0110", "10010000" },
	// { "0000", "01100000" },
	// { "1100", "00100000" },
	// { "0100", "10000000" },
	// { "1000", "01110000" },
	// { "0000", "00100000" },
	// };
	public String tagBits, setBits, offsetBits, searchSet;
	LRUSetAssociative LRU1 = new LRUSetAssociative();
	LRUSetAssociative LRU2 = new LRUSetAssociative();
	LRUSetAssociative LRU3 = new LRUSetAssociative();
	LRUSetAssociative LRU4 = new LRUSetAssociative();

	public SetAssociativeCache(int tag, int set, int offset) {
		super(tag, offset);
		this.set = set;
	}

	public int getSet() {
		return this.set;
	}

	public void setSet(int set) {
		this.set = set;
	}

	public String[][] getSaCache() {
		return this.saCache;
	}

	public void setSaCache(String[][] saCache) {
		this.saCache = saCache;
	}

	public String getTagBits() {
		return this.tagBits;
	}

	public void setTagBits(String tagBits) {
		this.tagBits = tagBits;
	}

	public String getSetBits() {
		return this.setBits;
	}

	public void setSetBits(String setBits) {
		this.setBits = setBits;
	}

	public String getOffsetBits() {
		return this.offsetBits;
	}

	public void setOffsetBits(String offsetBits) {
		this.offsetBits = offsetBits;
	}

	public void createArraySA(int cacheLines) {
		// Cache structure is (tag, data)
		saCache = new String[cacheLines][2];
	}

	public void printSA(String[][] saCache) {
		for (int i = 0; i < saCache.length; i++) {
			System.out.println(saCache[i][0]);
		}
	}

	public boolean searchAddressSA(String address, int kWaysInput, int cacheLines) {
		Map<String, String> addressBits = inputAddressAnalysis(address);
		searchSet = addressBits.get("Set");

		if (cacheLines == 8) {
			if (kWaysInput == 2 || kWaysInput == 4) {
				LRU1.setCACHE_SIZE(kWaysInput);
				LRU2.setCACHE_SIZE(kWaysInput);
				LRU3.setCACHE_SIZE(kWaysInput);
				LRU4.setCACHE_SIZE(kWaysInput);

				int setIndex = Integer.parseInt(searchSet, 2);

				if (setIndex == 0) {
					System.out.println("\nbazw stin lru1");
					if (LRU1.refer(addressBits.get("Tag"))) {
						return true;
					} else {
						return false;
					}

				} else if (setIndex == 1) {
					System.out.println("\nbazw stin lru2");
					if (LRU2.refer(addressBits.get("Tag"))) {
						return true;
					} else {
						return false;
					}

				} else if (setIndex == 2) {
					System.out.println("\nbazw stin lru3");
					if (LRU3.refer(addressBits.get("Tag"))) {
						return true;
					} else {
						return false;
					}

				} else if (setIndex == 3) {
					System.out.println("\nbazw stin lru4");
					if (LRU4.refer(addressBits.get("Tag"))) {
						return true;
					} else {
						return false;
					}

				}

			}
		} else if (cacheLines == 4 && kWaysInput == 2) {
			LRU1.setCACHE_SIZE(kWaysInput);
			LRU2.setCACHE_SIZE(kWaysInput);
			int setIndex = Integer.parseInt(searchSet, 2);

			if (setIndex == 0) {
				System.out.println("\nbazw stin lru1");
				if (LRU1.refer(addressBits.get("Tag"))) {
					return true;
				} else {
					return false;
				}
			} else if (setIndex == 1) {
				System.out.println("\nbazw stin lru2");

				if (LRU2.refer(addressBits.get("Tag"))) {
					return true;
				} else {
					return false;
				}
			}
		}

		return false;
	}

	public static int returnMemoryBlock(int blockSize, String address) {
		int addressInDecimal = binaryToDecimal(address);
		int memoryBlock = addressInDecimal / blockSize;
		return memoryBlock;
	}

	public Map<String, String> inputAddressAnalysis(String input) {

		Map<String, String> bits = new HashMap<>();

		int tagLength = super.getTag();
		int setLength = getSet();
		int offsetLength = super.getOffset();

		tagBits = input.substring(0, tagLength);
		setBits = input.substring(tagLength, tagLength + setLength);

		offsetBits = input.substring(tagLength + setLength, tagLength + setLength + offsetLength);

		bits.put("Tag", tagBits);
		bits.put("Set", setBits);
		bits.put("Offset", offsetBits);
		// bits.forEach((key, value) -> System.out.println(key + " " + value));
		return bits;
	}

}
