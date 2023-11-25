package cache.GUI;

import java.awt.*;
import javax.swing.*;

public class TabManager extends InitGUI {
    static JPanel directPanel, fullyPanel, setPanel;
    static JTabbedPane tabbedPane = new JTabbedPane();
    static private boolean[] flag = { false, false, false };

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

    public static JTabbedPane createTabs() {
        directPanel = createTab("Direct Mapped Cache");
        fullyPanel = createTab("Fully Associative Cache");
        setPanel = createTab("Set Associative Cache");

        tabbedPane.addTab("Direct Mapped Cache", directPanel);
        tabbedPane.addTab("Fully Associative Cache", fullyPanel);
        tabbedPane.addTab("Set Associative Cache", setPanel);

        stylingTabs(tabbedPane);
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
                    if (i == 0) {
                        displaySelectedTabContents(directPanel, 0);
                    } else if (i == 1) {
                        displaySelectedTabContents(fullyPanel, 1);
                    } else if (i == 2) {
                        displaySelectedTabContents(setPanel, 2);
                    }

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

        // Add the left and right panels to the main panel
        if (flag[index] == false) {
            JPanel leftPanel = PanelConfigurator.configurePanel(index);
            panel.add(leftPanel);
            flag[index] = true;
        }
    }

}
