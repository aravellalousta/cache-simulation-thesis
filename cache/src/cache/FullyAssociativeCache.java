package cache;

import java.util.*;

public class FullyAssociativeCache extends Cache {

	private String[][] faCache;

	public FullyAssociativeCache(int tag, int offset) {
		super(tag, offset);
	}

	public void createArrayFA(int cacheLines) {
		// Cache structure is (tag, data)
		faCache = new String[cacheLines][2];
	}

	public boolean searchAddressFA(String address, int cacheLines) {
		/*
		 * Split the input address String into the Fully Associative Cache structure
		 * (tag, offset)
		 * In order to complete the search we need to compare the tag of the input
		 * address and the tag of all cache lines. The line is indicated
		 * by the splitting of the address based on the structure above.
		 */

		Map<String, String> addressBits = inputAddressAnalysis(address);
		boolean found = false;

		// Example to see if hit works for input address 0001101
		// with ramSize=128, cacheSize=16, blockSize=4
		// faCache[2][0] = "00011";

		for (int i = 0; i < cacheLines; i++) {
			if (addressBits.get("Tag").equals(faCache[i][0])) {
				System.out.println("Hit!");
				found = true;
			}
		}

		if (!found) {
			System.out.println("Miss!");
		}

		return true;
	}

	public Map<String, String> inputAddressAnalysis(String input) {

		Map<String, String> bits = new HashMap<>();

		int tagLength = super.getTag();
		int offsetLength = super.getOffset();

		int totalDigits = String.valueOf(input).length(); // Calculate the total number of digits in the input

		while (totalDigits < tagLength + offsetLength) {
			System.out.println("Error: The sum of variables exceeds the number of digits in the input.");
		}

		String tagBits = input.substring(0, tagLength);
		String offsetBits = input.substring(tagLength, tagLength + offsetLength);

		bits.put("Tag", tagBits);
		bits.put("Offset", offsetBits);
		bits.forEach((key, value) -> System.out.println(key + " " + value));
		return bits;
	}
}
