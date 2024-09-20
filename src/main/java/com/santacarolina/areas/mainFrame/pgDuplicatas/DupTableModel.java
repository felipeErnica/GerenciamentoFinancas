package com.santacarolina.areas.mainFrame.pgDuplicatas;

import com.santacarolina.dto.DuplicataDTO;
import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.ui.CustomTableModelImpl;

import java.time.LocalDate;
import java.util.List;

public class DupTableModel implements CustomTableModel<DuplicataDTO> {

    public final static String TABLE = "table";

    private CustomTableModelImpl<DuplicataDTO> model;
    private List<DuplicataDTO> duplicataList;

    public DupTableModel(List<DuplicataDTO> duplicataList) {
        this.duplicataList = duplicataList;
        this.model = new CustomTableModelImpl<>(this, duplicataList);
    }

    public CustomTableModelImpl<DuplicataDTO> getBaseModel() { return model; }
    @Override
    public int getRowCount() { return model.getRowCount(); }
    @Override
    public int getColumnCount() { return 6; }

    public void setList(List<DuplicataDTO> list) {
        duplicataList = list;
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
        DuplicataDTO d = duplicataList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> d.numDup();
            case 1 -> d.dataVencimento();
            case 2 -> d.tipoPagamento() != null ? d.tipoPagamento().toString() : "";
            case 3 -> d.conta();
            case 4 -> d.nomeContato();
            case 5 -> d.valor();
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

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

}
