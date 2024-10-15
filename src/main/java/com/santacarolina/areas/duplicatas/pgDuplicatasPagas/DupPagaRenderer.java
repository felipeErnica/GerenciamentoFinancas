package com.santacarolina.areas.duplicatas.pgDuplicatasPagas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

import com.santacarolina.areas.duplicatas.common.DupTableModel;
import com.santacarolina.areas.duplicatas.common.DuplicataRenderer;
import com.santacarolina.dto.DuplicataDTO;

public class DupPagaRenderer implements TableCellRenderer {

    private static final Color INCOME_BLUE = UIManager.getColor("Duplicatas.incomeColor");
    private static final Color FOREGROUND_COLOR = UIManager.getColor("@foreground");

    private DuplicataRenderer cellRenderer;
    private DupTableModel model;
    private Font boldFont;
    private Font normalFont;

    public DupPagaRenderer(DupTableModel model) {
        this.cellRenderer = new DuplicataRenderer(model);
        this.model = model;
        normalFont = cellRenderer.getFont();
        boldFont = normalFont.deriveFont(Font.BOLD);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel c = (JLabel) cellRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        DuplicataDTO dup = model.getObject(row);
        if (dup.getValor() > 0) {
            c.setForeground(INCOME_BLUE);
            c.setFont(boldFont);
        } else {
            c.setForeground(FOREGROUND_COLOR);
            c.setFont(normalFont);
        }
        return c;
    }

}
