package com.santacarolina.areas.contato.frmManageContato;

import java.util.List;

import com.santacarolina.dao.ContatoDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.model.Contato;
import com.santacarolina.ui.CustomTableModelImpl;
import com.santacarolina.util.DocConversor;

public class ContatoTableModel implements CustomTableModel<Contato> {

    private final CustomTableModelImpl<Contato> baseModel;
    private List<Contato> contatoList;
    private FilterModel filterModel;

    private String[] columnNames = {
        "Nome do Contato",
        "CPF",
        "CNPJ",
        "IE"
    };

    public ContatoTableModel(List<Contato> contatoList) {
        this.contatoList = contatoList; 
        baseModel = new CustomTableModelImpl<>(this, contatoList);
        filterModel = new FilterModel(this);
    }

    @Override
    public CustomTableModelImpl<Contato> getBaseModel() { return baseModel; }

    @Override
    public int getRowCount() { return contatoList.size(); }

    @Override
    public String getColumnName(final int column) { return columnNames[column]; }

    @Override
    public int getColumnCount() { return columnNames.length; }

    @Override
    public boolean isCellEditable(final int rowIndex, final int columnIndex) { return false; }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        Contato contato = getObject(rowIndex);
        return switch (columnIndex) {
            case 0 -> contato.getNome();
            case 1 -> DocConversor.docFormat(contato.getCpf(), DocConversor.CPF_FORMAT);
            case 2 -> DocConversor.docFormat(contato.getCnpj(), DocConversor.CNPJ_FORMAT);
            case 3 -> DocConversor.docFormat(contato.getIe(), DocConversor.IE_FORMAT);
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) { return String.class; }

    @Override
    public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) { }

    @Override
    public Contato getObject(final int rowIndex) { return baseModel.getObject(rowIndex); }

    public void removeRow(final int row) { baseModel.removeRow(row); }
    public void removeRows(final int[] rows) { baseModel.removeRows(rows); }
    
    public FilterModel getFilterModel() { return filterModel; }
    public List<Contato> getList() { return contatoList; }

    public void requeryTable() throws FetchFailException {
        contatoList = new ContatoDAO().findAll();
        baseModel.setList(contatoList);
    }

}
