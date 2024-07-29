package com.santacarolina.mavenproject1.view.overview.mainpanel;

import com.santacarolina.mavenproject1.model.UserBankAccount;
import com.santacarolina.mavenproject1.services.ImageIconConfig;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BankingPanel extends JPanel {

    private JPanel bankSideBar;
    private JButton importOFXButton;
    private JButton newStatmentButton;
    private JPanel bankSideTopBar;
    private JPanel bankSideBottomBar;
    private JComboBox<UserBankAccount> comboBox;
    private JLabel comboBoxLabel;
    private BankTable bankTable;

    public BankingPanel() {
        initComponents();
    }

    private void initComponents(){

        comboBox = new JComboBox<>();
        comboBox.setPreferredSize(new Dimension(230,20));
        comboBoxLabel = new JLabel("Conta Bancária:");
        comboBox = new JComboBox<>();
        comboBox.setPreferredSize(new Dimension(230,20));
        comboBoxLabel = new JLabel("Conta Bancária:");

        bankSideTopBar = new JPanel(new FlowLayout());
        bankSideTopBar.setBackground(Color.MAGENTA);
        bankSideTopBar.add(comboBoxLabel);
        bankSideTopBar.add(comboBox);

        ImageIconConfig iconConfig = new ImageIconConfig("src/main/resources/icon/Swift.png");
        importOFXButton = new JButton("Importar OFX",iconConfig.getIcon());
        importOFXButton.setContentAreaFilled(false);
        importOFXButton.setBorderPainted(false);
        importOFXButton.setOpaque(false);
        importOFXButton.setHorizontalAlignment(SwingConstants.LEADING);
        importOFXButton.setHorizontalTextPosition(SwingConstants.TRAILING);
        newStatmentButton = new JButton("Adicionar Extratos");
        bankSideBottomBar = new JPanel(new GridLayout(6,1));
        bankSideBottomBar.setBorder(new EmptyBorder(1,20,1,20));
        bankSideBottomBar.add(importOFXButton);
        bankSideBottomBar.add(newStatmentButton);

        bankSideBar = new JPanel(new BorderLayout());
        bankSideBar.add(bankSideTopBar,BorderLayout.NORTH);
        bankSideBar.add(bankSideBottomBar,BorderLayout.CENTER);

        bankTable = new BankTable();

        setLayout(new BorderLayout());
        add(bankSideBar, BorderLayout.WEST);
        add(bankTable,BorderLayout.CENTER);

    }

}
