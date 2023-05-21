package cache;

import java.util.Scanner;

public class Ram {
    private int size, addressBits, offsetBits, cacheLines, lineBits, tagBits, numOfSets, setBits;

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
        Scanner userInput = new Scanner(System.in);
        // calculate the bit length of the address based on the RAM size
        addressBits = (int) (Math.log(ramSize) / Math.log(2));
        cacheLines = (int) (cacheSize / blockSize);

        if (cacheType == 1) {
            // For the Direct Mapped Cache the structure is ( tag, line, word )
            offsetBits = (int) (Math.log(blockSize) / Math.log(2));
            lineBits = (int) (Math.log(cacheLines) / Math.log(2));
            tagBits = addressBits - offsetBits - lineBits;

            // Create a Direct Mapped Cache object based on the structure indicated above
            DirectMappedCache dm = new DirectMappedCache(tagBits, lineBits, offsetBits);

            // Create a matrix to display the status of the cache
            dm.createMatrixDM(cacheLines);

            // Search for an address in the cache
            System.out.println("Enter a valid " + addressBits + " bit address to search in the Direct Mapped Cache.");
            String searchAdd = userInput.nextLine();
            dm.searchAddressDM(searchAdd);

        } else if (cacheType == 2) {
            // For the Fully Associative Cache the structure is ( tag, word )
            offsetBits = (int) (Math.log(blockSize) / Math.log(2));
            tagBits = addressBits - offsetBits;
            new FullyAssociativeCache(tagBits, offsetBits);
        } else if (cacheType == 3) {
            // For the Set Associative Cache the structure is ( tag, set, word )
            offsetBits = (int) (Math.log(blockSize) / Math.log(2));
            cacheLines = (int) (cacheSize / blockSize);
            numOfSets = (int) (cacheLines / kWays);
            setBits = (int) (Math.log(numOfSets) / Math.log(2));
            tagBits = addressBits - offsetBits - setBits;
        }

    }
}