package cache;

import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import cache.GUI.InitGUI;

public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SwingUtilities.invokeLater(() -> {
			InitGUI gui = new InitGUI();
			gui.createAndShowGUI();
		});
	}

}
