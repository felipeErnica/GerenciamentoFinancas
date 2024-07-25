package com.santacarolina.mavenproject1.view.overview.mainpanel;

import com.santacarolina.mavenproject1.model.enums.PaymentType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class FuturePaymentsPanel extends JPanel {

    private JScrollPane scrollPane;
    private JTable table;

    public FuturePaymentsPanel() {
        initComponents();
    }

    private void initComponents() {

        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.addColumn("Duplicata");
        tableModel.addColumn("Num. Doc.");
        tableModel.addColumn("Status");
        tableModel.addColumn("Forma de Pagamento");
        tableModel.addColumn("Data de Vencimento");
        tableModel.addColumn("Fornecedor");
        tableModel.addColumn("Valor");
        tableModel.addColumn("Conta Bancária");
        tableModel.addRow(new Object[]{1, 12345, null, PaymentType.BANK_TRANSFER, LocalDate.now(), "João", 2546.25, "BB"});

        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);

        setLayout(new GridLayout());
        add(scrollPane);
    }
}
