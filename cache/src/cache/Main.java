package cache;

import javax.swing.SwingUtilities;
import cache.GUI.InitGUI;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			InitGUI gui = new InitGUI();
			gui.createAndShowGUI();
		});
	}

}
