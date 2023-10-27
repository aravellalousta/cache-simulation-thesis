package cache;

import java.util.Scanner;

import javax.swing.table.DefaultTableModel;

import cache.CacheTypes.*;
import cache.CacheTypes.FullyAssociativeCache;

public class Ram {
    public int size, addressBits, offsetBits, cacheLines, lineBits, tagBits, numOfSets, setBits;
    private String searchAdd;

    public Ram(int size) {
        this.size = size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }

    public int getAddressBits() {
        return this.addressBits;
    }

    public void setAddressBits(int addressBits) {
        this.addressBits = addressBits;
    }

    public int getOffsetBits() {
        return this.offsetBits;
    }

    public void setOffsetBits(int offsetBits) {
        this.offsetBits = offsetBits;
    }

    public int getCacheLines() {
        return this.cacheLines;
    }

    public void setCacheLines(int cacheLines) {
        this.cacheLines = cacheLines;
    }

    public int getLineBits() {
        return this.lineBits;
    }

    public void setLineBits(int lineBits) {
        this.lineBits = lineBits;
    }

    public int getTagBits() {
        return this.tagBits;
    }

    public void setTagBits(int tagBits) {
        this.tagBits = tagBits;
    }

    public int getNumOfSets() {
        return this.numOfSets;
    }

    public void setNumOfSets(int numOfSets) {
        this.numOfSets = numOfSets;
    }

    public int getSetBits() {
        return this.setBits;
    }

    public void setSetBits(int setBits) {
        this.setBits = setBits;
    }

    public String getSearchAdd() {
        return this.searchAdd;
    }

    public void setSearchAdd(String searchAdd) {
        this.searchAdd = searchAdd;
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
        cacheLines = (int) (cacheSize / blockSize);

        if (cacheType == 0) {
            // For the Direct Mapped Cache the structure is ( tag, line, word )
            offsetBits = (int) (Math.log(blockSize) / Math.log(2));
            lineBits = (int) (Math.log(cacheLines) / Math.log(2));
            tagBits = addressBits - offsetBits - lineBits;

            // Create a Direct Mapped Cache object based on the structure indicated above
            DirectMappedCache dm = new DirectMappedCache(tagBits, lineBits, offsetBits);

            // Creating the Cache array
            dm.createArrayDM(cacheLines);

            // Search for an address in the cache
            // searchAdd = checkAddressInput(addressBits);
            // dm.searchAddressDM(searchAdd);

        } else if (cacheType == 1) {
            // For the Fully Associative Cache the structure is ( tag, word )
            offsetBits = (int) (Math.log(blockSize) / Math.log(2));
            tagBits = addressBits - offsetBits;

            // Create a Fully Associative Cache object based on the structure indicated
            // above
            FullyAssociativeCache fa = new FullyAssociativeCache(tagBits, offsetBits);

            // Creating the Cache array
            fa.createArrayFA(cacheLines);

            // Search for an address in the cache
            fa.searchAddressFA(searchAdd, cacheLines);

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
