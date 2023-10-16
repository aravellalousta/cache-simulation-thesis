package cache.GUI;

import java.util.*;

public class AddressGenerator {
    /*
     * An array containing all addresses for testing.
     * Includes 2 columns, one for each RAM size.
     * If the RAM size is 128 bytes it means the address is 7 bits long
     * If the RAM size is 256 bytes it means the address is 8 bits long
     */
    public String[][] addressesArray = new String[100][2];

    // Generate random binary addresses with different lengths
    public String[][] generateAddresses() {
        Random random = new Random();

        for (int i = 0; i < addressesArray.length; i++) {
            StringBuilder address7Bits = new StringBuilder();
            StringBuilder address8Bits = new StringBuilder();

            for (int k = 0; k < 7; k++) {
                address7Bits.append(random.nextInt(2)); // Generate random binary digit (0 or 1)
            }

            for (int k = 0; k < 8; k++) {
                address8Bits.append(random.nextInt(2)); // Generate random binary digit (0 or 1)
            }

            addressesArray[i][0] = address7Bits.toString();
            addressesArray[i][1] = address8Bits.toString();
        }

        for (int j = 0; j < 2; j++) {
            if (j == 0) {
                System.out.println("Generating 7 bit addresses");
            } else {
                System.out.println("Generating 8 bit addresses");
            }
            for (int i = 0; i < addressesArray.length; i++) {
                System.out.println(addressesArray[i][j]);

            }
        }

        return addressesArray;
    }
}
