package com.santacarolina.ui;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.EventObject;

public class UpperCaseCellEditor extends AbstractCellEditor implements TableCellEditor {

    private JTextField field;

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        field = new JTextField();
        if (value != null) field.setText(String.valueOf(value));
        return field;
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        if (e instanceof MouseEvent mouse) {
            return mouse.getClickCount() >= 2;
        } else return true;
    }

    @Override
    public Object getCellEditorValue() {
        return field.getText().toUpperCase();
    }

}
