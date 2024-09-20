package com.santacarolina.ui;

import com.formdev.flatlaf.FlatClientProperties;
import com.santacarolina.util.StringConversor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.EventObject;

public class DoubleCellEditor extends AbstractCellEditor implements TableCellEditor {

    private static Logger logger = LogManager.getLogger();
    private JTextField field;

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        field = new JTextField();
        if (value != null) {
            field.setText(String.valueOf(value));
        }
        return field;
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        if (e instanceof MouseEvent) {
            MouseEvent mouse = (MouseEvent) e;
            return mouse.getClickCount() >= 2;
        } return true;
    }

    @Override
    public Object getCellEditorValue() {
        try {
            field.setText(StringConversor.getDoubleFromLocalFormat(field.getText()).toString());
            field.putClientProperty(FlatClientProperties.OUTLINE, null);
        } catch (ParseException ex) {
            field.putClientProperty(FlatClientProperties.OUTLINE, FlatClientProperties.OUTLINE_ERROR);
            field.setToolTipText("Formato inválido!");
            field.setText("");
            logger.error(ex.getMessage());
        }
        return field.getText();
    }

}
