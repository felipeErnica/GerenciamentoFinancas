package com.santacarolina.areas.bancario.banco.frmManageBanco;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.santacarolina.dao.BancoDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.model.Banco;
import com.santacarolina.ui.CustomTableModelImpl;

public class TableModel implements CustomTableModel<Banco> {

    private String filterSearch;

    private CustomTableModelImpl<Banco> baseModel;
    private List<Banco> list;
    private List<Banco> filteredList;

    private String[] columnNames = {
            "Nome do Banco",
            "Apelido do Banco"
    };

    public TableModel() throws FetchFailException {
        list = new BancoDAO().findAll();
        this.baseModel = new CustomTableModelImpl<>(this, list);
        filteredList = new ArrayList<>(list);
    }

    @Override
    public CustomTableModelImpl<Banco> getBaseModel() { return baseModel; }

    @Override
    public int getRowCount() { return baseModel.getRowCount(); }

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
        Banco banco = getObject(rowIndex);
        return switch (columnIndex) {
            case 0 -> banco.getNomeBanco();
            case 1 -> banco.getApelidoBanco();
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) { }

    @Override
    public Banco getObject(int rowIndex) { return baseModel.getObject(rowIndex); }

    public void removeRow(int row) { baseModel.removeRow(row); }

    public void requeryTable() throws FetchFailException {
        list = new BancoDAO().findAll();
        filteredList = new ArrayList<>(list);
        filterList();
    }

    public void setFilterSearch(String filterSearch) {
        this.filterSearch = filterSearch;
        filterList();
    }

    public void filterList() {

        filteredList = new ArrayList<>(list);

        if (StringUtils.isBlank(filterSearch)) {
            baseModel.setList(list);
            return;
        }

        filteredList = filteredList.stream()
            .filter(b -> !(StringUtils.isBlank(b.getNomeBanco()) && StringUtils.isBlank(b.getApelidoBanco())))
            .filter(b -> b.getNomeBanco().contains(filterSearch.toUpperCase()) || apelidoContainsFilter(b.getApelidoBanco()))
            .collect(Collectors.toList());

        baseModel.setList(filteredList);
    }

    private boolean apelidoContainsFilter(String apelido) {
        if (apelido == null) return false;
        return apelido.contains(filterSearch.toUpperCase());
    }

}
