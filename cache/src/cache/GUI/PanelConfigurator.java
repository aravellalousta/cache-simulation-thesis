package cache.GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import cache.LRUFullyAssociative;
import cache.LRUSetAssociative;
import cache.Ram;
import cache.CacheTypes.*;

public class PanelConfigurator extends InitGUI {
    public static Ram myRam;
    public static DirectMappedCache dmCache;
    public static FullyAssociativeCache faCache;
    public static SetAssociativeCache saCache;
    public static LRUFullyAssociative LRUFully = new LRUFullyAssociative();
    public static LRUSetAssociative LRUSet = new LRUSetAssociative();

    public static JLabel leftColumnLabel, ramSizeInputLabel, cacheSizeInputLabel, blockSizeInputLabel,
            replacementAlgorithmLabel, kWaysLabel;

    public static JRadioButton ramSizeOption1, ramSizeOption2, cacheSizeOption1, cacheSizeOption2, kWaysOption1,
            kWaysOption2, replacementAlgorithmOption, blockSizeOption, manualOption, automaticOption;

    public static int blockSize, cacheSize, ramSize, kways;

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
    static String[][] addressesArray;

    public static JLabel testingAddress;
    public static Timer timer;

    static JLabel rightColumnLabel, memoryTableLabel, cacheTableLabel, testingAddressLabel, hitMissLabel, statsLabel,
            missRateLabel, missRate, manuaLabel;

    static JPanel indicatorPanel;

    // Adding all user interface elements to each panel, based on the the tab index
    // The GUI is split into 2 columns. One for configuration and one for displaying
    // results.
    public static JPanel configurePanel(int index) {
        JPanel panel = new JPanel(new GridBagLayout());

        // Creating constraints that determine the placement of the GUI elements
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

        // Tabs 2 & 3 have some extra options
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

        rightColumnLabel = new JLabel("Cache Mapping");
        rightColumnLabel.setFont(customFont);

        memoryTableLabel = new JLabel("Memory Address Analysis");

        JTable addressTable = new JTable(modelAddress);

        JScrollPane scrollPane = new JScrollPane(addressTable);
        scrollPane.setPreferredSize(new Dimension(200, 200));

        cacheTableLabel = new JLabel("Current State of Cache");
        cacheStateTable = new JTable(modelCache);

        JScrollPane scrollPaneRam = new JScrollPane(cacheStateTable);
        scrollPaneRam.setPreferredSize(new Dimension(200, 200));

        testingAddressLabel = new JLabel("Testing Address:");
        testingAddressLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Set top padding

        testingAddress = new JLabel("  ");
        testingAddress.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Set top padding

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

        missRateLabel = new JLabel("Miss Rate");
        missRate = new JLabel();

        constraints.gridx = 4;
        constraints.gridy = 0;
        panel.add(rightColumnLabel, constraints);

        constraints.gridx = 4;
        constraints.gridy = 4;
        panel.add(memoryTableLabel, constraints);

        constraints.gridx = 4;
        constraints.gridy = 5;
        panel.add(scrollPane, constraints);

        constraints.gridx = 5;
        constraints.gridy = 4;
        panel.add(cacheTableLabel, constraints);

        constraints.gridx = 5;
        constraints.gridy = 5;
        panel.add(scrollPaneRam, constraints);

        constraints.gridx = 4;
        constraints.gridy = 1;
        panel.add(testingAddressLabel, constraints);

        constraints.gridx = 5;
        constraints.gridy = 1;
        panel.add(testingAddress, constraints);

        constraints.gridx = 4;
        constraints.gridy = 5;
        panel.add(statsLabel, constraints);

        constraints.gridx = 4;
        constraints.gridy = 6;
        panel.add(missRateLabel, constraints);

        constraints.gridx = 5;
        constraints.gridy = 6;
        panel.add(missRate, constraints);

        // Adding values to selections
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

                if (index == 2) {
                    kWaysOption2.setEnabled(false);
                }

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

        if (index == 2) {
            kWaysOption1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    kways = 2;
                    kWaysOption2.setEnabled(false);

                }
            });
            kWaysOption2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    kways = 4;
                    kWaysOption1.setEnabled(false);

                }
            });
        }

        manualOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                automaticOption.setEnabled(false);
            }
        });

        automaticOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manualOption.setEnabled(false);
            }
        });

        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if all radio buttons have been selected
                if (checkAllOptionsSelected(index)) {
                    myRam = new Ram(ramSize);
                    // Based on the selections, calculate the bit lengths for every type of cache
                    myRam.addressAnalysis(ramSize, index, cacheSize, blockSize, kways);
                    myRam.setBlockSize(blockSize);

                    int tag = myRam.getTagBits();
                    int line = myRam.getLineBits();
                    int set = myRam.getSetBits();
                    int offset = myRam.getOffsetBits();
                    int cacheLines = cacheSize / blockSize;

                    // Create new cache instance based on the bits extracted from the ram analysis
                    if (index != 1 && index != 2 && !resetStatus) {
                        dmCache = new DirectMappedCache(tag, line, offset);
                        dmCache.setCacheLines(cacheLines);
                        onSubmit(index);
                    } else if (index == 1 && !resetStatus) {
                        faCache = new FullyAssociativeCache(tag, offset);
                        faCache.setCacheLines(cacheLines);
                        onSubmit(index);
                    } else if (index == 2 && !resetStatus) {
                        saCache = new SetAssociativeCache(tag, set, offset);
                        saCache.setCacheLines(cacheLines);
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
        // The automatic option uses the AddressGenerator class to generate addresses,
        // whereas in the manual option the addresses are predifined as an array
        if (automaticOption.isSelected()) {
            addressesArray = generator.generateAddresses();
        } else if (manualOption.isSelected()) {
            addressesArray = new String[][] {
                    { "0000011", "01111110" },
                    { "0000010", "00100110" },
                    { "0010010", "10011000" },
                    { "0000110", "01111000" },
                    { "1000000", "00101100" },
                    { "0100100", "10110000" },
                    { "1111000", "01111000" },
                    { "0011110", "00100000" },
                    { "1111011", "00101101" },
                    { "1011000", "00100100" }
            };
        }
        // Timer running to load a new address every 2 sec
        timer = new Timer(2000, new ActionListener() {
            public int currentIndex = 0;
            public String addressText;
            int ramSize = myRam.getSize();

            @Override
            public void actionPerformed(ActionEvent e) {

                if (ramSize == 128) {
                    if (currentIndex < addressesArray.length) {
                        addressText = addressesArray[currentIndex][0];
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

                    // Adding the address to the first table, splitting it into it's subsections
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
                    } else if (index == 2) {
                        saCache.inputAddressAnalysis(addressText);
                        String tag = saCache.getTagBits();
                        String set = saCache.getSetBits();
                        String offset = saCache.getOffsetBits();

                        modelAddress.addRow(new Object[] { tag, set, offset });
                    }
                    // Method used to fill the second table
                    fillCacheTableWithData(index, addressText);
                } else {
                    // Once the timer stops, display the final miss rate
                    if (index == 0) {
                        missRate.setText(Double.toString(dmCache.getMissRate()) + "%");
                    } else if (index == 1) {
                        missRate.setText(Double.toString(faCache.getMissRate()) + "%");
                    } else if (index == 2) {
                        missRate.setText(Double.toString(saCache.getMissRate()) + "%");
                    }
                }

            }
        });

        timer.start();
        initializeRightPanel(index);
    }

    // When the reset button is clicked, return all options to the initian
    // unselected state
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
            kWaysOption2.setEnabled(true);

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
        Cache.hitCounter = 0;
        Cache.missCounter = 0;

        resetStatus = false;
    }

    public static boolean checkAllOptionsSelected(int index) {
        if ((ramSizeOption1.isSelected() || ramSizeOption2.isSelected())
                && (cacheSizeOption1.isSelected() || cacheSizeOption2.isSelected())
                && blockSizeOption.isSelected()
                && (manualOption.isSelected() || automaticOption.isSelected())) {
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
        Color hitColor = new Color(134, 222, 183);
        Color missColor = new Color(8, 178, 227);

        // Direct Mapped
        if (index == 0) {
            tagBits = dmCache.getTagBits();
            testingAddress.setText(addressText);
            // If the address is found in the cache, indicate a hit and continue
            if (dmCache.searchAddressDM(addressText)) {
                indicatorPanel.setBackground(hitColor);
                hitMissLabel.setText("Hit!");
            } else {
                // If the address is not found in the cache, indicate a miss and add the tag to
                // the line specified by the address
                indicatorPanel.setBackground(missColor);
                hitMissLabel.setText("Miss!");
                memoryBlock = dmCache.returnMemoryBlock(blockSize, addressText);
                row = Integer.parseInt(dmCache.getSearchLine());

                cacheStateTable.getModel().setValueAt(tagBits, row, 0);
                cacheStateTable.getModel().setValueAt("MemBlock[" + memoryBlock + "]", row, 1);
            }
        } // Fully Associative
        else if (index == 1) {
            testingAddress.setText(addressText);
            int cacheLines = faCache.getCacheLines();

            // If the address is found in the cache, indicate a hit (the replacement is
            // handled within the class)
            if (faCache.searchAddressFA(addressText, cacheLines)) {
                indicatorPanel.setBackground(hitColor);
                hitMissLabel.setText("Hit!");
            } else {
                // If the address is not found in the cache, indicate a miss (the replacement is
                // handled within the class)
                indicatorPanel.setBackground(missColor);
                hitMissLabel.setText("Miss!");
            }
            // Handling replacement algorithm
            LRUFullyAssociative.updateColumnValues(faCache, modelCache);
        } else if (index == 2) {
            testingAddress.setText(addressText);
            int cacheLines = saCache.getCacheLines();

            // If the address is found in the cache, indicate a hit
            if (saCache.searchAddressSA(addressText, kways, cacheLines)) {
                indicatorPanel.setBackground(hitColor);
                hitMissLabel.setText("Hit!");
            } else {
                // If the address is found in the cache, indicate a miss
                indicatorPanel.setBackground(missColor);
                hitMissLabel.setText("Miss!");
            }
            // Handling replacement algorithm
            int set = Integer.parseInt(saCache.getSetBits(), 2);
            saCache.updateColumnValues(modelCache, set);

        }
    }

    public static void initializeRightPanel(int tabIndex) {
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
        } else if (tabIndex == 2) {
            modelAddress.addColumn("Tag");
            modelAddress.addColumn("Set");
            modelAddress.addColumn("Offset");
        }

    }
}
