package com.santacarolina.ui;

import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.santacarolina.interfaces.CustomTableModel;

public class CustomTableModelImpl<T> implements TableModel {

    private AbstractTableModel model;
    private CustomTableModel<T> childModel;
    private List<T> list;

    public CustomTableModelImpl(CustomTableModel<T> childModel, List<T> list) {
        this.model = new DefaultTableModel();
        this.childModel = childModel;
        this.list = list;
    }

    public List<T> getList() { return list; }
    public int getRowCount() { return list.size(); }
    public int getColumnCount() { return childModel.getColumnCount(); }
    public String getColumnName(int columnIndex) { return childModel.getColumnName(columnIndex); }
    public Class<?> getColumnClass(int columnIndex) { return childModel.getColumnClass(columnIndex); }
    public boolean isCellEditable(int rowIndex, int columnIndex) { return childModel.isCellEditable(rowIndex, columnIndex); }
    public Object getValueAt(int rowIndex, int columnIndex) { return childModel.getValueAt(rowIndex, columnIndex); }
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) { childModel.setValueAt(aValue, rowIndex, columnIndex); }
    public void addTableModelListener(TableModelListener l) { model.addTableModelListener(l); }
    public void removeTableModelListener(TableModelListener l) { model.removeTableModelListener(l); }
    public T getObject(int rowIndex) { return list.get(rowIndex); }
    public void fireTableDataChanged() { model.fireTableDataChanged(); }
    public void fireTableCellUpdated(int row, int column) { model.fireTableCellUpdated(row, column); }

    public void setList(List<T> list) {
        this.list = list;
        model.fireTableDataChanged();
    }

    public void addRow(T t) {
        list.add(t);
        model.fireTableDataChanged();
    }

    public void removeRow(int row) {
        list.remove(row);
        model.fireTableRowsDeleted(row, row);
    }

    public void removeRows(int[] rows) {
        for (int i = rows.length - 1; i >= 0; i--) {
            removeRow(rows[i]);
        }
    }

}
