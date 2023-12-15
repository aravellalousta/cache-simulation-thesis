package cache.CacheTypes;

import java.util.*;
import javax.swing.table.DefaultTableModel;
import cache.LRUSetAssociative;

public class SetAssociativeCache extends Cache {

	private int set;
	public String tagBits, setBits, offsetBits, searchSet;
	LRUSetAssociative LRU1 = new LRUSetAssociative();
	LRUSetAssociative LRU2 = new LRUSetAssociative();
	LRUSetAssociative LRU3 = new LRUSetAssociative();
	LRUSetAssociative LRU4 = new LRUSetAssociative();

	public SetAssociativeCache(int tag, int set, int offset) {
		super(tag, offset);
		this.set = set;
	}

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

	public boolean searchAddressSA(String address, int kWaysInput, int cacheLines) {
		Map<String, String> addressBits = inputAddressAnalysis(address);
		searchSet = addressBits.get("Set");
		boolean flag = false;

		if (cacheLines == 8) {
			if (kWaysInput == 2 || kWaysInput == 4) {
				LRU1.setCACHE_SIZE(2);
				LRU2.setCACHE_SIZE(2);
				LRU3.setCACHE_SIZE(2);
				LRU4.setCACHE_SIZE(2);

				int setIndex = Integer.parseInt(searchSet, 2);

				if (setIndex == 0) {
					if (LRU1.refer(addressBits.get("Tag"))) {
						Cache.hitCounter++;
						flag = true;
					} else {
						Cache.missCounter++;
						Cache.missRate = calculateMissRate(missCounter, hitCounter);
						flag = false;
					}

				} else if (setIndex == 1) {
					if (LRU2.refer(addressBits.get("Tag"))) {
						Cache.hitCounter++;
						flag = true;
					} else {
						Cache.missCounter++;
						Cache.missRate = calculateMissRate(missCounter, hitCounter);
						flag = false;
					}

				} else if (setIndex == 2) {
					if (LRU3.refer(addressBits.get("Tag"))) {
						Cache.hitCounter++;
						flag = true;
					} else {
						Cache.missCounter++;
						Cache.missRate = calculateMissRate(missCounter, hitCounter);
						flag = false;
					}

				} else if (setIndex == 3) {
					if (LRU4.refer(addressBits.get("Tag"))) {
						Cache.hitCounter++;
						flag = true;
					} else {
						Cache.missCounter++;
						Cache.missRate = calculateMissRate(missCounter, hitCounter);
						flag = false;
					}

				}

			}
		} else if (cacheLines == 4 && kWaysInput == 2) {
			LRU1.setCACHE_SIZE(kWaysInput);
			LRU2.setCACHE_SIZE(kWaysInput);
			int setIndex = Integer.parseInt(searchSet, 2);

			if (setIndex == 0) {
				if (LRU1.refer(addressBits.get("Tag"))) {
					Cache.hitCounter++;
					flag = true;
				} else {
					Cache.missCounter++;
					Cache.missRate = calculateMissRate(missCounter, hitCounter);
					flag = false;
				}
			} else if (setIndex == 1) {
				if (LRU2.refer(addressBits.get("Tag"))) {
					Cache.hitCounter++;
					flag = true;
				} else {
					Cache.missCounter++;
					Cache.missRate = calculateMissRate(missCounter, hitCounter);
					flag = false;
				}
			}
		}
		return flag;
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
		return bits;
	}

	public void updateColumnValues(DefaultTableModel tableModel, int set) {
		int rowCount = tableModel.getRowCount();

		Deque<String> d1 = LRU1.getDoublyQueue();
		Iterator<String> iteratorOfD1 = d1.iterator();

		Deque<String> d2 = LRU2.getDoublyQueue();
		Iterator<String> iteratorOfD2 = d2.iterator();

		Deque<String> d3 = LRU3.getDoublyQueue();
		Iterator<String> iteratorOfD3 = d3.iterator();

		Deque<String> d4 = LRU4.getDoublyQueue();
		Iterator<String> iteratorOfD4 = d4.iterator();

		if (set == 0) {
			for (int row = 0; row < rowCount; row++) {
				if (iteratorOfD1.hasNext()) {
					String newValue = iteratorOfD1.next();
					tableModel.setValueAt(newValue, row, 0);
					int memoryBlock = SetAssociativeCache.returnMemoryBlock(4, newValue);
					tableModel.setValueAt("MemBlock[" + memoryBlock + "]", row, 1);
				}
			}
		} else if (set == 1) {
			for (int row = 2; row < rowCount; row++) {
				if (iteratorOfD2.hasNext()) {
					String newValue = iteratorOfD2.next();
					tableModel.setValueAt(newValue, row, 0);
					int memoryBlock = SetAssociativeCache.returnMemoryBlock(4, newValue);
					tableModel.setValueAt("MemBlock[" + memoryBlock + "]", row, 1);
				}
			}
		} else if (set == 2) {
			for (int row = 4; row < rowCount; row++) {
				if (iteratorOfD3.hasNext()) {
					String newValue = iteratorOfD3.next();
					tableModel.setValueAt(newValue, row, 0);
					int memoryBlock = SetAssociativeCache.returnMemoryBlock(4, newValue);
					tableModel.setValueAt("MemBlock[" + memoryBlock + "]", row, 1);
				}
			}
		} else if (set == 3) {
			for (int row = 6; row < rowCount; row++) {
				if (iteratorOfD4.hasNext()) {
					String newValue = iteratorOfD4.next();
					tableModel.setValueAt(newValue, row, 0);
					int memoryBlock = SetAssociativeCache.returnMemoryBlock(4, newValue);
					tableModel.setValueAt("MemBlock[" + memoryBlock + "]", row, 1);
				}
			}
		}

	}

}
