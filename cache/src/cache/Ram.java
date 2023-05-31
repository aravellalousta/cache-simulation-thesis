package cache;

import java.util.Scanner;

public class Ram {
    private int size, addressBits, offsetBits, cacheLines, lineBits, tagBits, numOfSets, setBits;
    private String searchAdd;

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
        cacheLines = (int) (cacheSize / blockSize);

        if (cacheType == 1) {
            // For the Direct Mapped Cache the structure is ( tag, line, word )
            offsetBits = (int) (Math.log(blockSize) / Math.log(2));
            lineBits = (int) (Math.log(cacheLines) / Math.log(2));
            tagBits = addressBits - offsetBits - lineBits;

            // Create a Direct Mapped Cache object based on the structure indicated above
            DirectMappedCache dm = new DirectMappedCache(tagBits, lineBits, offsetBits);

            // Creating the Cache array
            dm.createArrayDM(cacheLines);

            // Search for an address in the cache
            searchAdd = checkAddressInput(addressBits);
            dm.searchAddressDM(searchAdd);

        } else if (cacheType == 2) {
            // For the Fully Associative Cache the structure is ( tag, word )
            offsetBits = (int) (Math.log(blockSize) / Math.log(2));
            tagBits = addressBits - offsetBits;

            // Create a Fully Associative Cache object based on the structure indicated
            // above
            FullyAssociativeCache fa = new FullyAssociativeCache(tagBits, offsetBits);

            // Creating the Cache array
            fa.createArrayFA(cacheLines);

            // Search for an address in the cache
            searchAdd = checkAddressInput(addressBits);

        } else if (cacheType == 3) {
            // For the Set Associative Cache the structure is ( tag, set, word )
            offsetBits = (int) (Math.log(blockSize) / Math.log(2));
            cacheLines = (int) (cacheSize / blockSize);
            numOfSets = (int) (cacheLines / kWays);
            setBits = (int) (Math.log(numOfSets) / Math.log(2));
            tagBits = addressBits - offsetBits - setBits;
        }

    }

    public String checkAddressInput(int addressBits) {
        Scanner userInput = new Scanner(System.in);

        System.out.println("Enter a valid " + addressBits + " bit address to search in the Cache.");
        String searchAdd = userInput.nextLine();
        while (searchAdd.length() != addressBits) {
            System.out.println("Error. Please enter an address that is exactly " + addressBits + " bits long.");
            searchAdd = userInput.nextLine();
        }

        userInput.close();
        return searchAdd;
    }
}
