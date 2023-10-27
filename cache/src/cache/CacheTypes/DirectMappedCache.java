package cache.CacheTypes;

import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DirectMappedCache extends Cache {

	private int line;
	private String[][] dmCache;
	private String tagBits, lineBits, offsetBits;

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

	public String[][] getDmCache() {
		return this.dmCache;
	}

	public void setDmCache(String[][] dmCache) {
		this.dmCache = dmCache;
	}

	public String getTagBits() {
		return this.tagBits;
	}

	public void setTagBits(String tagBits) {
		this.tagBits = tagBits;
	}

	public String getLineBits() {
		return this.lineBits;
	}

	public void setLineBits(String lineBits) {
		this.lineBits = lineBits;
	}

	public String getOffsetBits() {
		return this.offsetBits;
	}

	public void setOffsetBits(String offsetBits) {
		this.offsetBits = offsetBits;
	}

	public void createArrayDM(int cacheLines) {
		// Cache structure is (tag, data)
		dmCache = new String[cacheLines][2];
	}

	public void printDM(String[][] dmCache) {
		for (int i = 0; i < dmCache.length; i++) {
			System.out.println(dmCache[i][0]);
		}

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
		printDM(dmCache);

		if (addressBits.get("Tag").equals(dmCache[Integer.parseInt(searchLine)][0])) {
			System.out.println("Hit!");
			return true;
		} else {
			System.out.println("Miss!");
			dmCache[Integer.parseInt(searchLine)][0] = addressBits.get("Tag");
			return false;
		}

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

		tagBits = input.substring(0, tagLength);
		lineBits = input.substring(tagLength, tagLength + lineLength);
		offsetBits = input.substring(tagLength + lineLength, tagLength + lineLength + offsetLength);

		bits.put("Tag", tagBits);
		bits.put("Line", lineBits);
		bits.put("Offset", offsetBits);
		// bits.forEach((key, value) -> System.out.println(key + " " + value));
		return bits;
	}

}
