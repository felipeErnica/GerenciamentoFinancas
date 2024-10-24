package com.santacarolina.areas.bancario.contaBancaria.frmManageContaBancaria;

import java.util.List;

import com.santacarolina.dao.ContaDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.model.ContaBancaria;
import com.santacarolina.ui.CustomTableModelImpl;

public class ContaTableModel implements CustomTableModel<ContaBancaria> {

    private CustomTableModelImpl<ContaBancaria> baseModel;
    private List<ContaBancaria> list;

    private String[] columnNames = {
        "Apelido da Conta",
        "Abreviação da Conta",
        "Banco",
        "Agência",
        "Número da Conta"
    };

    public ContaTableModel() throws FetchFailException {
        this.list = new ContaDAO().findAll();
        this.baseModel = new CustomTableModelImpl<>(this, list);
    }

    @Override
    public CustomTableModelImpl<ContaBancaria> getBaseModel() { return baseModel; }

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
        ContaBancaria conta = list.get(rowIndex);
        return switch(columnIndex) {
            case 0 -> conta.getNomeConta();
            case 1 -> conta.getAbreviacaoConta();
            case 2 -> conta.getBanco().toString();
            case 3 -> conta.getAgencia();
            case 4 -> conta.getNumeroConta();
            default -> throw new IllegalArgumentException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) { }

    @Override
    public ContaBancaria getObject(int rowIndex) { return list.get(rowIndex); }

    public void requeryTable() throws FetchFailException {
        list = new ContaDAO().findAll();
        baseModel.setList(list);
    }

}

