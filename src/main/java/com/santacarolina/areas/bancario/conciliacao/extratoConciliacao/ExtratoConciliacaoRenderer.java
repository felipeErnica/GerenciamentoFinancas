package com.santacarolina.areas.bancario.conciliacao.extratoConciliacao;

import java.awt.Component;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.santacarolina.model.Extrato;
import com.santacarolina.util.StringConversor;

public class ExtratoConciliacaoRenderer implements TableCellRenderer {

    private DefaultTableCellRenderer renderer;
    private ExtratoConciliacaoTableModel model;

    public ExtratoConciliacaoRenderer(ExtratoConciliacaoTableModel model) {
        this.model = model;
        renderer = new DefaultTableCellRenderer();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel c = (JLabel) renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        row = table.convertRowIndexToModel(row);
        Extrato extrato = model.getObject(row);
        switch (column) {
            case 0 -> {
                c.setText(extrato.getDataTransacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                c.setHorizontalAlignment(SwingConstants.CENTER);
            }
            case 1 -> c.setHorizontalAlignment(SwingConstants.CENTER);
            case 2, 3 -> c.setHorizontalAlignment(SwingConstants.LEFT);
            case 4 -> {
                c.setText(StringConversor.getCurrency(extrato.getValor()));
                c.setHorizontalAlignment(SwingConstants.LEFT);
            }
        }
        return c;
    }
}
