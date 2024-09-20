package com.santacarolina.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DuplicatasAbertasPanel extends JPanel {

    private JScrollPane scrollPane;
    private JTable table;

    public DuplicatasAbertasPanel() {
        initComponents();
    }

    private void initComponents() {

        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.addColumn("...");
        tableModel.addColumn("Duplicata");
        tableModel.addColumn("Num. Doc.");
        tableModel.addColumn("Status");
        tableModel.addColumn("Forma de Pagamento");
        tableModel.addColumn("Data de Vencimento");
        tableModel.addColumn("Fornecedor");
        tableModel.addColumn("Valor");
        tableModel.addColumn("Conta Bancária");

        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);

        for (int i =0; i <=10; i++){
            Object[] objects = new Object[9];
            char c = 'a';
            objects[0] = "Abrir";
            for (int j = 1; j<=8; j++){
                objects[j] = c;
                c++;
            }
            tableModel.addRow(objects);
        }

        setLayout(new GridLayout());
        add(scrollPane);
    }
}
