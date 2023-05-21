package cache;

public class Cache {
	private int tag, offset, cacheLines;

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

}
