package com.santacarolina.ui;

import javax.swing.table.DefaultTableCellRenderer;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateCellRenderer extends DefaultTableCellRenderer {

    @Override
    protected void setValue(Object value) {
        try {
            String input = String.valueOf(value);
            String newValue;
            if (input.equals("null")) newValue = "";
            else newValue = LocalDate.parse(input).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            super.setValue(newValue);
        } catch (DateTimeException e) {
            super.setValue(value);
        }
    }

}
