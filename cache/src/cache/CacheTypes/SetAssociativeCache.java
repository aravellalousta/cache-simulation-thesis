package cache.CacheTypes;

import java.util.HashMap;
import java.util.Map;

public class SetAssociativeCache extends Cache {

	private int set;
	public String[][] saCache = {
			{ "0000011", "01100000" },
			{ "0000010", "00100000" },
			{ "0110010", "10010000" },
			{ "0000110", "01100000" },
			{ "1100000", "00100000" },
			{ "0100110", "10000000" },
			{ "1000100", "01110000" },
			{ "0000010", "00100000" },
	};
	public String tagBits, setBits, offsetBits, searchSet;

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
		searchSet = binaryToHexString(addressBits.get("Set"));
		System.out.println("kways pou lamvanw: " + kWaysInput);
		System.out.println("-----------------\n");

		if (cacheLines == 8) {
			if (kWaysInput == 2 || kWaysInput == 4) {
				int setIndex = Integer.parseInt(searchSet, 2);
				int baseLine = (kWaysInput == 2) ? setIndex * 2 : 0;
				int endLine = baseLine + kWaysInput - 1;

				for (int i = baseLine; i <= endLine; i++) {
					if (addressBits.get("Tag").equals(saCache[i][0])) {
						System.out.println("FOUND IT");
						return true;
					}
				}
			}
		} else if (cacheLines == 4 && kWaysInput == 2) {
			System.out.println("mpainw!");
			int setIndex = Integer.parseInt(searchSet, 2);
			int baseLine = setIndex * 2;
			int endLine = baseLine + kWaysInput - 1;

			for (int i = baseLine; i <= endLine; i++) {
				if (addressBits.get("Tag").equals(saCache[i][0])) {
					System.out.println("FOUND IT");
					return true;
				}
			}
		}

		return false;
	}

	public int returnMemoryBlock(int blockSize, String address) {
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
