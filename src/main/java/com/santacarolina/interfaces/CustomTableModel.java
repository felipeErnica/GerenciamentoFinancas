package com.santacarolina.interfaces;

import com.santacarolina.ui.CustomTableModelImpl;

public interface CustomTableModel<T> {
        CustomTableModelImpl<T> getBaseModel();
        int getRowCount();
        int getColumnCount();
        String getColumnName(int columnIndex);
        Class<?> getColumnClass(int columnIndex);
        boolean isCellEditable(int rowIndex, int columnIndex);
        Object getValueAt(int rowIndex, int columnIndex);
        T getObject (int rowIndex);
}
