package com.santacarolina.ui;

import com.santacarolina.util.StringConversor;

import javax.swing.table.DefaultTableCellRenderer;

public class CurrencyCellRenderer extends DefaultTableCellRenderer {

    @Override
    protected void setValue(Object value) {
        String input = String.valueOf(value);
        String newValue = StringConversor.getCurrency(Double.parseDouble(input));
        super.setValue(newValue);
    }

}
