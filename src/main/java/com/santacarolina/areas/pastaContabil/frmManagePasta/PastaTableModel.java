package com.santacarolina.areas.pastaContabil.frmManagePasta;

import com.santacarolina.dao.PastaDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.model.PastaContabil;
import com.santacarolina.ui.CustomTableModelImpl;

import java.util.List;

public class PastaTableModel implements CustomTableModel<PastaContabil> {

    private static final PastaDAO pastaDAO = new PastaDAO();

    private CustomTableModelImpl<PastaContabil> baseModel;
    private List<PastaContabil> list;

    private String[] columnNames = {
            "Nome da Pasta",
            "Caminho da Pasta",
            "Conta Bancária"
    };

    public PastaTableModel() throws FetchFailException {
        list = pastaDAO.findAll();
        this.baseModel = new CustomTableModelImpl<>(this, list);
    }

    @Override
    public CustomTableModelImpl<PastaContabil> getBaseModel() { return baseModel; }

    @Override
    public int getRowCount() { return list.size(); }

    @Override
    public int getColumnCount() { return columnNames.length; }

    @Override
    public String getColumnName(int columnIndex) { return columnNames[columnIndex]; }

    @Override
    public Class<?> getColumnClass(int columnIndex) { return String.class; }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PastaContabil p = list.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> p.getNome();
            case 1 -> p.getCaminhoPasta();
            case 2 -> p.getContaBancaria().toString();
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) { }

    @Override
    public PastaContabil getObject(int rowIndex) { return baseModel.getObject(rowIndex); }

    public void requeryTable() throws FetchFailException {
        list = pastaDAO.findAll();
        baseModel.setList(list);
    }

    public void removeRow(int row) { baseModel.removeRow(row); }
}
