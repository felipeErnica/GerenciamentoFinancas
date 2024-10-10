package com.santacarolina.areas.contato.frmManageContato;

import com.santacarolina.dao.ContatoDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.Contato;
import com.santacarolina.util.AbstractCustomModel;
import com.santacarolina.util.DocConversor;

import java.util.List;

public class ContatoTableModel extends AbstractCustomModel<Contato> {

    private List<Contato> contatoList;

    public ContatoTableModel(List<Contato> contatoList) { this.contatoList = contatoList; }

    @Override
    public int getRowCount() { return contatoList.size(); }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Nome do Contato";
            case 1 -> "CPF";
            case 2 -> "CNPJ";
            case 3 -> "IE";
            default -> throw new IllegalStateException("Unexpected value: " + column);
        };
    }

    @Override
    public int getColumnCount() { return 4; }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Contato c = getObject(rowIndex);
        return switch (columnIndex) {
            case 0 -> c.getNome();
            case 1 -> DocConversor.docFormat(c.getCpf(), DocConversor.CPF_FORMAT);
            case 2 -> DocConversor.docFormat(c.getCnpj(), DocConversor.CNPJ_FORMAT);
            case 3 -> DocConversor.docFormat(c.getIe(), DocConversor.IE_FORMAT);
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) { return String.class; }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) { }

    @Override
    public Contato getObject(int rowIndex) { return contatoList.get(rowIndex); }

    @Override
    public void addRow(Contato contato) { }

    @Override
    public void addNewRow() { }

    @Override
    public void removeRow(int row) {
        contatoList.remove(row);
        fireTableRowsDeleted(row, row);
    }

    @Override
    public void removeRows(int[] rows) { for (int i = rows.length - 1; i >= 0; i--) removeRow(rows[i]); }

    @Override
    public void requeryTable() throws FetchFailException { new ContatoDAO().findAll(); }

}
