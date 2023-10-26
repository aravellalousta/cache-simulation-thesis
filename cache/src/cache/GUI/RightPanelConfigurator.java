package cache.GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import cache.Ram;
import cache.CacheTypes.DirectMappedCache;

public class RightPanelConfigurator extends InitGUI implements RightPanelListener {
    static GridBagConstraints rightConstraints = new GridBagConstraints();
    static DefaultTableModel modelAddress = new DefaultTableModel();
    static JPanel rightPanel = new JPanel(new GridBagLayout());
    static DirectMappedCache dm;

    AddressGenerator generator = new AddressGenerator();
    private static JLabel testingAddress;

    /*
     * Contains 2 tables showcasing the state of the RAM and Cache,
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
        JLabel cacheTableLabel = new JLabel("Current State of RAM");
        rightConstraints.gridx = 1;
        rightConstraints.gridy = 1;
        rightPanel.add(cacheTableLabel, rightConstraints);

        DefaultTableModel modelCache = new DefaultTableModel();
        modelCache.addColumn("Tag");
        modelCache.addColumn("Data");
        JTable ramTable = new JTable(modelCache);

        JScrollPane scrollPaneRam = new JScrollPane(ramTable);
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

    public void refreshRightPanel(Ram myRam, int tabIndex) {
        System.out.println(myRam.size);
        if (tabIndex == 0) {
            int tag = myRam.getTagBits();
            int line = myRam.getLineBits();
            int offset = myRam.getOffsetBits();

            modelAddress.addColumn("Tag");
            modelAddress.addColumn("Line");
            modelAddress.addColumn("Offset");

            dm = new DirectMappedCache(tag, line, offset);

        }

    }

    public void loadingAddresses(Ram myRam, JLabel testingAddress, int tabIndex) {
        String[][] addressesArray = generator.generateAddresses();
        JPanel selectedPanel = TabManager.getTab(tabIndex);

        Timer timer = new Timer(1500, new ActionListener() {
            private int currentIndex = 0;
            private String addressText;
            int ramSize = myRam.getSize();

            @Override
            public void actionPerformed(ActionEvent e) {
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

                dm.inputAddressAnalysis(addressText);
                String tag = dm.getTagBits();
                String line = dm.getLineBits();
                String offset = dm.getOffsetBits();

                modelAddress.addRow(new Object[] { tag, line, offset });
            }
        });

        timer.start();

    }

    @Override
    public void onLeftPanelSubmit(Ram myRam) {
        refreshRightPanel(myRam, TabManager.getTabIndex());
        loadingAddresses(myRam, RightPanelConfigurator.testingAddress, TabManager.getTabIndex());
    }
}
