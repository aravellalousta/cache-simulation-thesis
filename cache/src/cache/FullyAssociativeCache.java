package cache;

public class FullyAssociativeCache extends Cache {

	private String[][] faCache;

	public FullyAssociativeCache(int tag, int word) {
		super(tag, word);
	}

	public void createArrayFA(int cacheLines) {
		// Cache structure is (tag, data)
		faCache = new String[cacheLines][2];
	}

}
