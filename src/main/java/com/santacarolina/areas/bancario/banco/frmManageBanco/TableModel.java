package com.santacarolina.areas.bancario.banco.frmManageBanco;

import com.santacarolina.dao.BancoDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.model.Banco;
import com.santacarolina.ui.CustomTableModelImpl;

import java.util.List;

public class TableModel implements CustomTableModel<Banco> {

    private static final BancoDAO bancoDAO = new BancoDAO();

    private CustomTableModelImpl<Banco> baseModel;
    private List<Banco> list;

    private String[] columnNames = {
            "Apelido do Banco",
            "Nome do Banco"
    };

    public TableModel() throws FetchFailException {
        this.baseModel = new CustomTableModelImpl<>(this, bancoDAO.findAll());
        list = baseModel.getList();
    }

    @Override
    public CustomTableModelImpl<Banco> getBaseModel() { return baseModel; }

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
        Banco banco = list.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> banco.getNomeBanco();
            case 1 -> banco.getApelidoBanco();
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}

    @Override
    public Banco getObject(int rowIndex) { return list.get(rowIndex); }

    public void removeRow(int row) { baseModel.removeRow(row); }

    public void requeryTable() throws FetchFailException {
        baseModel.setList(bancoDAO.findAll());
        list = baseModel.getList();
    }

}
