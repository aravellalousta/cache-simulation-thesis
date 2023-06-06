package cache;

import java.util.InputMismatchException;
import java.util.Scanner;

import javax.print.DocPrintJob;

public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int ramSize, cacheSize, blockSize, cacheType = 0, kWays = 0;

		// New scanner object to receive input from the user
		Scanner userInput = new Scanner(System.in);

		System.out.print("Enter RAM Size: ");
		ramSize = userInput.nextInt();

		System.out.println("Select Cache Type:");
		do {
			System.out.println("1. Direct Mapped\n2. Fully Associative\n3. Set Associative");

			try {
				cacheType = userInput.nextInt();

			} catch (InputMismatchException ex) {
				System.out.println("lathos");
				cacheType = 0;
				userInput.nextLine();
				continue;
			}

			if (cacheType == 3) {
				System.out.print("Enter k-way Size: ");
				kWays = userInput.nextInt();
			}
		} while (cacheType != 1 && cacheType != 2 && cacheType != 3);

		System.out.print("Enter Cache Size: ");
		cacheSize = userInput.nextInt();

		System.out.print("Enter Block Size: ");
		blockSize = userInput.nextInt();

		// Creating a new RAM object and proceed with the address analysis
		// based on the type of cache selected above.
		Ram myRam = new Ram();
		myRam.setSize(ramSize);
		myRam.addressAnalysis(ramSize, cacheType, cacheSize, blockSize, kWays);

		userInput.close();
	}

}
