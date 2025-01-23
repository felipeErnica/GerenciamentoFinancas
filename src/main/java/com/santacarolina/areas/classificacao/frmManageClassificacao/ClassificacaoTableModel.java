package com.santacarolina.areas.classificacao.frmManageClassificacao;

import java.util.List;

import com.santacarolina.dao.ClassificacaoDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.model.ClassificacaoContabil;
import com.santacarolina.ui.CustomTableModelImpl;

/**
 * ClassificacaoTableModel
 */
public class ClassificacaoTableModel implements CustomTableModel<ClassificacaoContabil> {


    private final CustomTableModelImpl<ClassificacaoContabil> baseModel;
    private List<ClassificacaoContabil> list;

    private FilterModel filterModel;

    private final String[] columnNames = {
        "Receita X Despesa",
        "Categoria Contábil",
        "Número da Classificação",
        "Classificação"
    };

    public ClassificacaoTableModel() throws FetchFailException {
        list = new ClassificacaoDAO().findAll();
        filterModel = new FilterModel(this);
        this.baseModel = new CustomTableModelImpl<>(this, list);
    }

    @Override
    public CustomTableModelImpl<ClassificacaoContabil> getBaseModel() { return baseModel; }

    @Override
    public int getRowCount() { return list.size(); }

    @Override
    public int getColumnCount() { return columnNames.length; }

    @Override
    public String getColumnName(int columnIndex) { return columnNames[columnIndex]; }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 3) return Long.class;
        else return String.class; 
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ClassificacaoContabil classificacaoContabil = getObject(rowIndex);
        return switch(columnIndex) {
            case 0 -> classificacaoContabil.getCategoria() != null ? classificacaoContabil.getCategoria().getFluxoCaixa().toString() : null;
            case 1 -> classificacaoContabil.getCategoria() != null ? classificacaoContabil.getCategoria().getNome() : null;
            case 2 -> classificacaoContabil.getNumeroIdentificacao();
            case 3 -> classificacaoContabil.getNomeClassificacao();
            default -> throw new IllegalArgumentException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) { }

    @Override
    public ClassificacaoContabil getObject(int rowIndex) { return baseModel.getObject(rowIndex); }

    public void requeryTable() throws FetchFailException {
        list = new ClassificacaoDAO().findAll();
        filterModel.setFilters();
    }
    
    public FilterModel getFilterModel() { return filterModel; }
    public List<ClassificacaoContabil> getList() { return list; }

}
