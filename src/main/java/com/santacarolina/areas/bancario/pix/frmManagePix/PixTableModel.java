package com.santacarolina.areas.bancario.pix.frmManagePix;

import com.santacarolina.dao.PixDao;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.beans.ChavePix;
import com.santacarolina.model.tablemodels.AbstractCustomModel;

import java.util.List;

public class PixTableModel extends AbstractCustomModel<ChavePix> {

    private List<ChavePix> pixList;

    public PixTableModel(List<ChavePix> pixList) { this.pixList = pixList; }

    @Override
    public int getRowCount() { return pixList.size(); }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Nome do Contato";
            case 1 -> "Tipo Pix";
            case 2 -> "Chave Pix";
            case 3 -> "Banco";
            case 4 -> "Agência";
            case 5 -> "Número da Conta";
            default -> throw new IllegalStateException("Unexpected value: " + column);
        };
    }

    @Override
    public int getColumnCount() { return 6; }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }

    @Override
    public void removeRow(int row) {
        pixList.remove(row);
        fireTableRowsDeleted(row, row);
    }

    @Override
    public void removeRows(int[] rows) {
        if (rows.length == 0) return;
        for (int i = rows.length - 1; i >= 0; i--) {
            int row = rows[i];
            removeRow(row);
        }
    }

    @Override
    public void requeryTable() throws FetchFailException {
        pixList = new PixDao().findAll();
        fireTableDataChanged();
    }

    @Override
    public ChavePix getObject(int rowIndex) { return pixList.get(rowIndex); }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ChavePix c = pixList.get(rowIndex);
        if (c.getDadoBancario() != null) {
            return switch (columnIndex) {
                case 0 -> c.getContato() != null ? c.getContato().getNome() : null;
                case 1 -> c.getTipoPix().toString();
                case 2 -> c.toString();
                case 3 -> c.getDadoBancario().getBanco() != null ? c.getDadoBancario().getBanco().getNomeBanco() : null;
                case 4 -> c.getDadoBancario().getAgencia();
                case 5 -> c.getDadoBancario().getNumeroConta();
                default -> throw new IllegalStateException("Unexpected column value: " + columnIndex);
            };
        } else {
            return switch (columnIndex) {
                case 0 -> c.getContato() != null ? c.getContato().getNome() : null;
                case 1 -> c.getTipoPix().toString();
                case 2 -> c.toString();
                case 3, 4, 5 -> "";
                default -> throw new IllegalStateException("Unexpected column value: " + columnIndex);
            };
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) { return String.class; }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) { }
    @Override
    public void addRow(ChavePix chavePix) { }
    @Override
    public void addNewRow() { }

}
