package cache;

public class DirectMappedCache extends Cache {

	private int line;

	public DirectMappedCache(int tag, int line, int word) {
		super(tag, word);
		this.line = line;
	}

}
