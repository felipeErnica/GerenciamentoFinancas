package com.santacarolina.areas.bancario.extrato.frmAddExtrato;

import java.text.ParseException;
import java.time.DateTimeException;
import java.util.List;

import com.santacarolina.areas.bancario.extrato.pgExtrato.ExtratoTableModel;
import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.model.Extrato;
import com.santacarolina.ui.CustomTableModelImpl;
import com.santacarolina.util.StringConversor;

public class AddExtratoTableModel implements CustomTableModel<Extrato> {

    private CustomTableModelImpl<Extrato> tableModel;
    private ExtratoTableModel extratoTableModel;
    private List<Extrato> list;

    public AddExtratoTableModel(List<Extrato> list) {
        this.list = list;
        this.tableModel = new CustomTableModelImpl<>(this, list);
        this.extratoTableModel = new ExtratoTableModel();
    }

    @Override
    public CustomTableModelImpl<Extrato> getBaseModel() { return tableModel; }

    @Override
    public int getRowCount() { return list.size(); }

    @Override
    public int getColumnCount() { return extratoTableModel.getColumnCount(); }

    @Override
    public String getColumnName(int columnIndex) { return extratoTableModel.getColumnName(columnIndex); }

    @Override
    public Class<?> getColumnClass(int columnIndex) { return extratoTableModel.getColumnClass(columnIndex); }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) { return columnIndex != 2 && columnIndex != 0; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Extrato e = list.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> rowIndex + 1;
            case 1 -> e.getDataTransacao();
            case 2 -> e.getContaBancaria().toString();
            case 3 -> e.getCatBancaria();
            case 4 -> e.getDescricao();
            case 5 -> e.getValor();
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Extrato e = list.get(rowIndex);
        switch (columnIndex) {
            case 1 -> {
                try {
                    e.setDataTransacao(StringConversor.transformDate(String.valueOf(aValue)));
                } catch (DateTimeException ex) {
                    e.setDataTransacao(null);
                }
            }
            case 3 -> e.setCatBancaria((String) aValue);
            case 4 -> e.setDescricao((String) aValue);
            case 5 -> {
                try {
                    e.setValor(StringConversor.getDoubleFromLocalFormat(String.valueOf(aValue)));
                } catch (ParseException ex) {
                    e.setValor(0);
                }
            }
        }
        tableModel.fireTableDataChanged();
    }

    @Override
    public Extrato getObject(int rowIndex) { return list.get(rowIndex); }
    public void addRow(Extrato extrato) { tableModel.addRow(extrato); }
    public void removeRows(int[] rows) { tableModel.removeRows(rows); }
    public List<Extrato> getList() { return list; }

}


