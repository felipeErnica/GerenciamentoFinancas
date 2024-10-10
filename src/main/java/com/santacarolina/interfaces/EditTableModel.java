package com.santacarolina.interfaces;

public interface EditTableModel<T> extends CustomTableModel<T> {
    void addNewRow();
    void removeRows(int[] rows);
    void fireTableCellUpdated(int row, int column);
}
