package cache.CacheTypes;

import java.util.Random;

public class Cache {
	protected int tag;
	protected int offset;
	public static int cacheLines;

	public Cache(int tag, int offset) {
		super();
		this.tag = tag;
		this.offset = offset;
	}

	public void setCacheLines(int cacheLines) {
		this.cacheLines = cacheLines;
	}

	public int getCacheLines() {
		return this.cacheLines;
	}

	public int getTag() {
		return this.tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public int getOffset() {
		return this.offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public String binaryToHex(String binary) {
		int decimal = Integer.parseInt(binary, 2); // Convert binary to decimal
		String hex = Integer.toHexString(decimal); // Convert decimal to hexadecimal
		return hex;
	}

	public static void fillArrayWithTags(String[][] cache, int tagBits) {
		for (int i = 0; i < cache.length; i++) {
			int maxValue = (int) Math.pow(2, tagBits) - 1;
			int binaryValue = (int) (Math.random() * (maxValue + 1));
			String binaryString = Integer.toBinaryString(binaryValue);

			cache[i][0] = binaryString;
		}
	}

	public static void fillArrayWithDummyData(String[][] cache) {
		Random random = new Random();
		for (int i = 0; i < cache.length; i++) {
			// Generate a random number as a string
			String randomNumber = String.valueOf(random.nextInt(1000)); // Change the range as needed
			cache[i][1] = randomNumber;
		}
	}

	public static void printCacheContents(String[][] cache) {
		// Printing the contents of the Cache array for demonstration
		for (int i = 0; i < cache.length; i++) {
			for (int j = 0; j < 2; j++) {
				System.out.print(cache[i][j] + " ");
			}
			System.out.println();
		}
	}

}
