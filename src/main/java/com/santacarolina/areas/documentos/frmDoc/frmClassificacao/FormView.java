package com.santacarolina.areas.documentos.frmDoc.frmClassificacao;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.TableColumnModel;

public class FormView {

    private JDialog dialog;
    private JScrollPane scrollPane;
    private JLabel searchLabel;
    private JTextField searchField;
    private JTable table;

    public FormView() { initComponents(); }

    private void initComponents() {
        dialog = new JDialog();
        dialog.setTitle("Tabela de Classificação");

        searchLabel = new JLabel("Pesquisar:");
        searchField = new JTextField();
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pesquisar Classificação...");
        searchLabel.setLabelFor(searchField);

        table = new JTable();
        scrollPane = new JScrollPane(table);

        dialog.setLayout(new MigLayout("insets 20",
                "[][grow, fill]",
                "[][grow, fill]"));

        dialog.add(searchLabel);
        dialog.add(searchField, "wrap");
        dialog.add(scrollPane, "span");
    }

    public void formatColumns() {
        int width = scrollPane.getWidth()/100;
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(width*20);
        columnModel.getColumn(1).setPreferredWidth(width*80);
    }

    public JDialog getDialog() { return dialog; }
    public JTextField getSearchField() { return searchField; }
    public JTable getTable() { return table; }
    public JTextField getTextField() { return searchField; }

}