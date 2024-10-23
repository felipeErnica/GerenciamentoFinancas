package com.santacarolina.areas.documentos.frmDoc.frmClassificacao;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.santacarolina.dao.ClassificacaoDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.model.CategoriaContabil;
import com.santacarolina.model.ClassificacaoContabil;
import com.santacarolina.model.Produto;
import com.santacarolina.ui.CustomTableModelImpl;

public class FormModel implements CustomTableModel<ClassificacaoContabil> {

    private Produto produto;
    private CategoriaContabil categoriaContabil;
    private String searchField;
    private CustomTableModelImpl<ClassificacaoContabil> tableModel;
    private List<ClassificacaoContabil> unfilteredList;
    private List<ClassificacaoContabil> classificacaoList;
    private String[] columnNames = new String[] {
            "Número",
            "Classificacão"
    };

    public FormModel(Produto produto) throws FetchFailException {
        classificacaoList = new ClassificacaoDAO().findAll();
        unfilteredList = classificacaoList;
        tableModel = new CustomTableModelImpl<>(this, classificacaoList);
        this.produto = produto;
    }

    public void setList(List<ClassificacaoContabil> list){
        this.classificacaoList = list;
        tableModel.setList(list);
    }

    public void setFilter(String filter) {
        this.classificacaoList = unfilteredList;
        this.searchField = filter;
        if (!StringUtils.isBlank(searchField)) triggerSearch();
        if (categoriaContabil != null) triggerCategoria();
        tableModel.setList(classificacaoList);
    }

    public void setCategoriaContabil(CategoriaContabil categoriaContabil) {
        this.categoriaContabil = categoriaContabil;
        this.classificacaoList = unfilteredList;
        if (categoriaContabil != null) triggerCategoria();
        if (!StringUtils.isBlank(searchField)) triggerSearch();
        tableModel.setList(classificacaoList);
    }

    private void triggerSearch() {
        this.classificacaoList = classificacaoList.stream()
                .filter(c -> c.getNomeClassificacao().toUpperCase().contains(searchField.toUpperCase()))
                .collect(Collectors.toList());
    }

    private void triggerCategoria() {
        this.classificacaoList = classificacaoList.stream()
            .filter(c -> c.getCategoriaId() == categoriaContabil.getId())
            .collect(Collectors.toList());
    }

    public void setClassificacao(ClassificacaoContabil classificacao) { produto.setClassificacao(classificacao); }

    @Override
    public CustomTableModelImpl<ClassificacaoContabil> getBaseModel() { return tableModel; }

    @Override
    public int getRowCount() { return classificacaoList.size(); }

    @Override
    public String getColumnName(int column) { return columnNames[column]; }

    @Override
    public int getColumnCount() { return columnNames.length; }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ClassificacaoContabil classificacao = classificacaoList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> classificacao.getNumeroIdentificacao();
            case 1 -> classificacao.getNomeClassificacao();
            default -> throw new ArrayIndexOutOfBoundsException();
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> Long.class;
            case 1 -> String.class;
            default -> throw new ArrayIndexOutOfBoundsException();
        };
    }

    @Override
    public ClassificacaoContabil getObject(int rowIndex) { return  classificacaoList.get(rowIndex); }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ClassificacaoContabil classificacao = classificacaoList.get(rowIndex);
        switch (columnIndex) {
            case 0 -> classificacao.setNumeroIdentificacao((long) aValue);
            case 1 -> classificacao.setNomeClassificacao((String) aValue);
        };
    }

    public void addRow(ClassificacaoContabil classificacaoContabil) { tableModel.addRow(classificacaoContabil); }
    public void removeRows(int[] rows) { tableModel.removeRows(rows); }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

}
