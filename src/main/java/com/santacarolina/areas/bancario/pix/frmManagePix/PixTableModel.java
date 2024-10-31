package com.santacarolina.areas.bancario.pix.frmManagePix;

import java.util.List;

import com.santacarolina.dao.PixDAO;
import com.santacarolina.dto.PixDTO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.ui.CustomTableModelImpl;

public class PixTableModel implements CustomTableModel<PixDTO> {

    private FilterModel filterModel;
    private CustomTableModelImpl<PixDTO> baseModel;
    private List<PixDTO> list;

    private String[] columnNames = {
            "Nome do Contato",
            "Tipo Pix",
            "Chave Pix",
            "Banco",
            "Agência",
            "Número da Conta",
    };
   
    public PixTableModel() throws FetchFailException {
        this.list = new PixDAO().findAll(); 
        baseModel = new CustomTableModelImpl<>(this, list);
        filterModel = new FilterModel(this);
    }

    @Override
    public CustomTableModelImpl<PixDTO> getBaseModel() { return baseModel; }

    @Override
    public int getRowCount() { return list.size(); }

    @Override
    public String getColumnName(int column) { return columnNames[column]; }

    @Override
    public int getColumnCount() { return columnNames.length; }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }

    public void removeRow(int row) { baseModel.removeRow(row); }
    public void removeRows(int[] rows) { baseModel.removeRows(rows); }

    public List<PixDTO> getList() { return list; }
    public FilterModel getFilterModel() { return filterModel; }

    public void requeryTable() throws FetchFailException {
        list = new PixDAO().findAll();
        filterModel.setFilters();
    }

    @Override
    public PixDTO getObject(int rowIndex) { return baseModel.getObject(rowIndex); }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PixDTO c = getObject(rowIndex);
        return switch (columnIndex) {
            case 0 -> c.getNomeContato();
            case 1 -> c.getTipoPix().toString();
            case 2 -> c.printChave();
            case 3 -> c.getNomeBanco();
            case 4 -> c.getAgencia();
            case 5 -> c.getNumeroConta();
            default -> throw new IllegalStateException("Unexpected column value: " + columnIndex);
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) { return String.class; }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) { }

}
