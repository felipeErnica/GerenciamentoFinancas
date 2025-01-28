package com.santacarolina.areas.bancario.conciliacao.frmManageConciliados;

import java.awt.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.renderer.DefaultTableRenderer;

import com.santacarolina.util.StringConversor;

/**
 * ConciliacaoRenderer
 */
public class ConciliacaoRenderer implements TableCellRenderer {

    private DefaultTableRenderer baseRenderer;

    public ConciliacaoRenderer() { baseRenderer = new DefaultTableRenderer(); }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        JLabel c = (JLabel) baseRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value == null) return c;
        switch (column) {
            case 0 -> {
                LocalDate date = (LocalDate) value;
                c.setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                c.setHorizontalAlignment(SwingConstants.CENTER);
            }
            case 1 -> c.setHorizontalAlignment(SwingConstants.CENTER);
            case 2 -> c.setHorizontalAlignment(SwingConstants.CENTER);
            case 5 -> c.setText(StringConversor.getCurrency((double) value));
            case 6 -> c.setText(StringConversor.getCurrency((double) value));
        };
        return c;
    }

}
