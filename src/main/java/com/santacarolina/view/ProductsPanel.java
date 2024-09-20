package com.santacarolina.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductsPanel extends JPanel {

    private JScrollPane scrollPane;
    private JTable table;

    public ProductsPanel() {
        initComponents();
    }

    private void initComponents() {

        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.addColumn("Data de Emissão");
        tableModel.addColumn("Pasta Contábil");
        tableModel.addColumn("Emitente");
        tableModel.addColumn("Tipo de Mercadoria");
        tableModel.addColumn("Descrição");
        tableModel.addColumn("Unidade");
        tableModel.addColumn("Quantidade");
        tableModel.addColumn("Valor Unitário");
        tableModel.addColumn("Valor Total");

        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);

        setLayout(new GridLayout());
        add(scrollPane);
    }
}
