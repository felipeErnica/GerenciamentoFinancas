package com.santacarolina.areas.documentos.frmDoc.frmClassificacao;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.formdev.flatlaf.FlatClientProperties;
import com.santacarolina.model.CategoriaContabil;

import net.miginfocom.swing.MigLayout;

public class FormView {

    private JDialog dialog;

    private JComboBox<CategoriaContabil> categoriaComboBox;

    private JLabel searchLabel;
    private JTextField searchField;

    private JScrollPane scrollPane;
    private JTable table;

    public FormView() { initComponents(); }

    private void initComponents() {
        dialog = new JDialog();
        dialog.setTitle("Tabela de Classificação");

        JLabel categoriaLabel = new JLabel("Selecionar Categoria:");
        categoriaComboBox = new JComboBox<>();
        AutoCompleteDecorator.decorate(categoriaComboBox);
        categoriaLabel.setLabelFor(categoriaComboBox);

        searchLabel = new JLabel("Pesquisar:");
        searchField = new JTextField();
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pesquisar Classificação...");
        searchLabel.setLabelFor(searchField);

        table = new JTable();
        scrollPane = new JScrollPane(table);

        dialog.setLayout(new MigLayout("insets 20",
                "[][grow, fill]",
                "[][][grow, fill]"));

        dialog.add(searchLabel);
        dialog.add(searchField, "wrap");
        dialog.add(categoriaLabel);
        dialog.add(categoriaComboBox, "wrap");
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
    public JComboBox<CategoriaContabil> getCategoriaComboBox() { return categoriaComboBox; }

}
