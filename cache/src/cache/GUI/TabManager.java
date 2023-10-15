package cache.GUI;

import java.awt.*;
import javax.swing.*;

public class TabManager extends InitGUI {

    public static JTabbedPane createTabs() {
        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel directPanel = createTab("Direct Mapped Cache");
        JPanel fullyPanel = createTab("Fully Associative Cache");
        JPanel setPanel = createTab("Set Associative Cache");

        tabbedPane.addTab("Direct Mapped Cache", directPanel);
        tabbedPane.addTab("Fully Associative Cache", fullyPanel);
        tabbedPane.addTab("Set Associative Cache", setPanel);

        stylingTabs(tabbedPane);
        displaySelectedTabContents(directPanel, 0);
        displaySelectedTabContents(fullyPanel, 1);
        displaySelectedTabContents(setPanel, 2);

        return tabbedPane;
    }

    private static JPanel createTab(String title) {
        return new JPanel(new GridLayout(1, 2));
    }

    // Styling for the tabs (background color, selected tab color)
    public static void stylingTabs(JTabbedPane tabbedPane) {
        Color selectedTabColor = Color.BLUE;

        tabbedPane.setFont(customFont);

        tabbedPane.addChangeListener(e -> {
            Component selectedComponent = tabbedPane.getSelectedComponent();
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                if (tabbedPane.getComponentAt(i) == selectedComponent) {
                    tabbedPane.setBackgroundAt(i, selectedTabColor);
                } else {
                    tabbedPane.setBackgroundAt(i, null);
                }
            }
        });

        tabbedPane.setBackgroundAt(tabbedPane.getSelectedIndex(), selectedTabColor);
    }

    /*
     * Methods for displaying the appropriate UI of each tab.
     * Every tab consists of 2 panels, left and right.
     * The left panel includes all options to initialize the simulation.
     * The right panel displays the simulation in action.
     */
    public static void displaySelectedTabContents(JPanel panel, int index) {
        // Left column: Add a nested JPanel with GridLayout for 3 input fields
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        LeftPanelConfigurator.configureLeftPanel(panel, leftPanel, index);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        RightPanelConfigurator.configureRightPanel(rightPanel);

        // Add the left and right panels to the main panel
        panel.add(leftPanel);
        panel.add(rightPanel);

    }
}
