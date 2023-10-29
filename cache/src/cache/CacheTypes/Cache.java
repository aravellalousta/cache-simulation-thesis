package cache.CacheTypes;

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

	public String binaryToHexString(String binary) {
		int decimal = Integer.parseInt(binary, 2); // Convert binary to decimal
		String hex = Integer.toHexString(decimal); // Convert decimal to hexadecimal
		return hex;
	}

	public static int binaryToDecimal(String binary) {
		int decimal = Integer.parseInt(binary, 2);
		return decimal;
	}

}
