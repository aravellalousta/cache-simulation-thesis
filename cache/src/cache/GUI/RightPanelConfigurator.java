package cache.GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import cache.Ram;

public class RightPanelConfigurator extends InitGUI implements RightPanelListener {
    static DefaultTableModel modelAddress = new DefaultTableModel();

    /*
     * Contains 2 tables showcasing the state of the RAM and Cache,
     * a text field which is used for loading the addresses, a panel to indicate a
     * hit or miss
     * and a section for displaying results.
     */
    public static JPanel configureRightPanel() {
        JPanel rightPanel = new JPanel(new GridBagLayout());

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
        JLabel memoryTableLabel = new JLabel("Memory Address Analysis");
        rightConstraints.gridx = 0;
        rightConstraints.gridy = 1;
        rightPanel.add(memoryTableLabel, rightConstraints);

        JTable addressTable = new JTable(modelAddress);

        JScrollPane scrollPane = new JScrollPane(addressTable);
        scrollPane.setPreferredSize(new Dimension(200, 100));

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

        return rightPanel;

    }

    public void refreshRightPanel(Ram myRam) {
        modelAddress.addColumn("Tag");
        modelAddress.addColumn("Line");
        modelAddress.addColumn("Offset");

        int tag = myRam.getTagBits();
        int line = myRam.getLineBits();
        int offset = myRam.getOffsetBits();

        modelAddress.addRow(new Object[] { tag, line, offset });
    }

    @Override
    public void onLeftPanelSubmit(Ram myRam) {
        refreshRightPanel(myRam);
    }
}
