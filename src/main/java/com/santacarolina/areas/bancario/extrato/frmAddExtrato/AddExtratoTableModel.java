package com.santacarolina.areas.bancario.extrato.frmAddExtrato;

import com.santacarolina.areas.mainFrame.pgBanco.ExtratoTableModel;
import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.model.beans.Extrato;
import com.santacarolina.ui.CustomTableModelImpl;

import java.util.ArrayList;
import java.util.List;

public class AddExtratoTableModel implements CustomTableModel<Extrato> {

    private CustomTableModelImpl<Extrato> tableModel;
    private ExtratoTableModel extratoTableModel;
    private List<Extrato> list;

    public AddExtratoTableModel() {
        this.list = new ArrayList<>();
        this.tableModel = new CustomTableModelImpl<>(this, list);
        this.extratoTableModel = new ExtratoTableModel(new ArrayList<>());
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
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 1) return false;
        else return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Extrato e = list.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> e.getDataTransacao();
            case 1 -> e.getContaBancaria().toString();
            case 2 -> e.getCatBancaria();
            case 3 -> e.getDescricao();
            case 4 -> e.getValor();
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public Extrato getObject(int rowIndex) { return list.get(rowIndex); }

}
