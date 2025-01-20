package com.santacarolina.areas.bancario.extrato.pgExtrato;

import com.santacarolina.model.Extrato;
import com.santacarolina.util.StringConversor;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("rawtypes")
public class ExtratoRenderer implements TableCellRenderer {

    private DefaultTableCellRenderer renderer;
    private ExtratoTableModel model;
    private RowSorter sorter;

    public ExtratoRenderer(ExtratoTableModel model, RowSorter sorter) {
        this.model = model;
        renderer = new DefaultTableCellRenderer();
        this.sorter = sorter;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel c = (JLabel) renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        int modelRow = sorter.convertRowIndexToModel(row);
        Extrato extrato = model.getObject(modelRow);
        switch (column) {
            case 0, 2 -> c.setHorizontalAlignment(SwingConstants.CENTER);
            case 1 -> {
                c.setText(extrato.getDataTransacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                c.setHorizontalAlignment(SwingConstants.CENTER);
            }
            case 3, 4 -> c.setHorizontalAlignment(SwingConstants.LEFT);
            case 5 -> {
                c.setText(StringConversor.getCurrency(extrato.getValor()));
                c.setHorizontalAlignment(SwingConstants.LEFT);
            }
        }
        return c;
    }
}
