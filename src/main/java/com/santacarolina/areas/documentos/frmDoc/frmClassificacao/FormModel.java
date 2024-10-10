package com.santacarolina.areas.documentos.frmDoc.frmClassificacao;

import com.santacarolina.dao.ClassificacaoDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.model.Produto;
import com.santacarolina.ui.CustomTableModelImpl;
import com.santacarolina.util.AbstractCustomModel;
import com.santacarolina.model.ClassificacaoContabil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FormModel implements CustomTableModel<ClassificacaoContabil> {

    private Produto produto;
    private String filter;
    private ClassificacaoDAO classificacaoDAO;
    private CustomTableModelImpl<ClassificacaoContabil> tableModel;
    private List<ClassificacaoContabil> unfilteredList;
    private List<ClassificacaoContabil> classificacaoList;
    private String[] columnNames = new String[] {
            "Número",
            "Classificacão"
    };

    public FormModel(Produto produto) throws FetchFailException {
        classificacaoDAO = new ClassificacaoDAO();
        classificacaoList = classificacaoDAO.getAll();
        unfilteredList = classificacaoList;
        tableModel = new CustomTableModelImpl<>(this, classificacaoList);
        this.produto = produto;
    }

    public void setList(List<ClassificacaoContabil> list){
        this.classificacaoList = list;
        tableModel.setList(list);
    }

    public void setFilter(String filter) {
        this.filter = filter;
        this.classificacaoList = unfilteredList;
        this.classificacaoList = classificacaoList.stream()
                .filter(c -> c.getNomeClassificacao().toUpperCase().contains(filter.toUpperCase()))
                .collect(Collectors.toList());
        tableModel.setList(classificacaoList);
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

}
