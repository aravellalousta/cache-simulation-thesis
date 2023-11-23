package cache.GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import cache.LRUImplementation;
import cache.Ram;
import cache.CacheTypes.*;

public class PanelConfigurator extends InitGUI {
    public static Ram myRam;
    public static DirectMappedCache dmCache;
    public static FullyAssociativeCache faCache;

    public static JLabel leftColumnLabel, ramSizeInputLabel, cacheSizeInputLabel, blockSizeInputLabel,
            replacementAlgorithmLabel, kWaysLabel;

    public static JRadioButton ramSizeOption1, ramSizeOption2, cacheSizeOption1, cacheSizeOption2, kWaysOption1,
            kWaysOption2, replacementAlgorithmOption, blockSizeOption, manualOption, automaticOption;

    public static int blockSize;
    public static int cacheSize;
    public static int ramSize;

    static boolean resetStatus = false;

    // GUI elements
    static JPanel panel = new JPanel(new GridBagLayout());
    static GridBagConstraints constraints = new GridBagConstraints();
    static DefaultTableModel modelAddress = new DefaultTableModel();

    static String[] data = { "Tag", "Data" };
    static DefaultTableModel modelCache = new DefaultTableModel(data, 8);
    static JTable cacheStateTable;

    // Managing the display of addresses
    static AddressGenerator generator = new AddressGenerator();
    static String[][] addressesArray = {
            { "0000011", "00000000" },
            { "0000010", "00000000" },
            { "0010010", "00000000" },
            { "0000010", "00000000" },
            { "1000000", "00000000" },
            { "0100010", "00000000" },
            { "1000000", "00000000" },
            { "0000010", "00000000" },
    };
    public static JLabel testingAddress;
    public static Timer timer;

    static JLabel rightColumnLabel, memoryTableLabel, cacheTableLabel, testingAddressLabel, hitMissLabel, statsLabel,
            missRateLabel, missRate, manuaLabel;

    static JPanel indicatorPanel;

    public static JPanel configurePanel(int index) {
        JPanel panel = new JPanel(new GridBagLayout());

        // Create GridBagConstraints for left column
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        leftColumnLabel = new JLabel("Configuration");
        leftColumnLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Set top padding

        leftColumnLabel.setFont(customFont);

        ramSizeInputLabel = new JLabel("Choose RAM Size");
        ramSizeOption1 = new JRadioButton("128 bytes");
        ramSizeOption2 = new JRadioButton("256 bytes");

        cacheSizeInputLabel = new JLabel("Choose Cache Size");
        cacheSizeOption1 = new JRadioButton("16 bytes");
        cacheSizeOption2 = new JRadioButton("32 bytes");

        blockSizeInputLabel = new JLabel("Choose Block Size:");
        blockSizeOption = new JRadioButton("4 words");

        manuaLabel = new JLabel("Type of simulation:");
        manualOption = new JRadioButton("Manual");
        automaticOption = new JRadioButton("Automatic");

        if (index == 1) {
            replacementAlgorithmLabel = new JLabel("Choose Replacement Algorithm:");
            replacementAlgorithmOption = new JRadioButton("LRU");

            constraints.gridx = 0;
            constraints.gridy = 4;
            panel.add(replacementAlgorithmLabel, constraints);

            constraints.gridx = 1;
            constraints.gridy = 4;
            panel.add(replacementAlgorithmOption, constraints);

        } else if (index == 2) {
            replacementAlgorithmLabel = new JLabel("Choose Replacement Algorithm:");
            replacementAlgorithmOption = new JRadioButton("LRU");

            kWaysLabel = new JLabel("Choose k-ways:");
            kWaysOption1 = new JRadioButton("2 ways");
            kWaysOption2 = new JRadioButton("4 ways");

            constraints.gridx = 0;
            constraints.gridy = 4;
            panel.add(replacementAlgorithmLabel, constraints);

            constraints.gridx = 1;
            constraints.gridy = 4;
            panel.add(replacementAlgorithmOption, constraints);

            constraints.gridx = 0;
            constraints.gridy = 5;
            panel.add(kWaysLabel, constraints);

            constraints.gridx = 1;
            constraints.gridy = 5;
            panel.add(kWaysOption1, constraints);

            constraints.gridx = 2;
            constraints.gridy = 5;
            panel.add(kWaysOption2, constraints);
        }

        JButton submitBtn = new JButton("Submit");
        JButton resetBtn = new JButton("Reset");

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(leftColumnLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(ramSizeInputLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(cacheSizeInputLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(blockSizeInputLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(ramSizeOption1, constraints);

        constraints.gridx = 2;
        constraints.gridy = 1;
        panel.add(ramSizeOption2, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(cacheSizeOption1, constraints);

        constraints.gridx = 2;
        constraints.gridy = 2;
        panel.add(cacheSizeOption2, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(blockSizeOption, constraints);

        constraints.gridx = 0;
        constraints.gridy = 6;
        panel.add(manuaLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 6;
        panel.add(manualOption, constraints);

        constraints.gridx = 2;
        constraints.gridy = 6;
        panel.add(automaticOption, constraints);

        constraints.gridx = 1;
        constraints.gridy = 7;
        panel.add(submitBtn, constraints);

        constraints.gridx = 2;
        constraints.gridy = 7;
        panel.add(resetBtn, constraints);

        // Right Column
        rightColumnLabel = new JLabel("Cache Mapping");
        rightColumnLabel.setFont(customFont);

        constraints.gridx = 4;
        constraints.gridy = 0;
        panel.add(rightColumnLabel, constraints);

        // Cache Table
        memoryTableLabel = new JLabel("Memory Address Analysis");
        constraints.gridx = 4;
        constraints.gridy = 4;
        panel.add(memoryTableLabel, constraints);

        JTable addressTable = new JTable(modelAddress);

        JScrollPane scrollPane = new JScrollPane(addressTable);
        scrollPane.setPreferredSize(new Dimension(200, 200));

        constraints.gridx = 4;
        constraints.gridy = 5;
        panel.add(scrollPane, constraints);

        // Ram Table
        cacheTableLabel = new JLabel("Current State of Cache");
        constraints.gridx = 5;
        constraints.gridy = 4;
        panel.add(cacheTableLabel, constraints);

        cacheStateTable = new JTable(modelCache);

        JScrollPane scrollPaneRam = new JScrollPane(cacheStateTable);
        scrollPaneRam.setPreferredSize(new Dimension(200, 200));

        constraints.gridx = 5;
        constraints.gridy = 5;
        panel.add(scrollPaneRam, constraints);

        testingAddressLabel = new JLabel("Testing Address:");
        testingAddressLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Set top padding

        testingAddress = new JLabel("  ");
        testingAddress.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Set top padding

        constraints.gridx = 4;
        constraints.gridy = 1;
        panel.add(testingAddressLabel, constraints);

        constraints.gridx = 5;
        constraints.gridy = 1;
        panel.add(testingAddress, constraints);

        // Area highlighting hit or miss
        indicatorPanel = new JPanel();
        indicatorPanel.setLayout(new GridBagLayout());
        indicatorPanel.setBackground(Color.gray);

        hitMissLabel = new JLabel("   ");
        hitMissLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hitMissLabel.setVerticalAlignment(SwingConstants.CENTER);
        indicatorPanel.add(hitMissLabel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 4; // Start at column 0
        gbc.gridy = 2; // Start at row 0
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Span the entire row
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(indicatorPanel, gbc);

        // Section for stats
        statsLabel = new JLabel("Results");
        statsLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0)); // Set top padding
        statsLabel.setFont(customFont);

        constraints.gridx = 4;
        constraints.gridy = 5;
        panel.add(statsLabel, constraints);

        missRateLabel = new JLabel("Miss Rate");
        constraints.gridx = 4;
        constraints.gridy = 6;
        panel.add(missRateLabel, constraints);

        missRate = new JLabel();
        constraints.gridx = 5;
        constraints.gridy = 6;
        panel.add(missRate, constraints);

        ramSizeOption1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ramSize = 128;
                ramSizeOption2.setEnabled(false);
            }
        });
        ramSizeOption2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ramSize = 256;
                ramSizeOption1.setEnabled(false);
            }
        });
        cacheSizeOption1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cacheSize = 16;
                cacheSizeOption2.setEnabled(false);

            }
        });
        cacheSizeOption2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cacheSize = 32;
                cacheSizeOption1.setEnabled(false);

            }
        });
        blockSizeOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blockSize = 4;
            }
        });

        manualOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // setting something to true
                automaticOption.setEnabled(false);
            }
        });

        automaticOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // setting something to true
                manualOption.setEnabled(false);
            }
        });

        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkAllOptionsSelected(index)) {
                    myRam = new Ram(ramSize);
                    myRam.addressAnalysis(ramSize, index, cacheSize, blockSize, 0);
                    myRam.setBlockSize(blockSize);

                    int tag = myRam.getTagBits();
                    int line = myRam.getLineBits();
                    int offset = myRam.getOffsetBits();
                    int cacheLines = cacheSize / blockSize;

                    if (index != 1 && index != 2 && !resetStatus) {
                        dmCache = new DirectMappedCache(tag, line, offset);
                        dmCache.setCacheLines(cacheLines);
                        onSubmit(index);
                    } else if (index == 1) {
                        faCache = new FullyAssociativeCache(tag, offset);
                        faCache.setCacheLines(cacheLines);
                        onSubmit(index);
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Please select all options before submitting.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onReset(index);
                if (timer != null && timer.isRunning()) {
                    timer.stop();
                }
            }
        });

        return panel;
    }

    public static void onSubmit(int index) {
        // addressesArray = generator.generateAddresses();

        JPanel selectedPanel = TabManager.getTab(index);

        timer = new Timer(2500, new ActionListener() {
            public int currentIndex = 0;
            public String addressText;
            int ramSize = myRam.getSize();

            @Override
            public void actionPerformed(ActionEvent e) {

                if (ramSize == 128) {
                    if (currentIndex < addressesArray.length) {
                        addressText = addressesArray[currentIndex][0];
                        // currentIndex++;
                    } else {
                        ((Timer) e.getSource()).stop(); // Stop the timer when all addresses have been shown
                    }
                } else {
                    if (currentIndex < addressesArray.length) {
                        addressText = addressesArray[currentIndex][1];
                    } else {
                        ((Timer) e.getSource()).stop(); // Stop the timer when all addresses have been shown
                    }
                }
                currentIndex++;

                if (timer.isRunning()) {
                    testingAddress.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Set top padding

                    selectedPanel.revalidate();
                    selectedPanel.repaint();

                    if (index == 0) {
                        dmCache.inputAddressAnalysis(addressText);
                        String tag = dmCache.getTagBits();
                        String line = dmCache.getLineBits();
                        String offset = dmCache.getOffsetBits();

                        modelAddress.addRow(new Object[] { tag, line, offset });
                    } else if (index == 1) {
                        faCache.inputAddressAnalysis(addressText);
                        String tag = faCache.getTagBits();
                        String offset = faCache.getOffsetBits();
                        modelAddress.addRow(new Object[] { tag, offset });
                    }

                    fillCacheTableWithData(index, addressText);
                } else {
                    if (index == 0) {
                        missRate.setText(dmCache.getMissRate());
                    }
                }

            }
        });

        timer.start();
        refreshRightPanel(myRam, index);

    }

    public static void onReset(int index) {
        resetStatus = true;
        ramSizeOption1.setEnabled(true);
        ramSizeOption2.setEnabled(true);

        cacheSizeOption1.setEnabled(true);
        cacheSizeOption2.setEnabled(true);

        ramSizeOption1.setSelected(false);
        ramSizeOption2.setSelected(false);

        cacheSizeOption1.setSelected(false);
        cacheSizeOption2.setSelected(false);

        blockSizeOption.setSelected(false);

        manualOption.setSelected(false);
        automaticOption.setSelected(false);

        manualOption.setEnabled(true);
        automaticOption.setEnabled(true);

        if (index == 1) {
            replacementAlgorithmOption.setSelected(false);
        } else if (index == 2) {
            replacementAlgorithmOption.setSelected(false);
            kWaysOption1.setSelected(false);
            kWaysOption2.setSelected(false);
        }

        modelAddress.setColumnCount(0);
        modelAddress.setRowCount(0);
        modelCache.getDataVector().removeAllElements();
        modelCache.fireTableDataChanged();
        modelCache.setRowCount(8);
        testingAddress.setText("");
        indicatorPanel.setBackground(Color.gray);
        hitMissLabel.setText("   ");
        missRate.setText("");

    }

    public static boolean checkAllOptionsSelected(int index) {
        if ((ramSizeOption1.isSelected() || ramSizeOption2.isSelected())
                && (cacheSizeOption1.isSelected() || cacheSizeOption2.isSelected())
                && blockSizeOption.isSelected()) {
            if (index == 1 && replacementAlgorithmOption.isSelected()) {
                return true;
            } else if (index == 2 && replacementAlgorithmOption.isSelected()
                    && (kWaysOption1.isSelected() || kWaysOption2.isSelected())) {
                return true;
            }
            return true;
        }
        return false;
    }

    public static void fillCacheTableWithData(int index, String addressText) {
        int memoryBlock;
        int row;
        String tagBits;

        if (index == 0) {
            tagBits = dmCache.getTagBits();
            testingAddress.setText(addressText);

            if (dmCache.searchAddressDM(addressText)) {
                indicatorPanel.setBackground(Color.red);
                hitMissLabel.setText("Hit!");
            } else {
                indicatorPanel.setBackground(Color.green);
                hitMissLabel.setText("Miss!");
                memoryBlock = dmCache.returnMemoryBlock(blockSize, addressText);
                row = Integer.parseInt(dmCache.getSearchLine());

                cacheStateTable.getModel().setValueAt(tagBits, row, 0);
                cacheStateTable.getModel().setValueAt("MemBlock[" + memoryBlock + "]", row, 1);
            }
        } else if (index == 1) {
            testingAddress.setText(addressText);

            int cacheLines = faCache.getCacheLines();
            if (faCache.searchAddressFA(addressText, cacheLines)) {
                indicatorPanel.setBackground(Color.green);
                hitMissLabel.setText("Miss!");
            } else {
                indicatorPanel.setBackground(Color.red);
                hitMissLabel.setText("Hit!");
            }

            LRUImplementation.updateColumnValues(faCache, modelCache);
        }
    }

    public static void refreshRightPanel(Ram myRam, int tabIndex) {
        if (tabIndex == 0) {
            modelAddress.addColumn("Tag");
            modelAddress.addColumn("Line");
            modelAddress.addColumn("Offset");
            int cacheSize = dmCache.getCacheLines();
            dmCache.createArrayDM(cacheSize);

        } else if (tabIndex == 1) {
            modelAddress.addColumn("Tag");
            modelAddress.addColumn("Offset");
            int cacheSize = faCache.getCacheLines();
            faCache.createArrayFA(cacheSize);
        }

    }
}
