package com.santacarolina.areas.frmDoc;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.model.beans.Produto;
import com.santacarolina.model.tablemodels.ProdutoTableModel;
import com.santacarolina.ui.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;

public class ProdutosDocPanel {

    private JPanel mainPanel;
    private JTable table;
    private JButton addButton;

    public ProdutosDocPanel() {
        buildUI();
    }

    private void buildUI() {

        this.mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        EditableTablePanel<Produto> editableTablePanel = new EditableTablePanel<>(new ProdutoTableModel());
        table = editableTablePanel.getTable();
        table.setColumnSelectionAllowed(true);

        addButton = new ActionSVGButton("Adicionar Produto", new FlatSVGIcon("icon/add_icon.svg"));

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout(FlowLayout.TRAILING,20,10));
        northPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        northPanel.add(addButton);
        mainPanel.add(northPanel,BorderLayout.NORTH);
        mainPanel.add(editableTablePanel, BorderLayout.CENTER);

        for (int column = 0; column < table.getColumnCount(); column++){
            DefaultTableCellRenderer cellRenderer = null;
            TableColumn columnModel = table.getColumnModel().getColumn(column);

            switch (column) {
                case 0 -> {
                    cellRenderer = new DefaultTableCellRenderer();
                    cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                    columnModel.setPreferredWidth(100);
                }
                case 1 -> {
                    columnModel.setCellEditor(new UpperCaseCellEditor());
                    columnModel.setPreferredWidth(250);
                }
                case 2 -> {
                    columnModel.setCellEditor(new UpperCaseCellEditor());
                    cellRenderer = new DefaultTableCellRenderer();
                    cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                }
                case 3 -> {
                    cellRenderer = new DefaultTableCellRenderer();
                    cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                    columnModel.setCellEditor(new DoubleCellEditor());
                }
                case 4, 5 -> {
                    cellRenderer = new CurrencyCellRenderer();
                    cellRenderer.setHorizontalAlignment(SwingConstants.LEFT);
                    columnModel.setCellEditor(new DoubleCellEditor());
                }
            }
            columnModel.setCellRenderer(cellRenderer);
        }
    }

    public JPanel getMainPanel() { return mainPanel; }
    public JTable getTable() { return table; }
    public JButton getAddButton() { return addButton; }

}

