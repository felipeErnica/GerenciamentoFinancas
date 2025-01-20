package com.santacarolina.areas.duplicatas.common;

import com.santacarolina.model.Duplicata;
import com.santacarolina.util.StringConversor;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class DuplicataRenderer implements TableCellRenderer {

    private DefaultTableCellRenderer cellRenderer;
    private DupTableModel model;

    public DuplicataRenderer(DupTableModel model) {
        this.cellRenderer = new DefaultTableCellRenderer();
        this.model = model;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel c = (JLabel) cellRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Duplicata dup = model.getObject(row);
        switch (column) {
            case 1 -> {
                c.setHorizontalAlignment(SwingConstants.CENTER);
                c.setText(dup.getDataVencimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
            case 0, 2, 3 -> c.setHorizontalAlignment(SwingConstants.CENTER);
            case 4 -> c.setHorizontalAlignment(SwingConstants.LEFT);
            case 5 -> {
                c.setText(StringConversor.getCurrency(dup.getValor()));
                c.setHorizontalAlignment(SwingConstants.LEFT);
            }
        }
        return c;
    }

    public Font getFont() { return cellRenderer.getFont(); }

}
