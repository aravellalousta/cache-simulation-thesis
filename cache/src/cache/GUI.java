package cache;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.*;

public class GUI extends JPanel {
    Font customFont = new Font("Arial", Font.BOLD, 16);
    private JPanel directPanel;
    private JPanel fullyPanel;
    private JPanel setPanel;

    public GUI() {
        super(new GridLayout(1, 1));
    }

    // Initial frame configuration
    public void frameConfiguration() {
        JFrame frame = new JFrame("Cache Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 500);

        // Create the tabbed pane
        JTabbedPane tabbedPane = tabsConfiguration();

        // Add the tabbed pane to the frame
        frame.add(tabbedPane);

        frame.setVisible(true);
    }

    // Configuration for each tab
    public JTabbedPane tabsConfiguration() {
        // Create a JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Create content panels for each tab
        directPanel = new JPanel(new GridLayout(1, 2));
        fullyPanel = new JPanel(new GridLayout(1, 2));
        setPanel = new JPanel(new GridLayout(1, 2));

        tabbedPane.addTab("Direct Mapped Cache", directPanel);
        tabbedPane.addTab("Fully Associative Cache", fullyPanel);
        tabbedPane.addTab("Set Associative Cache", setPanel);

        // Add styling to the JTabbedPane
        stylingTabs(tabbedPane);

        // Link the tabs to their respective methods
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                displaySelectedTabContents(directPanel, i);
            } else if (i == 1) {
                displaySelectedTabContents(fullyPanel, i);
            } else if (i == 2) {

                // TO CHANGE : need to change this in order to add the k-ways option
                displaySelectedTabContents(setPanel, i);
            }
        }

        return tabbedPane;
    }

    // Styling for the tabs (background color, selected tab color)
    public void stylingTabs(JTabbedPane tabbedPane) {
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
    public void displaySelectedTabContents(JPanel panel, int index) {
        // Left column: Add a nested JPanel with GridLayout for 3 input fields
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        configureLeftPanel(panel, leftPanel, index);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        configureRightPanel(rightPanel);

        // Add the left and right panels to the main panel
        panel.add(leftPanel);
        panel.add(rightPanel);

    }

    // Contains radio buttons for all options and a button to run the simulation
    private void configureLeftPanel(JPanel panel, JPanel leftPanel, int index) {
        // Create GridBagConstraints for left column
        GridBagConstraints leftConstraints = new GridBagConstraints();
        leftConstraints.anchor = GridBagConstraints.WEST;
        leftConstraints.insets = new Insets(5, 5, 5, 5);

        JLabel leftColumnLabel = new JLabel("Configuration");
        leftColumnLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Set top padding

        leftColumnLabel.setFont(customFont);

        JLabel ramSizeInputLabel = new JLabel("Choose RAM Size");
        JRadioButton ramSizeOption1 = new JRadioButton("128 bytes");
        JRadioButton ramSizeOption2 = new JRadioButton("256 bytes");

        JLabel cacheSizeInputLabel = new JLabel("Choose Cache Size");
        JRadioButton cacheSizeOption1 = new JRadioButton("16 bytes");
        JRadioButton cacheSizeOption2 = new JRadioButton("32 bytes");

        JLabel blockSizeInputLabel = new JLabel("Choose Block Size:");
        JRadioButton blockSizeOption = new JRadioButton("4 words");

        if (index == 1) {
            JLabel replacementAlgorithmLabel = new JLabel("Choose Replacement Algorithm:");
            JRadioButton replacementAlgorithmOption = new JRadioButton("LRU");

            leftConstraints.gridx = 0;
            leftConstraints.gridy = 4;
            leftPanel.add(replacementAlgorithmLabel, leftConstraints);

            leftConstraints.gridx = 1;
            leftConstraints.gridy = 4;
            leftPanel.add(replacementAlgorithmOption, leftConstraints);

        } else if (index == 2) {
            JLabel replacementAlgorithmLabel = new JLabel("Choose Replacement Algorithm:");
            JRadioButton replacementAlgorithmOption = new JRadioButton("LRU");

            JLabel kWaysLabel = new JLabel("Choose k-ways:");
            JRadioButton kWaysOption1 = new JRadioButton("2 ways");
            JRadioButton kWaysOption2 = new JRadioButton("4 ways");

            leftConstraints.gridx = 0;
            leftConstraints.gridy = 4;
            leftPanel.add(replacementAlgorithmLabel, leftConstraints);

            leftConstraints.gridx = 1;
            leftConstraints.gridy = 4;
            leftPanel.add(replacementAlgorithmOption, leftConstraints);

            leftConstraints.gridx = 0;
            leftConstraints.gridy = 5;
            leftPanel.add(kWaysLabel, leftConstraints);

            leftConstraints.gridx = 1;
            leftConstraints.gridy = 5;
            leftPanel.add(kWaysOption1, leftConstraints);

            leftConstraints.gridx = 2;
            leftConstraints.gridy = 5;
            leftPanel.add(kWaysOption2, leftConstraints);
        }

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
        leftPanel.add(ramSizeOption1, leftConstraints);

        leftConstraints.gridx = 2;
        leftConstraints.gridy = 1;
        leftPanel.add(ramSizeOption2, leftConstraints);

        leftConstraints.gridx = 1;
        leftConstraints.gridy = 2;
        leftPanel.add(cacheSizeOption1, leftConstraints);

        leftConstraints.gridx = 2;
        leftConstraints.gridy = 2;
        leftPanel.add(cacheSizeOption2, leftConstraints);

        leftConstraints.gridx = 1;
        leftConstraints.gridy = 3;
        leftPanel.add(blockSizeOption, leftConstraints);

        leftConstraints.gridx = 1;
        leftConstraints.gridy = 6;
        leftPanel.add(submitBtn, leftConstraints);
    }

    /*
     * Contains 2 tables showcasing the state of the RAM and Cache,
     * a text field which is used for loading the addresses, a panel to indicate a
     * hit or miss
     * and a section for displaying results.
     */
    private void configureRightPanel(JPanel rightPanel) {
        // Create GridBagConstraints for right column
        GridBagConstraints rightConstraints = new GridBagConstraints();
        rightConstraints.anchor = GridBagConstraints.WEST;
        rightConstraints.insets = new Insets(5, 5, 5, 5);

        JLabel rightColumnLabel = new JLabel("Cache Mapping");
        rightColumnLabel.setFont(customFont);

        rightConstraints.gridx = 0;
        rightConstraints.gridy = 0;
        rightPanel.add(rightColumnLabel, rightConstraints);

        // Cache Table
        JLabel cacheTableLabel = new JLabel("Current State of Cache");
        rightConstraints.gridx = 0;
        rightConstraints.gridy = 1;
        rightPanel.add(cacheTableLabel, rightConstraints);

        DefaultTableModel modelCache = new DefaultTableModel();
        modelCache.addColumn("Tag");
        modelCache.addColumn("Line");
        modelCache.addColumn("Offset");

        JTable cacheTable = new JTable(modelCache);

        JScrollPane scrollPane = new JScrollPane(cacheTable);
        scrollPane.setPreferredSize(new Dimension(200, 100));

        rightConstraints.gridx = 0;
        rightConstraints.gridy = 2;
        rightPanel.add(scrollPane, rightConstraints);

        // Ram Table
        JLabel ramTableLabel = new JLabel("Current State of RAM");
        rightConstraints.gridx = 1;
        rightConstraints.gridy = 1;
        rightPanel.add(ramTableLabel, rightConstraints);

        DefaultTableModel modelRam = new DefaultTableModel();
        modelRam.addColumn("Tag");
        modelRam.addColumn("Data");
        JTable ramTable = new JTable(modelRam);

        JScrollPane scrollPaneRam = new JScrollPane(ramTable);
        scrollPaneRam.setPreferredSize(new Dimension(200, 100));

        rightConstraints.gridx = 1;
        rightConstraints.gridy = 2;
        rightPanel.add(scrollPaneRam, rightConstraints);

        // Input for searching in the Cache
        // TO CHANGE
        JLabel testingAddressLabel = new JLabel("Testing Address:");
        testingAddressLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Set top padding

        JLabel testingAddress = new JLabel("000000000");
        testingAddress.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Set top padding

        rightConstraints.gridx = 0;
        rightConstraints.gridy = 3;
        rightPanel.add(testingAddressLabel, rightConstraints);

        rightConstraints.gridx = 1;
        rightConstraints.gridy = 3;
        rightPanel.add(testingAddress, rightConstraints);

        // Area highlighting hit or miss
        JPanel indicatorPanel = new JPanel();
        indicatorPanel.setLayout(new GridBagLayout());
        indicatorPanel.setBackground(Color.green);

        JLabel label = new JLabel("Hit");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        indicatorPanel.add(label);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Start at column 0
        gbc.gridy = 4; // Start at row 0
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Span the entire row
        gbc.fill = GridBagConstraints.BOTH;
        rightPanel.add(indicatorPanel, gbc);

        // Section for stats
        JLabel statsLabel = new JLabel("Results");
        statsLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0)); // Set top padding
        statsLabel.setFont(customFont);

        rightConstraints.gridx = 0;
        rightConstraints.gridy = 5;
        rightPanel.add(statsLabel, rightConstraints);

        JLabel missRateLabel = new JLabel("Miss Rate");
        rightConstraints.gridx = 0;
        rightConstraints.gridy = 6;
        rightPanel.add(missRateLabel, rightConstraints);

        JLabel missRate = new JLabel("0.0%");
        rightConstraints.gridx = 1;
        rightConstraints.gridy = 6;
        rightPanel.add(missRate, rightConstraints);

    }

}
