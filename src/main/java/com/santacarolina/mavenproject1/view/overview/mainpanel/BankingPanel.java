package com.santacarolina.mavenproject1.view.overview.mainpanel;

import com.formdev.flatlaf.ui.FlatButtonUI;
import com.santacarolina.mavenproject1.model.UserBankAccount;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ButtonUI;
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
        bankSideTopBar.add(comboBoxLabel);
        bankSideTopBar.add(comboBox);

        importOFXButton = new JButton("Importar OFX");
        importOFXButton.setBorder(BorderFactory.createEmptyBorder());
        newStatmentButton = new JButton("Adicionar Extratos");
        newStatmentButton.setOpaque(true);
        bankSideBottomBar = new JPanel(new GridLayout(10,1));
        bankSideBottomBar.add(importOFXButton);
        bankSideBottomBar.add(newStatmentButton);

        bankSideBar = new JPanel(new BorderLayout());
        bankSideBar.setBorder(new EmptyBorder(10,20,2,20));
        bankSideBar.add(bankSideTopBar,BorderLayout.NORTH);
        bankSideBar.add(bankSideBottomBar,BorderLayout.SOUTH);

        bankTable = new BankTable();
        add(bankSideBar, BorderLayout.WEST);
        add(bankTable,BorderLayout.CENTER);

    }

}
