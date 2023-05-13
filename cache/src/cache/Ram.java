package cache;

public class Ram {
    private int size;

    public void setSize(int size) {
        this.size = size;
    }

    /*
     * This method calculates the bit lengths for the tag, line, set and word
     * components
     * based on the given ramSize, cacheType, cacheSize, and blockSize parameters.
     * It then creates an instance of the specific Cache
     * (Direct/FullyAssociative/SetAssociative) class
     * with the calculated bit lengths.
     */

    public void addressAnalysis(int ramSize, int cacheType, int cacheSize, int blockSize) {
        // calculate the bit length of the address based on the RAM size
        int addressBits = (int) (Math.log(ramSize) / Math.log(2));

        if (cacheType == 1) {
            // for the direct mapped cache the bits structure is ( tag, line, word )
            int wordBits = (int) (Math.log(blockSize) / Math.log(2));
            int cacheLines = (int) (cacheSize / blockSize);
            int lineBits = (int) (Math.log(cacheLines) / Math.log(2));
            int tagBits = addressBits - wordBits - lineBits;

            System.out.println("****Direct Mapped****");
            System.out.println("Tag:" + tagBits);
            System.out.println("Line:" + lineBits);
            System.out.println("Word:" + wordBits);

            new DirectMappedCache(tagBits, lineBits, wordBits);
        }

    }
}
