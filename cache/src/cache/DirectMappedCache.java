package cache;

import java.util.*;

public class DirectMappedCache extends Cache {

	private int line;
	private int validBit;
	private String[][] dmCache;

	public DirectMappedCache(int tag, int line, int offset) {
		super(tag, offset);
		this.line = line;
	}

	public int getLine() {
		return this.line;
	}

	public int getValidBit() {
		return this.validBit;
	}

	public void setValidBit(int validBit) {
		this.validBit = validBit;
	}

	public void createArrayDM(int cacheLines, int tagBits) {
		// Cache structure is (tag, data)
		dmCache = new String[cacheLines][2];
		fillArrayWithTags(dmCache, tagBits);
		fillArrayWithDummyData(dmCache);
		printCacheContents(dmCache);
	}

	public boolean searchAddressDM(String address) {
		/*
		 * Split the input address String into the Direct Mapped Cache structure
		 * (tag, line, offset)
		 * In order to complete the search we need to compare the tag of the input
		 * address and the tag of a specific cache line. The line is indicated
		 * by the splitting of the address based on the structure above.
		 */

		Map<String, String> addressBits = inputAddressAnalysis(address);

		// In order to find the line we need to convert the bits to hex value
		String searchLine = binaryToHex(addressBits.get("Line"));

		// Example to see if hit works for input address 0001101
		// with ramSize=128, cacheSize=16, blockSize=4

		// dmCache[2][0] = "000";

		if (addressBits.get("Tag").equals(dmCache[Integer.parseInt(searchLine)][0])) {
			System.out.println("Hit!");
			// sto hit den xreiazetai kati parapanw
		} else {
			System.out.println("Miss!");
			dmCache[Integer.parseInt(searchLine)][0] = addressBits.get("Tag");
			// sto miss thelei politiki antikatastasis
		}

		return true;
	}

	public Map<String, String> inputAddressAnalysis(String input) {

		Map<String, String> bits = new HashMap<>();

		int tagLength = super.getTag();
		int lineLength = getLine();
		int offsetLength = super.getOffset();

		int totalDigits = String.valueOf(input).length(); // Calculate the total number of digits in the input

		while (totalDigits < tagLength + lineLength + offsetLength) {
			System.out.println("Error: The sum of variables exceeds the number of digits in the input.");
		}

		String tagBits = input.substring(0, tagLength);
		String lineBits = input.substring(tagLength, tagLength + lineLength);
		String offsetBits = input.substring(tagLength + lineLength, tagLength + lineLength + offsetLength);

		bits.put("Tag", tagBits);
		bits.put("Line", lineBits);
		bits.put("Offset", offsetBits);
		bits.forEach((key, value) -> System.out.println(key + " " + value));
		return bits;
	}

}
