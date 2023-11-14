package cache;

import cache.CacheTypes.*;

public class Ram {
    public int size, addressBits, offsetBits, cacheLines, lineBits, tagBits, numOfSets, setBits, blockSize;
    private String searchAdd;

    public Ram(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }

    public int getLineBits() {
        return this.lineBits;
    }

    public int getTagBits() {
        return this.tagBits;
    }

    public int getOffsetBits() {
        return this.offsetBits;
    }

    public int getBlockSize() {
        return this.blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    /*
     * This method calculates the bit lengths for the tag, line, set and offset
     * components
     * based on the given ramSize, cacheType, cacheSize, and blockSize parameters.
     * It then creates an instance of the specific Cache
     * (Direct/FullyAssociative/SetAssociative) class
     * with the calculated bit lengths.
     */

    public void addressAnalysis(int ramSize, int cacheType, int cacheSize, int blockSize, int kWays) {

        // calculate the bit length of the address based on the RAM size
        addressBits = (int) (Math.log(ramSize) / Math.log(2));
        cacheLines = (int) (cacheSize / blockSize);

        if (cacheType == 0) {
            // For the Direct Mapped Cache the structure is ( tag, line, word )
            offsetBits = (int) (Math.log(blockSize) / Math.log(2));
            lineBits = (int) (Math.log(cacheLines) / Math.log(2));
            tagBits = addressBits - offsetBits - lineBits;

        } else if (cacheType == 1) {
            // For the Fully Associative Cache the structure is ( tag, word )
            offsetBits = (int) (Math.log(blockSize) / Math.log(2));
            tagBits = addressBits - offsetBits;
        } else if (cacheType == 2) {
            // For the Set Associative Cache the structure is ( tag, set, word )
            offsetBits = (int) (Math.log(blockSize) / Math.log(2));
            cacheLines = (int) (cacheSize / blockSize);
            numOfSets = (int) (cacheLines / kWays);
            setBits = (int) (Math.log(numOfSets) / Math.log(2));
            tagBits = addressBits - offsetBits - setBits;
        }

    }

}
