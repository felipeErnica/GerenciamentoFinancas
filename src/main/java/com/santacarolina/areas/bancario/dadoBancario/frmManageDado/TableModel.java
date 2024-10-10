package com.santacarolina.areas.bancario.dadoBancario.frmManageDado;

import com.santacarolina.dao.DadoDAO;
import com.santacarolina.dto.DadoDTO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.ui.CustomTableModelImpl;

import java.util.List;

public class TableModel implements CustomTableModel<DadoDTO> {

    private static final DadoDAO dadoDAO = new DadoDAO();

    private CustomTableModelImpl<DadoDTO> baseModel;
    private List<DadoDTO> list;

    private String[] columnNames = {
            "Nome do Contato",
            "Banco",
            "Agência",
            "Número da Conta",
            "Tipo Pix",
            "Chave Pix"
    };

    public TableModel() throws FetchFailException {
        this.baseModel = new CustomTableModelImpl<>(this, dadoDAO.findAll() );
        list = baseModel.getList();
    }

    @Override
    public CustomTableModelImpl<DadoDTO> getBaseModel() { return baseModel; }

    @Override
    public int getRowCount() { return list.size(); }

    @Override
    public int getColumnCount() { return columnNames.length; }

    @Override
    public String getColumnName(int columnIndex) { return columnNames[columnIndex]; }

    @Override
    public Class<?> getColumnClass(int columnIndex) { return String.class; }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DadoDTO d = list.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> d.getNomeContato();
            case 1 -> d.getNomeBanco();
            case 2 -> d.getAgencia();
            case 3 -> d.getNumeroConta();
            case 4 -> d.getTipoPix();
            case 5 -> d.printChave();
            default -> throw new IllegalStateException("Unexpected column value: " + columnIndex);
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}

    @Override
    public DadoDTO getObject(int rowIndex) { return baseModel.getObject(rowIndex); }

    public void addNewRow() { baseModel.addRow(new DadoDTO());}
    public void addRow(DadoDTO dto) { baseModel.addRow(dto); }
    public void removeRow(int row) { baseModel.removeRow(row); }
    public void removeRows(int[] rows) { baseModel.removeRows(rows); }

    public void requeryTable() throws FetchFailException {
        baseModel.setList(dadoDAO.findAll());
        list = baseModel.getList();
    }
}
