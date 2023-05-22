package cache;

import java.util.*;

public class DirectMappedCache extends Cache {

	private int line;
	private int validBit;

	public DirectMappedCache(int tag, int line, int offset) {
		super(tag, offset);
		this.line = line;
	}

	public int getLine() {
		return this.line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getValidBit() {
		return this.validBit;
	}

	public void setValidBit(int validBit) {
		this.validBit = validBit;
	}

	public void createMatrixDM(int cacheLines) {
		int[][] dmCache = new int[cacheLines][3]; // 3 columns for valid bit, tag and data
	}

	public boolean searchAddressDM(String address) {
		/*
		 * Split the input address String into the Direct Mapped Cache structure
		 * (tag, line, offset)
		 * In order to complete the search we need to compare the tag of the input
		 * address
		 * and the tag of a specific cache line.
		 */

		Map<String, String> addressBits = inputAddressAnalysis(address);

		// definitely not working will check later
		if (addressBits.get("Tag").equals(dmCache[Integer.parseInt(addressBits.get("Line"))][1])) {

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
		// bits.forEach((key, value) -> System.out.println(key + " " + value));
		return bits;
	}

}
