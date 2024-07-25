package com.santacarolina.mavenproject1.view.overview.mainpanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class BankingPanel extends JPanel {

    private JPanel bankInformationPane;
    private JPanel bankSideBar;
    private JPanel bankTopBar;
    private BankTable bankTable;
    private JPanel statmentHandlingPanel;
    private JButton nonConciliatedButton;
    private JButton conciliationButton;
    private JButton concilatedButton;

    public BankingPanel() {
        initComponents();
    }

    private void initComponents(){

        statmentHandlingPanel = new JPanel();

        nonConciliatedButton = new JButton("Ver Extratos Não Conciliados");
        nonConciliatedButton.setPreferredSize(new Dimension(200,30));

        conciliationButton = new JButton("Iniciar Conciliação");
        conciliationButton.setPreferredSize(new Dimension(200,30));

        concilatedButton = new JButton("Arquivos Conciliados");
        concilatedButton.setPreferredSize(new Dimension(200,30));

        statmentHandlingPanel.setBorder(new TitledBorder("Conciliação"));
        statmentHandlingPanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,10));
        statmentHandlingPanel.add(concilatedButton);
        statmentHandlingPanel.add(conciliationButton);
        statmentHandlingPanel.add(nonConciliatedButton);

        bankInformationPane = new JPanel();

        bankSideBar = new JPanel();
        bankSideBar.setBackground(Color.red);

        bankTopBar = new JPanel();
        bankTopBar.setBackground(Color.MAGENTA);

        bankTable = new BankTable();

        bankInformationPane.setLayout(new BorderLayout());
        bankInformationPane.add(bankSideBar,BorderLayout.WEST);
        bankInformationPane.add(bankTopBar,BorderLayout.NORTH);
        bankInformationPane.add(bankTable,BorderLayout.CENTER);

        setBorder(new EmptyBorder(20,20,20,20));
        setLayout(new BorderLayout(20,20));
        add(statmentHandlingPanel,BorderLayout.NORTH);
        add(bankInformationPane,BorderLayout.CENTER);

    }

}
