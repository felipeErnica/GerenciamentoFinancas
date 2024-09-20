package com.santacarolina.model.tablemodels;

import com.santacarolina.model.beans.ClassificacaoContabil;
import java.util.ArrayList;
import java.util.List;

public class ClassificacaoModel extends AbstractCustomModel<ClassificacaoContabil> {

    private List<ClassificacaoContabil> classificacaoList = new ArrayList<>();
    private String[] columnNames = new String[] {
            "Número da Classificação",
            "Classificacão"
    };

    @Override
    public int getRowCount() {
        return classificacaoList.size();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

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

    @Override
    public void addRow(ClassificacaoContabil classificacaoContabil) {
        classificacaoList.add(classificacaoContabil);
    }

    @Override
    public void addNewRow() {
        addRow(new ClassificacaoContabil());
    }

    @Override
    public void removeRow(int row) {
        classificacaoList.remove(row);
    }

    @Override
    public void removeRows(int[] rows) {

    }

    @Override
    public void requeryTable() { }

    public void setList(List<ClassificacaoContabil> list){
        this.classificacaoList = list;
        fireTableDataChanged();
    }

    public List<ClassificacaoContabil> getClassificacaoList() { return classificacaoList; }

}
