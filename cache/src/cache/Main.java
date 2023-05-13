package cache;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner userInput = new Scanner(System.in);
		
		System.out.println("Enter Cache Size: ");
		int cacheSize = userInput.nextInt();
		
		System.out.println("Enter Block Size: ");
		int blockSize = userInput.nextInt();
		
		Cache mainCache = new Cache(0, 0);
	}

}
