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

	public void createArrayDM(int cacheLines) {
		// Cache structure is ()
		dmCache = new String[cacheLines][3];
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
		// dmCache[2][1] = "00";

		if (addressBits.get("Tag").equals(dmCache[Integer.parseInt(searchLine) - 1][1])) {
			System.out.println("Hit!");
		} else {
			System.out.println("Miss!");
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

	public String binaryToHex(String binary) {
		int decimal = Integer.parseInt(binary, 2); // Convert binary to decimal
		String hex = Integer.toHexString(decimal); // Convert decimal to hexadecimal
		return hex;
	}

}
