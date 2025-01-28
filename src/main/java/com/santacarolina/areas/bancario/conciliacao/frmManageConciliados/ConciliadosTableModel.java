package com.santacarolina.areas.bancario.conciliacao.frmManageConciliados;

import java.time.LocalDate;
import java.util.List;

import com.santacarolina.dao.ConciliacaoDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.model.Conciliacao;
import com.santacarolina.ui.CustomTableModelImpl;

/**
 * ConciliadosTableModel
 */
public class ConciliadosTableModel implements CustomTableModel<Conciliacao> {

    private CustomTableModelImpl<Conciliacao> baseModel;
    private List<Conciliacao> list;
    private FilterModel filterModel;

    private String[] columnNames = {
        "Data da Transação",
        "Emissor",
        "Conta Bancária",
        "Categoria Bancária",
        "Descrição",
        "Valor do Extrato",
        "Valor da Duplicata"
    };

    public ConciliadosTableModel() throws FetchFailException {
        list = new ConciliacaoDAO().findAll();
        baseModel = new CustomTableModelImpl<>(this, list);
        filterModel = new FilterModel(this);
    }

    public FilterModel getFilterModel() { return filterModel; }

    @Override
    public CustomTableModelImpl<Conciliacao> getBaseModel() { return baseModel; }

    @Override
    public int getRowCount() { return list.size(); }

    @Override
    public int getColumnCount() { return columnNames.length; }

    @Override
    public String getColumnName(int columnIndex) { return columnNames[columnIndex]; }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> LocalDate.class; 
            case 1 -> String.class;
            case 2 -> String.class;
            case 3 -> String.class;
            case 4 -> String.class;
            case 5 -> Double.class; 
            case 6 -> Double.class;
            default -> throw new IllegalArgumentException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Conciliacao conc = filterModel.getList().get(rowIndex);
        return switch (columnIndex) {
            case 0 -> conc.getExtrato().getDataTransacao();
            case 1 -> {
                if (conc.getDuplicata() == null) yield null;
                if (conc.getDuplicata().getDocumento() == null) yield null;
                if (conc.getDuplicata().getDocumento().getPasta() == null) yield null;
                yield conc.getDuplicata().getDocumento().getPasta().getNome();
            }
            case 2 -> {
                if (conc.getDuplicata() == null) yield null;
                if (conc.getDuplicata().getDocumento() == null) yield null;
                if (conc.getDuplicata().getDocumento().getEmissor() == null) yield null;
                yield conc.getDuplicata().getDocumento().getEmissor().getNome();
            }
            case 3 -> conc.getExtrato().getConta().getAbreviacaoConta();
            case 4 -> conc.getExtrato().getCategoriaExtrato();
            case 5 -> conc.getExtrato().getDescricao();
            case 6 -> conc.getExtrato().getValor();
            case 7 -> conc.getDuplicata() != null ? conc.getDuplicata().getValor() : 0;
            default -> throw new IllegalArgumentException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}

    @Override
    public Conciliacao getObject(int rowIndex) { return list.get(rowIndex); }

    public List<Conciliacao> getList() { return list; }

    public void requeryTable() throws FetchFailException {
        list = new ConciliacaoDAO().findAll();
        baseModel.setList(list);
    }

}
