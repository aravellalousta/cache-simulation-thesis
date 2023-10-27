package cache.GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import cache.Ram;
import cache.CacheTypes.*;

public class RightPanelConfigurator extends InitGUI implements RightPanelListener {
    // GUI elements
    static JPanel rightPanel = new JPanel(new GridBagLayout());
    static GridBagConstraints rightConstraints = new GridBagConstraints();
    static DefaultTableModel modelAddress = new DefaultTableModel();
    static DefaultTableModel modelRam = new DefaultTableModel();

    // Managing the display of addresses
    AddressGenerator generator = new AddressGenerator();
    static String[][] addressesArray;
    private static JLabel testingAddress;

    private Timer timer;

    /*
     * Contains 2 tables showcasing the Memory Address analysis and the state of the
     * Cache,
     * a text field which is used for loading the addresses, a panel to indicate a
     * hit or miss
     * and a section for displaying results.
     */
    public static JPanel configureRightPanel() {

        // Create GridBagConstraints for right column
        rightConstraints.anchor = GridBagConstraints.WEST;
        rightConstraints.insets = new Insets(5, 5, 5, 5);

        JLabel rightColumnLabel = new JLabel("Cache Mapping");
        rightColumnLabel.setFont(customFont);

        rightConstraints.gridx = 0;
        rightConstraints.gridy = 0;
        rightPanel.add(rightColumnLabel, rightConstraints);

        // Cache Table
        JLabel memoryTableLabel = new JLabel("Memory Address Analysis");
        rightConstraints.gridx = 0;
        rightConstraints.gridy = 1;
        rightPanel.add(memoryTableLabel, rightConstraints);

        JTable addressTable = new JTable(modelAddress);

        JScrollPane scrollPane = new JScrollPane(addressTable);
        scrollPane.setPreferredSize(new Dimension(200, 200));

        rightConstraints.gridx = 0;
        rightConstraints.gridy = 2;
        rightPanel.add(scrollPane, rightConstraints);

        // Ram Table
        JLabel cacheTableLabel = new JLabel("Current State of Cache");
        rightConstraints.gridx = 1;
        rightConstraints.gridy = 1;
        rightPanel.add(cacheTableLabel, rightConstraints);

        JTable cacheStateTable = new JTable(modelRam);

        JScrollPane scrollPaneRam = new JScrollPane(cacheStateTable);
        scrollPaneRam.setPreferredSize(new Dimension(200, 200));

        rightConstraints.gridx = 1;
        rightConstraints.gridy = 2;
        rightPanel.add(scrollPaneRam, rightConstraints);

        JLabel testingAddressLabel = new JLabel("Testing Address:");
        testingAddressLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Set top padding

        testingAddress = new JLabel();
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

        return rightPanel;

    }

    public void refreshRightPanel(Ram myRam, DirectMappedCache myCache, int tabIndex) {
        if (tabIndex == 0) {
            modelAddress.addColumn("Tag");
            modelAddress.addColumn("Line");
            modelAddress.addColumn("Offset");
        }
        modelRam.addColumn("Tag");
        modelRam.addColumn("Data");

    }

    public void loadingAddresses(Ram myRam, DirectMappedCache myCache, JLabel testingAddress, int tabIndex,
            boolean resetStatus) {
        addressesArray = generator.generateAddresses();
        JPanel selectedPanel = TabManager.getTab(tabIndex);

        if (resetStatus) {
            // Stop the timer if resetStatus is true
            if (timer != null && timer.isRunning()) {
                timer.stop();
            }
            return;
        }

        timer = new Timer(1500, new ActionListener() {
            private int currentIndex = 0;
            private String addressText;
            int ramSize = myRam.getSize();

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(resetStatus);

                if (ramSize == 128) {
                    if (currentIndex < addressesArray.length) {
                        addressText = addressesArray[currentIndex][0];
                        testingAddress.setText(addressesArray[currentIndex][0]);
                        currentIndex++;
                    } else {
                        ((Timer) e.getSource()).stop(); // Stop the timer when all addresses have been shown
                    }
                } else {
                    if (currentIndex < addressesArray.length) {
                        addressText = addressesArray[currentIndex][1];
                        testingAddress.setText(addressesArray[currentIndex][1]);
                        currentIndex++;
                    } else {
                        ((Timer) e.getSource()).stop(); // Stop the timer when all addresses have been shown
                    }
                }
                // Update the label for the selectedPanel
                selectedPanel.add(testingAddress);
                testingAddress.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Set top padding

                rightConstraints.gridx = 1;
                rightConstraints.gridy = 3;
                rightPanel.add(testingAddress, rightConstraints);

                selectedPanel.revalidate();
                selectedPanel.repaint();

                myCache.inputAddressAnalysis(addressText);
                String tag = myCache.getTagBits();
                String line = myCache.getLineBits();
                String offset = myCache.getOffsetBits();

                modelAddress.addRow(new Object[] { tag, line, offset });
            }
        });

        timer.start();

    }

    public void fillCacheTableWithData(Cache myCache, int tabIndex) {
        int cacheSize = myCache.getCacheLines();

        for (int i = 0; i < cacheSize; i++) {
            if (tabIndex == 0) {

            }
            // modelRam.addRow(new Object[] { binaryString, randomNumber });
        }
    }

    public void clearRightPanel() {

        modelAddress.setRowCount(0);
        modelRam.setRowCount(0);
        testingAddress.setText("");

    }

    @Override
    public void onLeftPanelSubmit(Ram myRam, DirectMappedCache myCache, boolean resetStatus) {
        System.out.println(resetStatus);
        if (!resetStatus) {
            refreshRightPanel(myRam, myCache, TabManager.getTabIndex());
            loadingAddresses(myRam, myCache, RightPanelConfigurator.testingAddress, TabManager.getTabIndex(),
                    resetStatus);
            fillCacheTableWithData(myCache, TabManager.getTabIndex());
        } else {
            clearRightPanel();
            loadingAddresses(myRam, myCache, RightPanelConfigurator.testingAddress, TabManager.getTabIndex(),
                    resetStatus);
            return;
        }
    }
}
