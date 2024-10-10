package com.santacarolina.areas.bancario.extrato.frmAddExtrato;

import com.santacarolina.model.ContaBancaria;
import com.santacarolina.model.Extrato;

import java.util.List;

public class AddExtratoModel {

    private ContaBancaria contaBancaria;
    private AddExtratoTableModel tableModel;

    public AddExtratoModel(ContaBancaria contaBancaria, List<Extrato> list) {
        tableModel = new AddExtratoTableModel(list);
        this.contaBancaria = contaBancaria;
    }

    public ContaBancaria getContaBancaria() { return contaBancaria; }
    public AddExtratoTableModel getTableModel() { return tableModel; }

    public void addRow(Extrato extrato) { tableModel.addRow(extrato); }
    public void removeRows(int[] rows) { tableModel.removeRows(rows); }
    public List<Extrato> getList() { return tableModel.getList(); }
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) { tableModel.setValueAt(aValue, rowIndex, columnIndex); }
    public Object getValueAt(int rowIndex, int columnIndex) { return tableModel.getValueAt(rowIndex, columnIndex); }

}
