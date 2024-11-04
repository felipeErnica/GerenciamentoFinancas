package com.santacarolina.areas.bancario.extrato.pgExtrato;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;

import com.santacarolina.dto.ExtratoDTO;
import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.ui.CustomTableModelImpl;

public class ExtratoTableModel implements CustomTableModel<ExtratoDTO> {

    public static final String CONTA = "conta";
    public static final String SALDO_TEXT = "saldo";

    private List<ExtratoDTO> extratoList;
    private CustomTableModelImpl<ExtratoDTO> model;
    private FilterModel filterModel;

    public ExtratoTableModel() {
        this.extratoList = new ArrayList<>();
        this.model = new CustomTableModelImpl<>(this, extratoList); 
        filterModel = new FilterModel(this);
    }

    @Override
    public CustomTableModelImpl<ExtratoDTO> getBaseModel() { return model; }

    public int getRowCount() { return extratoList.size(); }
    public int getColumnCount() { return 6; }
    public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }
    public ExtratoDTO getObject(int rowIndex) { return model.getObject(rowIndex); }
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) { }
    public void addTableModelListener(TableModelListener l) { model.addTableModelListener(l); }
    public void removeTableModelListener(TableModelListener l) { model.removeTableModelListener(l); }
    public FilterModel getFilterModel() { return filterModel; }
    public List<ExtratoDTO> getList() { return extratoList; }

    public void setList(List<ExtratoDTO> list) {
        extratoList = list;
        model.setList(list);
        filterModel.setData();
    }

    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> "";
            case 1 -> "Data da Transação";
            case 2 -> "Conta Bancária";
            case 3 -> "Categoria Bancária";
            case 4 -> "Descrição";
            case 5 -> "Valor";
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

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
        ExtratoDTO e = getObject(rowIndex);
        return switch (columnIndex) {
            case 0 -> rowIndex + 1;
            case 1 -> e.getDataTransacao();
            case 2 -> e.getContaBancaria();
            case 3 -> e.getCategoriaExtrato();
            case 4 -> e.getDescricao();
            case 5 -> e.getValor();
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

}
