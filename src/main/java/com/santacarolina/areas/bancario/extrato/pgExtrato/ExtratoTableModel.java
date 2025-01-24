package com.santacarolina.areas.bancario.extrato.pgExtrato;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;

import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.model.Extrato;
import com.santacarolina.ui.CustomTableModelImpl;

public class ExtratoTableModel implements CustomTableModel<Extrato> {

    public static final String CONTA = "conta";
    public static final String SALDO_TEXT = "saldo";

    private List<Extrato> extratoList;
    private CustomTableModelImpl<Extrato> model;
    private FilterModel filterModel;

    private String[] columnNames = {
        "",
        "Data da Transação",
        "Conta Bancária",
        "Categoria Bancária",
        "Descrição",
        "Valor",
    };

    public ExtratoTableModel() {
        this.extratoList = new ArrayList<>();
        this.model = new CustomTableModelImpl<>(this, extratoList); 
        filterModel = new FilterModel(this);
    }

    @Override
    public CustomTableModelImpl<Extrato> getBaseModel() { return model; }

    public int getRowCount() { return extratoList.size(); }
    public int getColumnCount() { return columnNames.length; }
    public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }
    public Extrato getObject(int rowIndex) { return model.getObject(rowIndex); }
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) { }
    public void addTableModelListener(TableModelListener l) { model.addTableModelListener(l); }
    public void removeTableModelListener(TableModelListener l) { model.removeTableModelListener(l); }
    public FilterModel getFilterModel() { return filterModel; }
    public List<Extrato> getList() { return extratoList; }

    public void setList(List<Extrato> list) {
        extratoList = list;
        model.setList(list);
        filterModel.setData();
    }

    public String getColumnName(int columnIndex) { return columnNames[columnIndex]; }

    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> Integer.class;
            case 1 -> LocalDate.class;
            case 2, 3, 4 -> String.class;
            case 5 -> Double.class;
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Extrato e = getObject(rowIndex);
        return switch (columnIndex) {
            case 0 -> rowIndex + 1;
            case 1 -> e.getDataTransacao();
            case 2 -> e.getConta();
            case 3 -> e.getCategoriaExtrato();
            case 4 -> e.getDescricao();
            case 5 -> e.getValor();
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

}
