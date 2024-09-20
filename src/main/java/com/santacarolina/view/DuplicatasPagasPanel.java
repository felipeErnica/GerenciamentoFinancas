package com.santacarolina.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DuplicatasPagasPanel extends JPanel{

    private JScrollPane scrollPane;
    private JTable table;

    public DuplicatasPagasPanel() {
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
