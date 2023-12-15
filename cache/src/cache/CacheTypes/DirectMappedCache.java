package cache.CacheTypes;

import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import cache.Ram;

public class DirectMappedCache extends Cache {

	public static int line;
	public String[][] dmCache;
	public String tagBits, lineBits, offsetBits;
	Ram myRam;
	static String searchLine;

	public DirectMappedCache(int tag, int line, int offset) {
		super(tag, offset);
		this.line = line;
	}

	public double getMissRate() {
		return Cache.missRate;
	}

	public int getLine() {
		return this.line;
	}

	public String getTagBits() {
		return this.tagBits;
	}

	public String getLineBits() {
		return this.lineBits;
	}

	public String getOffsetBits() {
		return this.offsetBits;
	}

	public String getSearchLine() {
		return this.searchLine;
	}

	public void createArrayDM(int cacheLines) {
		// Cache structure is (tag, data)
		dmCache = new String[cacheLines][2];
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
		boolean flag;

		// In order to find the line we need to convert the bits to hex value
		searchLine = binaryToHexString(addressBits.get("Line"));

		if (addressBits.get("Tag").equals(dmCache[Integer.parseInt(searchLine)][0])) {
			Cache.hitCounter++;
			flag = true;
		} else {
			Cache.missCounter++;
			dmCache[Integer.parseInt(searchLine)][0] = addressBits.get("Tag");
			flag = false;
		}
		Cache.missRate = calculateMissRate(missCounter, hitCounter);

		return flag;

	}

	public int returnMemoryBlock(int blockSize, String address) {
		int addressInDecimal = binaryToDecimal(address);
		int memoryBlock = addressInDecimal / blockSize;
		return memoryBlock;
	}

	public Map<String, String> inputAddressAnalysis(String input) {

		Map<String, String> bits = new HashMap<>();

		int tagLength = super.getTag();
		int lineLength = getLine();
		int offsetLength = super.getOffset();

		tagBits = input.substring(0, tagLength);
		lineBits = input.substring(tagLength, tagLength + lineLength);
		offsetBits = input.substring(tagLength + lineLength, tagLength + lineLength + offsetLength);

		bits.put("Tag", tagBits);
		bits.put("Line", lineBits);
		bits.put("Offset", offsetBits);
		return bits;
	}

}
