package cache;

public class Ram {
    private int size, addressBits, wordBits, cacheLines, lineBits, tagBits, numOfSets, setBits;

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

    public void addressAnalysis(int ramSize, int cacheType, int cacheSize, int blockSize, int kWays) {
        // calculate the bit length of the address based on the RAM size
        addressBits = (int) (Math.log(ramSize) / Math.log(2));

        if (cacheType == 1) {
            // For the Direct Mapped Cache the structure is ( tag, line, word )
            wordBits = (int) (Math.log(blockSize) / Math.log(2));
            cacheLines = (int) (cacheSize / blockSize);
            lineBits = (int) (Math.log(cacheLines) / Math.log(2));
            tagBits = addressBits - wordBits - lineBits;
            new DirectMappedCache(tagBits, lineBits, wordBits);
        } else if (cacheType == 2) {
            // For the Fully Associative Cache the structure is ( tag, word )
            wordBits = (int) (Math.log(blockSize) / Math.log(2));
            tagBits = addressBits - wordBits;
            new FullyAssociativeCache(tagBits, wordBits);
        } else if (cacheType == 3) {
            // For the Set Associative Cache the structure is ( tag, set, word )
            wordBits = (int) (Math.log(blockSize) / Math.log(2));
            cacheLines = (int) (cacheSize / blockSize);
            numOfSets = (int) (cacheLines / kWays);
            setBits = (int) (Math.log(numOfSets) / Math.log(2));
            tagBits = addressBits - wordBits - setBits;
        }

    }
}
