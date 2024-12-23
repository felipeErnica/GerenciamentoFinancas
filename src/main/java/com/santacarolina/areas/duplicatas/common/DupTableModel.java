package com.santacarolina.areas.duplicatas.common;

import java.time.LocalDate;
import java.util.List;

import com.santacarolina.dto.DuplicataDTO;
import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.ui.CustomTableModelImpl;

public class DupTableModel implements CustomTableModel<DuplicataDTO> {

    public final static String TABLE = "table";

    private CustomTableModelImpl<DuplicataDTO> model;
    private List<DuplicataDTO> list;
    private FilterModel filterModel;

    public DupTableModel(List<DuplicataDTO> duplicataList) {
        this.list = duplicataList;
        this.model = new CustomTableModelImpl<>(this, duplicataList);
        filterModel = new FilterModel(this);
    }

    public CustomTableModelImpl<DuplicataDTO> getBaseModel() { return model; }
    @Override
    public int getRowCount() { return model.getRowCount(); }
    @Override
    public int getColumnCount() { return 6; }

    public void setList(List<DuplicataDTO> list) {
        this.list = list;
        model.setList(list);
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Nº da Parcela";
            case 1 -> "Data de Vencimento";
            case 2 -> "Forma de Pagamento";
            case 3 -> "Conta Bancária";
            case 4 -> "Fornecedor";
            case 5 -> "Valor";
            default -> throw new IllegalStateException("Unexpected value: " + column);
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DuplicataDTO d = getObject(rowIndex);
        return switch (columnIndex) {
            case 0 -> d.getNumDup();
            case 1 -> d.getDataVencimento();
            case 2 -> d.getTipoPagamento() != null ? d.getTipoPagamento().toString() : "";
            case 3 -> d.getConta();
            case 4 -> d.getNomeContato();
            case 5 -> d.getValor();
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) { }

    @Override
    public DuplicataDTO getObject(int rowIndex) { return model.getObject(rowIndex); }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> Integer.class;
            case 1 -> LocalDate.class;
            case 2,3,4 -> String.class;
            case 5 -> Double.class;
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

    public List<DuplicataDTO> getList() { return list; }
    public FilterModel getFilterModel() { return filterModel; }

}
