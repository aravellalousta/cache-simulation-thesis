package cache.GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import cache.Ram;

public class LeftPanelConfigurator extends InitGUI {
    private static RightPanelConfigurator rightPanelConfigurator;
    private static RightPanelListener rightPanelListener; // Reference to the right panel listener
    protected static int blockSize;
    protected static int cacheSize;
    protected static int ramSize;

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
        cacheSizeOption1.addActionListener(new ActionListener() {
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
                System.out.println("selected options are: " + cacheSize + ramSize + blockSize);
                Ram myRam = new Ram();
                myRam.addressAnalysis(ramSize, index, cacheSize, blockSize, 0);

                rightPanelListener.onLeftPanelSubmit(myRam);
            }
        });

        return leftPanel;
    }

}
