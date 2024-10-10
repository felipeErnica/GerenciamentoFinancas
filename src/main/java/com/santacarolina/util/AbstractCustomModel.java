package com.santacarolina.util;

import com.santacarolina.exceptions.FetchFailException;

import javax.swing.table.AbstractTableModel;

public abstract class AbstractCustomModel<T> extends AbstractTableModel {

    @Override
    public abstract int getRowCount();
    @Override
    public abstract String getColumnName(int column);
    @Override
    public abstract int getColumnCount();
    @Override
    public abstract boolean isCellEditable(int rowIndex, int columnIndex);
    @Override
    public abstract Object getValueAt(int rowIndex, int columnIndex);
    @Override
    public abstract Class<?> getColumnClass(int columnIndex);
    @Override
    public abstract void setValueAt(Object aValue, int rowIndex, int columnIndex);
    public abstract T getObject(int rowIndex);
    public abstract void addRow(T t);
    public abstract void addNewRow();
    public abstract void removeRow(int row);
    public abstract void removeRows(int[] rows);
    public abstract void requeryTable() throws FetchFailException;

}
