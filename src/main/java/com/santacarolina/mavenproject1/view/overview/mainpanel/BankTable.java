package com.santacarolina.mavenproject1.view.overview.mainpanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BankTable extends JPanel {

    private JScrollPane scrollPane;
    private JTable table;

    public BankTable() {
        initComponents();
    }

    private void initComponents(){

        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.addColumn("Data");
        tableModel.addColumn("Conta");
        tableModel.addColumn("Categoria");
        tableModel.addColumn("Histórico");
        tableModel.addColumn("Valor");

        table = new JTable(tableModel);
        scrollPane = new JScrollPane();
        scrollPane.add(table);

        setLayout(new GridLayout());
        add(scrollPane);
    }
}
