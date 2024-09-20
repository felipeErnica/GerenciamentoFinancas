package com.santacarolina.areas.frmDoc;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.enums.TipoPagamento;
import com.santacarolina.model.beans.Duplicata;
import com.santacarolina.model.tablemodels.DuplicataTableModel;
import com.santacarolina.ui.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;

public class DuplicatasDocPanel {

    private JPanel mainPanel;
    private JTable table;
    private TipoPagamento tipoPagamento;
    private JComboBox<TipoPagamento> pagtoComboBox;
    private JButton addDadoBancario;
    private JButton addButton;

    public DuplicatasDocPanel() {
        buildUI();
    }

    private void buildUI() {

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        EditableTablePanel<Duplicata> tablePanel = new EditableTablePanel<>(new DuplicataTableModel());
        table = tablePanel.getTable();
        table.setColumnSelectionAllowed(true);

        addButton = new ActionSVGButton("Adicionar Duplicata", new FlatSVGIcon("icon/add_icon.svg"));
        addDadoBancario = new ActionSVGButton("Adicionar Dado Bancário", new FlatSVGIcon("icon/add_info_pagto_icon.svg"));
        addDadoBancario.setEnabled(false);

        pagtoComboBox = new JComboBox<>(TipoPagamento.values());
        JLabel pagtoLabel = new JLabel("Método de Pagamento:");
        pagtoLabel.setLabelFor(pagtoComboBox);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout(FlowLayout.TRAILING,20,10));
        northPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        northPanel.add(addButton);
        northPanel.add(pagtoLabel);
        northPanel.add(pagtoComboBox);
        northPanel.add(addDadoBancario);
        mainPanel.add(northPanel,BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) formatColumns(i);
    }

    private void formatColumns(int i){
        DefaultTableCellRenderer renderer = null;
        TableColumn column = table.getColumnModel().getColumn(i);
        switch (i){
            case 0 -> {
                column.setPreferredWidth(30);
                renderer = new DefaultTableCellRenderer();
                renderer.setHorizontalAlignment(SwingConstants.CENTER);
            }
            case 1 -> {
                column.setPreferredWidth(150);
                renderer = new DefaultTableCellRenderer();
                renderer.setHorizontalAlignment(SwingConstants.CENTER);
            }
            case 2 -> {
                renderer = new DateCellRenderer();
                renderer.setHorizontalAlignment(SwingConstants.CENTER);
                column.setCellEditor(new DateCellEditor());
            }
            case 3 -> {
                column.setPreferredWidth(100);
                renderer = new DefaultTableCellRenderer();
                renderer.setHorizontalAlignment(SwingConstants.CENTER);
            }
            case 4 -> {
                renderer = new CurrencyCellRenderer();
                column.setCellEditor(new DoubleCellEditor());
            }
            case 5 -> column.setPreferredWidth(200);
        }
        column.setCellRenderer(renderer);
    }

    public JPanel getMainPanel() { return mainPanel; }
    public JTable getTable() { return table; }
    public JComboBox<TipoPagamento> getPagtoComboBox() { return pagtoComboBox; }
    public JButton getAddDadoBancario() { return addDadoBancario; }
    public JButton getAddButton() { return addButton; }
}

