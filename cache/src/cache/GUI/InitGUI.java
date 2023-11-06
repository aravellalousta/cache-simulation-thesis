package cache.GUI;

import javax.swing.*;
import java.awt.*;

public class InitGUI extends JPanel {
    public static final Font customFont = new Font("Arial", Font.BOLD, 16);

    public InitGUI() {
        super(new GridLayout(1, 1));
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Cache Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 650);

        JTabbedPane tabbedPane = TabManager.createTabs();
        frame.add(tabbedPane);

        frame.setVisible(true);
    }
}
