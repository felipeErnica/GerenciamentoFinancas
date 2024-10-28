package com.santacarolina.areas.bancario.conciliacao.frmManageConciliados;

import java.time.LocalDate;
import java.util.List;

import com.santacarolina.dao.ConciliacaoDAO;
import com.santacarolina.dto.ConciliacaoDTO;
import com.santacarolina.enums.TipoMovimento;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.ui.CustomTableModelImpl;

/**
 * ConciliadosTableModel
 */
public class ConciliadosTableModel implements CustomTableModel<ConciliacaoDTO> {

    private CustomTableModelImpl<ConciliacaoDTO> baseModel;
    private List<ConciliacaoDTO> list;

    private String[] columnNames = {
        "Tipo de Movimento",
        "Data da Transação",
        "Conta Bancária",
        "Categoria Bancária",
        "Descrição",
        "Valor do Extrato",
        "Data de Vencimento",
        "Pasta Contábil",
        "Emissor",
        "Valor da Duplicata"
    };


    public ConciliadosTableModel() throws FetchFailException {
        list = new ConciliacaoDAO().findAll();
        baseModel = new CustomTableModelImpl<>(this, list);
    }

    @Override
    public CustomTableModelImpl<ConciliacaoDTO> getBaseModel() { return baseModel; }

    @Override
    public int getRowCount() { return list.size(); }

    @Override
    public int getColumnCount() { return columnNames.length; }

    @Override
    public String getColumnName(int columnIndex) { return columnNames[columnIndex]; }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> TipoMovimento.class; 
            case 1 -> LocalDate.class; 
            case 2 -> String.class;
            case 3 -> String.class;
            case 4 -> String.class;
            case 5 -> Double.class;
            case 6 -> LocalDate.class;
            case 7 -> String.class; 
            case 8 -> String.class;
            case 9 -> Double.class;
            default -> throw new IllegalArgumentException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ConciliacaoDTO conc = list.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> conc.getTipoMovimento();
            case 1 -> conc.getDataExtrato();
            case 2 -> conc.getContaBancaria();
            case 3 -> conc.getCategoriaExtrato();
            case 4 -> conc.getDescExtrato();
            case 5 -> conc.getValorExtrato();
            case 6 -> conc.getDataVencimento();
            case 7 -> conc.getNomePasta();
            case 8 -> conc.getNomeEmissor();
            case 9 -> conc.getValorDuplicata();
            default -> throw new IllegalArgumentException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}

    @Override
    public ConciliacaoDTO getObject(int rowIndex) { return list.get(rowIndex); }

    public void requeryTable() throws FetchFailException {
        list = new ConciliacaoDAO().findAll();
        baseModel.setList(list);
    }
    
}
