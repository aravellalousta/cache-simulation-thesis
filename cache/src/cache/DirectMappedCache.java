package cache;

public class DirectMappedCache extends Cache {

	private int line;
	private int validBit;

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

	public int getValidBit() {
		return this.validBit;
	}

	public void setValidBit(int validBit) {
		this.validBit = validBit;
	}

	public void createMatrixDM(int cacheLines) {

		// TODO

		int[][] dmCache = new int[cacheLines][4]; // 4 columns for index, valid bit, tag and data
	}

	public boolean searchAddressDM(String address) {

		// TODO

		inputAddressAnalysis(address);
		return true;
	}

	public void inputAddressAnalysis(String input) {
		int tagLength = super.getTag();
		int lineLength = getLine();
		int offsetLength = super.getOffset();

		int totalDigits = String.valueOf(input).length(); // Calculate the total number of digits in the input

		if (totalDigits < tagLength + lineLength + offsetLength) {
			System.out.println("Error: The sum of variables exceeds the number of digits in the input.");
		} else {
			String tagBits = input.substring(0, tagLength);
			String lineBits = input.substring(tagLength, tagLength + lineLength);
			String offsetBits = input.substring(tagLength + lineLength, tagLength + lineLength + offsetLength);

			System.out.println("Tag: " + tagBits);
			System.out.println("Line: " + lineBits);
			System.out.println("Offset: " + offsetBits);
		}
	}

}
