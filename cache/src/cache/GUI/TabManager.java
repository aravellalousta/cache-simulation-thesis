package cache.GUI;

import java.awt.*;
import javax.swing.*;

// TabManager class manages the creation and styling of tabs in the GUI

public class TabManager extends InitGUI {
    // Panels for each type of cache
    static JPanel directPanel, fullyPanel, setPanel;

    // JTabbedPane to hold the tabs
    static JTabbedPane tabbedPane = new JTabbedPane();

    // Flags to track whether the content of each tab has been displayed at least 1
    // time
    static private boolean[] flag = { false, false, false };

    // Get the index of the currently selected tab
    public static int getTabIndex() {
        Component selectedComponent = tabbedPane.getSelectedComponent();
        int index = 0;
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if (tabbedPane.getComponentAt(i) == selectedComponent) {
                index = i;
            }
        }
        return index;
    }

    // Create and configure the tabs
    public static JTabbedPane createTabs() {
        directPanel = createTab();
        fullyPanel = createTab();
        setPanel = createTab();

        tabbedPane.addTab("Direct Mapped Cache", directPanel);
        tabbedPane.addTab("Fully Associative Cache", fullyPanel);
        tabbedPane.addTab("Set Associative Cache", setPanel);

        stylingTabs(tabbedPane);

        // Display the content of the first tab when loading the GUI for the first time
        displaySelectedTabContents(directPanel, 0);

        return tabbedPane;
    }

    public static JPanel getTab(int index) {
        if (index == 0) {
            return directPanel;
        } else if (index == 1) {
            return fullyPanel;
        } else if (index == 3) {
            return setPanel;
        }
        return null;
    }

    private static JPanel createTab() {
        return new JPanel(new GridLayout(1, 2));
    }

    // Apply styling to the tabs (background color, selected tab color)
    public static void stylingTabs(JTabbedPane tabbedPane) {

        Color selectedTabColor = Color.BLUE;

        tabbedPane.setFont(customFont);

        // Add a change listener to handle tab changes
        tabbedPane.addChangeListener(e -> {
            Component selectedComponent = tabbedPane.getSelectedComponent();
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                if (tabbedPane.getComponentAt(i) == selectedComponent) {
                    // Set background color for the selected tab
                    tabbedPane.setBackgroundAt(i, selectedTabColor);
                    // Display the appropriate content for the selected tab
                    if (i == 0) {
                        displaySelectedTabContents(directPanel, 0);
                    } else if (i == 1) {
                        displaySelectedTabContents(fullyPanel, 1);
                    } else if (i == 2) {
                        displaySelectedTabContents(setPanel, 2);
                    }
                } else {
                    // Reset background color for other tabs
                    tabbedPane.setBackgroundAt(i, null);
                }
            }
        });

        // Set background color for the initially selected tab
        tabbedPane.setBackgroundAt(tabbedPane.getSelectedIndex(), selectedTabColor);
    }

    // Display the appropriate UI for each tab
    public static void displaySelectedTabContents(JPanel panel, int index) {
        // Check if the content has already been displayed
        if (flag[index] == false) {
            JPanel tabPanel = PanelConfigurator.configurePanel(index);
            panel.add(tabPanel);
            // Update the flag to indicate that content has been displayed
            flag[index] = true;
        }
    }
}
