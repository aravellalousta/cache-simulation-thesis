package cache;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JPanel {
    Font customFont = new Font("Arial", Font.BOLD, 14);
    private JPanel directPanel;
    private JPanel fullyPanel;
    private JPanel setPanel;

    public GUI() {
        super(new GridLayout(1, 1));
    }

    public void frameConfiguration() {
        JFrame frame = new JFrame("Cache Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);

        // Create the tabbed pane
        JTabbedPane tabbedPane = tabsConfiguration();

        // Add the tabbed pane to the frame
        frame.add(tabbedPane);

        frame.setVisible(true);
    }

    public JTabbedPane tabsConfiguration() {
        // Create a JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Create content panels for each tab
        directPanel = new JPanel(new GridLayout(1, 2));
        fullyPanel = new JPanel();
        setPanel = new JPanel();

        // Add the content panels to the JTabbedPane with tab titles
        tabbedPane.addTab("Direct Mapped Cache", directPanel);
        tabbedPane.addTab("Fully Associative Cache", fullyPanel);
        tabbedPane.addTab("Set Associative Cache", setPanel);

        // Add styling to the JTabbedPane
        stylingTabs(tabbedPane);

        // Link the tabs to their respective methods
        for (int i = 0; i < 3; i++) {
            if (i == 0) { // Direct Mapped Cache tab is selected
                displaySelectedTabContents(directPanel);
            } else if (i == 1) { // Fully Associative Cache tab is selected
                performFullyAssociativeCacheAction();
            } else if (i == 2) { // Set Associative Cache tab is selected
                performSetAssociativeCacheAction();
            }
        }

        return tabbedPane; // Return the JTabbedPane
    }

    public void stylingTabs(JTabbedPane tabbedPane) {
        Color selectedTabColor = Color.BLUE;

        tabbedPane.setFont(customFont);

        // Add a ChangeListener to the JTabbedPane to change the color of the selected
        // tab
        tabbedPane.addChangeListener(e -> {
            Component selectedComponent = tabbedPane.getSelectedComponent();
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                if (tabbedPane.getComponentAt(i) == selectedComponent) {
                    // Set the background color of the selected tab's content panel
                    tabbedPane.setBackgroundAt(i, selectedTabColor);
                    // Perform specific tab actions here, if needed
                } else {
                    // Reset the background color of other tabs' content panels
                    tabbedPane.setBackgroundAt(i, null);
                }
            }
        });

        // Set the initial selected tab's background color
        tabbedPane.setBackgroundAt(tabbedPane.getSelectedIndex(), selectedTabColor);
    }

    // Add methods for functionality of each tab
    public void displaySelectedTabContents(JPanel panel) {

        panel.setBackground(Color.pink);

        // Left column: Add a nested JPanel with GridLayout for 3 input fields
        JPanel leftPanel = new JPanel(new GridBagLayout());

        // Create GridBagConstraints for left column
        GridBagConstraints leftConstraints = new GridBagConstraints();
        leftConstraints.anchor = GridBagConstraints.WEST;
        leftConstraints.insets = new Insets(5, 5, 5, 5);

        // Create GridBagConstraints for right column
        GridBagConstraints rightConstraints = new GridBagConstraints();
        rightConstraints.anchor = GridBagConstraints.WEST;
        rightConstraints.insets = new Insets(5, 5, 5, 5);

        JLabel leftColumnLabel = new JLabel("Inputs");
        leftColumnLabel.setFont(customFont);

        JLabel ramSizeInputLabel = new JLabel("Enter RAM Size:");
        JTextField ramSizeInput = new JTextField(5);

        JLabel cacheSizeInputLabel = new JLabel("Enter Cache Size:");
        JTextField cacheSizeInput = new JTextField(5);

        JLabel blockSizeInputLabel = new JLabel("Enter Block Size:");
        JTextField blockSizeInput = new JTextField(5);

        JButton submitBtn = new JButton("Submit");

        leftConstraints.gridx = 0;
        leftConstraints.gridy = 0;
        leftPanel.add(leftColumnLabel, leftConstraints);

        leftConstraints.gridx = 0;
        leftConstraints.gridy = 1;
        leftPanel.add(ramSizeInputLabel, leftConstraints);

        leftConstraints.gridx = 0;
        leftConstraints.gridy = 2;
        leftPanel.add(cacheSizeInputLabel, leftConstraints);

        leftConstraints.gridx = 0;
        leftConstraints.gridy = 3;
        leftPanel.add(blockSizeInputLabel, leftConstraints);

        leftConstraints.gridx = 1;
        leftConstraints.gridy = 1;
        leftPanel.add(ramSizeInput, leftConstraints);

        leftConstraints.gridx = 1;
        leftConstraints.gridy = 2;
        leftPanel.add(cacheSizeInput, leftConstraints);

        leftConstraints.gridx = 1;
        leftConstraints.gridy = 3;
        leftPanel.add(blockSizeInput, leftConstraints);

        leftConstraints.gridx = 1;
        leftConstraints.gridy = 4;
        leftPanel.add(submitBtn, leftConstraints);

        // Right column: Add a progress bar
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(50);

        // Add the left and right panels to the main panel
        panel.add(leftPanel);
        panel.add(progressBar);

    }

    public void performFullyAssociativeCacheAction() {
        // Add your Fully Associative Cache functionality here
        System.out.println("Fully Associative Cache Action Performed");
    }

    public void performSetAssociativeCacheAction() {
        // Add your Set Associative Cache functionality here
        System.out.println("Set Associative Cache Action Performed");
    }

}
