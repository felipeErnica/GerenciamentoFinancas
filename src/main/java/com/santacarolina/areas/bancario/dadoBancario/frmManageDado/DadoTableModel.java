package com.santacarolina.areas.bancario.dadoBancario.frmManageDado;

import com.santacarolina.dao.DadoDao;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.beans.DadoBancario;
import com.santacarolina.model.tablemodels.AbstractCustomModel;

import java.util.List;

public class DadoTableModel extends AbstractCustomModel<DadoBancario> {

    private List<DadoBancario> dadoList;

    public DadoTableModel(List<DadoBancario> dadoList) {
        this.dadoList = dadoList;
    }

    @Override
    public int getRowCount() { return dadoList.size(); }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Nome do Contato";
            case 1 -> "Banco";
            case 2 -> "Agência";
            case 3 -> "Número da Conta";
            case 4 -> "Tipo Pix";
            case 5 -> "Chave Pix";
            default -> throw new IllegalStateException("Unexpected value: " + column);
        };
    }

    @Override
    public int getColumnCount() { return 6; }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }

    @Override
    public void removeRow(int row) {
        dadoList.remove(row);
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
        dadoList = new DadoDao().findAll();
        fireTableDataChanged();
    }

    @Override
    public DadoBancario getObject(int rowIndex) { return dadoList.get(rowIndex); }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DadoBancario d = dadoList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> d.getContato() != null ? d.getContato().getNome() : null;
            case 1 -> d.getBanco() != null ? d.getBanco().getNomeBanco() : null;
            case 2 -> d.getAgencia();
            case 3 -> d.getNumeroConta();
            case 4 -> d.getChavePix() != null ? d.getChavePix().getTipoPix().toString() : null;
            case 5 -> d.getChavePix() != null ? d.getChavePix().toString() : null;
            default -> throw new IllegalStateException("Unexpected column value: " + columnIndex);
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) { return String.class; }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) { }
    @Override
    public void addRow(DadoBancario dadoBancario) { }
    @Override
    public void addNewRow() { }

}
