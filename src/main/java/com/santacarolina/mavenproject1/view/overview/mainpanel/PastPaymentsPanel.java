package com.santacarolina.mavenproject1.view.overview.mainpanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PastPaymentsPanel extends JPanel{

    private JScrollPane scrollPane;
    private JTable table;

    public PastPaymentsPanel() {
        initComponents();
    }

    private void initComponents() {

        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.addColumn("Data de Vencimento");
        tableModel.addColumn("Num. Doc.");
        tableModel.addColumn("Fornecedor");
        tableModel.addColumn("Duplicata");
        tableModel.addColumn("Data de Vencimento");
        tableModel.addColumn("Conta Bancária");
        tableModel.addColumn("Valor");

        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);

        setLayout(new GridLayout());
        add(scrollPane);
    }

}
