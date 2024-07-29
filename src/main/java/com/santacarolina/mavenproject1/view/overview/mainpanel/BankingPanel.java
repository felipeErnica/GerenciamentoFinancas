package com.santacarolina.mavenproject1.view.overview.mainpanel;

import com.santacarolina.mavenproject1.model.UserBankAccount;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BankingPanel extends JPanel {

    private final Color MENU_BACKGROUND = new Color(66, 172, 227, 255);
    private final Border SIDE_PANEL_BORDER = new EmptyBorder(20,2,1,20);

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
        comboBoxLabel.setForeground(Color.WHITE);

        bankSideTopBar = new JPanel(new FlowLayout());
        bankSideTopBar.setBackground(MENU_BACKGROUND);
        bankSideTopBar.add(comboBoxLabel);
        bankSideTopBar.add(comboBox);

        importOFXButton = new JButton("Importar OFX");
        importOFXButton.setBorderPainted(false);
        importOFXButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        importOFXButton.setHorizontalAlignment(SwingConstants.LEADING);
        importOFXButton.setFont(new Font("Segoe UI",Font.BOLD,12));
        importOFXButton.setForeground(Color.WHITE);

        newStatmentButton = new JButton("Adicionar Extratos");
        newStatmentButton.setBorderPainted(false);
        newStatmentButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        newStatmentButton.setHorizontalAlignment(SwingConstants.LEADING);
        newStatmentButton.setFont(new Font("Segoe UI",Font.BOLD,12));
        newStatmentButton.setForeground(Color.WHITE);

        bankSideBottomBar = new JPanel(new GridLayout(6,1));
        bankSideBottomBar.setBackground(MENU_BACKGROUND);
        bankSideBottomBar.add(importOFXButton);
        bankSideBottomBar.add(newStatmentButton);

        newStatmentButton.setBackground(bankSideBottomBar.getBackground());
        importOFXButton.setBackground(bankSideBottomBar.getBackground());

        bankSideBar = new JPanel(new BorderLayout());
        bankSideBar.setBorder(SIDE_PANEL_BORDER);
        bankSideBar.setBackground(MENU_BACKGROUND);
        bankSideBar.add(bankSideTopBar,BorderLayout.NORTH);
        bankSideBar.add(bankSideBottomBar,BorderLayout.CENTER);

        bankTable = new BankTable();

        setLayout(new BorderLayout());
        add(bankSideBar, BorderLayout.WEST);
        add(bankTable,BorderLayout.CENTER);

    }

}
