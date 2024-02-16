package cache.CacheTypes;

import java.util.*;
import javax.swing.table.DefaultTableModel;
import cache.LRUSetAssociative;

/*
 * Child Class - Set Associative Cache 
 */
public class SetAssociativeCache extends Cache {

	private int set;
	public String tagBits, setBits, offsetBits, searchSet;
	LRUSetAssociative LRU1 = new LRUSetAssociative();
	LRUSetAssociative LRU2 = new LRUSetAssociative();
	LRUSetAssociative LRU3 = new LRUSetAssociative();
	LRUSetAssociative LRU4 = new LRUSetAssociative();

	// Constructor
	public SetAssociativeCache(int tag, int set, int offset) {
		super(tag, offset);
		this.set = set;
	}

	// Getter methods
	public double getMissRate() {
		return Cache.missRate;
	}

	public int getSet() {
		return this.set;
	}

	public String getTagBits() {
		return this.tagBits;
	}

	public String getSetBits() {
		return this.setBits;
	}

	public String getOffsetBits() {
		return this.offsetBits;
	}

	/*
	 * Search for a specific address in the cache.
	 * The search depends mainly on the cache lines and the k-ways as we create
	 * different LRU instances for every case.
	 */
	public boolean searchAddressSA(String address, int kWaysInput, int cacheLines) {
		/*
		 * Split the input address String into the Set Associative Mapped Cache
		 * structure
		 * (tag, set, offset)
		 * In order to complete the search we need to compare the tag of the input
		 * address and all the tags of a specific set in the cache.
		 */
		Map<String, String> addressBits = inputAddressAnalysis(address);
		searchSet = addressBits.get("Set");
		boolean flag = false;

		if (cacheLines == 8) {
			// In the case where the cache size is 32 bytes and the block size 4 words,
			// depending on the size of the kWays (2/4) we either have 2 or 4 sets.
			if (kWaysInput == 2 || kWaysInput == 4) {
				LRU1.setSetSize(2);
				LRU2.setSetSize(2);
				LRU3.setSetSize(2);
				LRU4.setSetSize(2);

				int setIndex = Integer.parseInt(searchSet, 2);

				if (setIndex == 0) {
					if (LRU1.refer(addressBits.get("Tag"))) {
						Cache.hitCounter++;
						flag = true;
					} else {
						Cache.missCounter++;
						flag = false;
					}
					Cache.missRate = calculateMissRate(missCounter, hitCounter);

				} else if (setIndex == 1) {
					if (LRU2.refer(addressBits.get("Tag"))) {
						Cache.hitCounter++;
						flag = true;
					} else {
						Cache.missCounter++;
						flag = false;
					}
					Cache.missRate = calculateMissRate(missCounter, hitCounter);

				} else if (setIndex == 2) {
					if (LRU3.refer(addressBits.get("Tag"))) {
						Cache.hitCounter++;
						flag = true;
					} else {
						Cache.missCounter++;
						flag = false;
					}
					Cache.missRate = calculateMissRate(missCounter, hitCounter);

				} else if (setIndex == 3) {
					if (LRU4.refer(addressBits.get("Tag"))) {
						Cache.hitCounter++;
						flag = true;
					} else {
						Cache.missCounter++;
						flag = false;
					}
					Cache.missRate = calculateMissRate(missCounter, hitCounter);

				}

			}
			// In the case where the cache size is 16 bytes and the block size 4 words,
			// we can only have the cache split in 2 ways, so we have 2 sets.
		} else if (cacheLines == 4 && kWaysInput == 2) {
			LRU1.setSetSize(kWaysInput);
			LRU2.setSetSize(kWaysInput);
			int setIndex = Integer.parseInt(searchSet, 2);

			if (setIndex == 0) {
				if (LRU1.refer(addressBits.get("Tag"))) {
					Cache.hitCounter++;
					flag = true;
				} else {
					Cache.missCounter++;
					flag = false;
				}
				Cache.missRate = calculateMissRate(missCounter, hitCounter);
			} else if (setIndex == 1) {
				if (LRU2.refer(addressBits.get("Tag"))) {
					Cache.hitCounter++;
					flag = true;
				} else {
					Cache.missCounter++;
					flag = false;
				}
				Cache.missRate = calculateMissRate(missCounter, hitCounter);
			}
		}

		return flag;
	}

	// Calculate the memory block based on the address and block size
	public static int returnMemoryBlock(int blockSize, String address) {
		int addressInDecimal = binaryToDecimal(address);
		int memoryBlock = addressInDecimal / blockSize;
		return memoryBlock;
	}

	// Analyze the input address and extract tag, set, and offset bits
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
		return bits;
	}

	// This method helps with updating the value of each set on the table of the GUI
	public void updateColumnValues(DefaultTableModel tableModel, int set) {
		int lineCount = tableModel.getRowCount();

		// The iterator of each doubly queue provides a way to sequentially access the
		// elements of a collection.
		Deque<String> d1 = LRU1.getDoublyQueue();
		Iterator<String> iteratorOfD1 = d1.iterator();

		Deque<String> d2 = LRU2.getDoublyQueue();
		Iterator<String> iteratorOfD2 = d2.iterator();

		Deque<String> d3 = LRU3.getDoublyQueue();
		Iterator<String> iteratorOfD3 = d3.iterator();

		Deque<String> d4 = LRU4.getDoublyQueue();
		Iterator<String> iteratorOfD4 = d4.iterator();

		// In the method searchAddressSA every set has a size of 2, meaning it takes up
		// 2 lines of the cache. This is why the correspondence is:
		// set 1: lines 1-2
		// set 2: lines 3-4
		// set 3: lines 5-6
		// set 4: lines 7-8
		if (set == 0) {
			for (int line = 0; line < lineCount; line++) {
				// Checks if the queue has a next item
				if (iteratorOfD1.hasNext()) {
					// Saves the item to a new variable and adds it to the specific line of the set
					// in the first column of the table
					String newValue = iteratorOfD1.next();
					tableModel.setValueAt(newValue, line, 0);
					// Setting the value of the second column of the table
					int memoryBlock = SetAssociativeCache.returnMemoryBlock(4, newValue);
					tableModel.setValueAt("MemBlock[" + memoryBlock + "]", line, 1);
				}
			}
		} else if (set == 1) {
			for (int line = 2; line < lineCount; line++) {
				if (iteratorOfD2.hasNext()) {
					String newValue = iteratorOfD2.next();
					tableModel.setValueAt(newValue, line, 0);
					int memoryBlock = SetAssociativeCache.returnMemoryBlock(4, newValue);
					tableModel.setValueAt("MemBlock[" + memoryBlock + "]", line, 1);
				}
			}
		} else if (set == 2) {
			for (int line = 4; line < lineCount; line++) {
				if (iteratorOfD3.hasNext()) {
					String newValue = iteratorOfD3.next();
					tableModel.setValueAt(newValue, line, 0);
					int memoryBlock = SetAssociativeCache.returnMemoryBlock(4, newValue);
					tableModel.setValueAt("MemBlock[" + memoryBlock + "]", line, 1);
				}
			}
		} else if (set == 3) {
			for (int line = 6; line < lineCount; line++) {
				if (iteratorOfD4.hasNext()) {
					String newValue = iteratorOfD4.next();
					tableModel.setValueAt(newValue, line, 0);
					int memoryBlock = SetAssociativeCache.returnMemoryBlock(4, newValue);
					tableModel.setValueAt("MemBlock[" + memoryBlock + "]", line, 1);
				}
			}
		}

	}

}
