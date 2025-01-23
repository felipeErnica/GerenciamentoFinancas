package com.santacarolina.areas.categoria.frmManageCategoria;

import java.util.List;

import com.santacarolina.dao.CategoriaDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.model.CategoriaContabil;
import com.santacarolina.ui.CustomTableModelImpl;

public class CategoriaTableModel implements CustomTableModel<CategoriaContabil> {

    private CustomTableModelImpl<CategoriaContabil> baseModel;
    private List<CategoriaContabil> list;
    
    private String[] columnNames = {
        "Fluxo de Caixa",
        "Número da Etiqueta",
        "Nome da Categoria"
    };

    private FilterModel filterModel;

    public CategoriaTableModel() throws FetchFailException {
        this.list = new CategoriaDAO().findAll();
        this.baseModel = new CustomTableModelImpl<>(this, list);
        filterModel = new FilterModel(this);
    }

    @Override
    public CustomTableModelImpl<CategoriaContabil> getBaseModel() { return baseModel; }

    @Override
    public int getRowCount() { return baseModel.getRowCount(); }

    @Override
    public int getColumnCount() { return columnNames.length; }

    @Override
    public String getColumnName(int columnIndex) { return columnNames[columnIndex]; }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 1) return Long.class;
        else return String.class; 
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CategoriaContabil cat = getObject(rowIndex);
        return switch(columnIndex) {
            case 0 -> cat.getFluxoCaixa().toString();
            case 1 -> cat.getNumeroCategoria();
            case 2 -> cat.getNome();
            default -> throw new IllegalArgumentException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) { }

    @Override
    public CategoriaContabil getObject(int rowIndex) { return baseModel.getObject(rowIndex); }

    public void requeryTable() throws FetchFailException {
        list = new CategoriaDAO().findAll();
        filterModel.setFilters();
    }

    public List<CategoriaContabil> getList() { return list; }
    public FilterModel getFilterModel() { return filterModel; }

}

