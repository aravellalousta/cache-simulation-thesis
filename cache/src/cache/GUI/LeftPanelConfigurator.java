package cache.GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import cache.Ram;
import cache.CacheTypes.*;

public class LeftPanelConfigurator extends InitGUI {
    private static RightPanelConfigurator rightPanelConfigurator;
    private static RightPanelListener rightPanelListener; // Reference to the right panel listener

    private static JLabel leftColumnLabel, ramSizeInputLabel, cacheSizeInputLabel, blockSizeInputLabel,
            replacementAlgorithmLabel, kWaysLabel;

    private static JRadioButton ramSizeOption1, ramSizeOption2, cacheSizeOption1, cacheSizeOption2, kWaysOption1,
            kWaysOption2, replacementAlgorithmOption, blockSizeOption;

    protected static int blockSize;
    protected static int cacheSize;
    protected static int ramSize;

    static boolean resetStatus = false;

    // Constructor to set the right panel listener
    public LeftPanelConfigurator(RightPanelListener rightPanelListener) {
        this.rightPanelListener = rightPanelListener;
    }

    public static JPanel configureLeftPanel(int index) {
        JPanel leftPanel = new JPanel(new GridBagLayout());

        // Create GridBagConstraints for left column
        GridBagConstraints leftConstraints = new GridBagConstraints();
        leftConstraints.anchor = GridBagConstraints.WEST;
        leftConstraints.insets = new Insets(5, 5, 5, 5);

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

        if (index == 1) {
            replacementAlgorithmLabel = new JLabel("Choose Replacement Algorithm:");
            replacementAlgorithmOption = new JRadioButton("LRU");

            leftConstraints.gridx = 0;
            leftConstraints.gridy = 4;
            leftPanel.add(replacementAlgorithmLabel, leftConstraints);

            leftConstraints.gridx = 1;
            leftConstraints.gridy = 4;
            leftPanel.add(replacementAlgorithmOption, leftConstraints);

        } else if (index == 2) {
            replacementAlgorithmLabel = new JLabel("Choose Replacement Algorithm:");
            replacementAlgorithmOption = new JRadioButton("LRU");

            kWaysLabel = new JLabel("Choose k-ways:");
            kWaysOption1 = new JRadioButton("2 ways");
            kWaysOption2 = new JRadioButton("4 ways");

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
        JButton resetBtn = new JButton("Reset");

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

        leftConstraints.gridx = 2;
        leftConstraints.gridy = 6;
        leftPanel.add(resetBtn, leftConstraints);

        ramSizeOption1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ramSize = 128;
            }
        });
        ramSizeOption2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ramSize = 256;
            }
        });
        cacheSizeOption1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cacheSize = 16;
            }
        });
        cacheSizeOption2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cacheSize = 32;
            }
        });
        blockSizeOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blockSize = 4;
            }
        });

        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ram myRam = new Ram(ramSize);
                myRam.addressAnalysis(ramSize, index, cacheSize, blockSize, 0);

                int tag = myRam.getTagBits();
                int line = myRam.getLineBits();
                int offset = myRam.getOffsetBits();
                int cacheLines = cacheSize / blockSize;

                if (index != 1 && index != 2 && !resetStatus) {
                    DirectMappedCache myCache = new DirectMappedCache(tag, line, offset);
                    myCache.setCacheLines(cacheLines);
                    rightPanelListener.onLeftPanelSubmit(myRam, myCache, resetStatus);
                }

            }
        });

        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ramSizeOption1.setSelected(false);
                ramSizeOption2.setSelected(false);

                cacheSizeOption1.setSelected(false);
                cacheSizeOption2.setSelected(false);

                blockSizeOption.setSelected(false);

                if (index == 1) {
                    replacementAlgorithmOption.setSelected(false);
                } else if (index == 2) {
                    replacementAlgorithmOption.setSelected(false);
                    kWaysOption1.setSelected(false);
                    kWaysOption2.setSelected(false);
                }
                rightPanelListener.onLeftPanelSubmit(null, null, true);
            }
        });

        return leftPanel;
    }

}
