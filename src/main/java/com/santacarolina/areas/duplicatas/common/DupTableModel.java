package com.santacarolina.areas.duplicatas.common;

import java.time.LocalDate;
import java.util.List;

import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.model.Duplicata;
import com.santacarolina.ui.CustomTableModelImpl;

public class DupTableModel implements CustomTableModel<Duplicata> {

    public final static String TABLE = "table";

    private CustomTableModelImpl<Duplicata> model;
    private List<Duplicata> list;
    private FilterModel filterModel;

    public DupTableModel(List<Duplicata> duplicataList) {
        this.list = duplicataList;
        this.model = new CustomTableModelImpl<>(this, duplicataList);
        filterModel = new FilterModel(this);
    }

    public CustomTableModelImpl<Duplicata> getBaseModel() { return model; }

    @Override
    public int getRowCount() { return model.getRowCount(); }

    @Override
    public int getColumnCount() { return 6; }

    public void setList(List<Duplicata> list) {
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
        Duplicata d = getObject(rowIndex);
        return switch (columnIndex) {
            case 0 -> d.getNumDup();
            case 1 -> d.getDataVencimento();
            case 2 -> d.getTipoPagamento() != null ? d.getTipoPagamento().toString() : "";
            case 3 -> {
                if (d.getDocumento() == null) yield null;
                if (d.getDocumento().getPasta() == null) yield null;
                if (d.getDocumento().getPasta().getConta() == null) yield null;
                yield d.getDocumento().getPasta().getConta().getAbreviacaoConta();
            }
            case 4 -> {
                if (d.getDocumento() == null) yield null;
                if (d.getDocumento().getEmissor() == null) yield null;
                yield d.getDocumento().getEmissor().getNome();
            }
            case 5 -> d.getValor();
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) { }

    @Override
    public Duplicata getObject(int rowIndex) { return model.getObject(rowIndex); }

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

    public List<Duplicata> getList() { return list; }
    public FilterModel getFilterModel() { return filterModel; }

}
