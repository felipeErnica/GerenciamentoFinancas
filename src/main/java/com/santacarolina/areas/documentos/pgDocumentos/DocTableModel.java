package com.santacarolina.areas.documentos.pgDocumentos;

import com.santacarolina.dao.DocumentoDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.model.DocumentoFiscal;
import com.santacarolina.ui.CustomTableModelImpl;

import java.time.LocalDate;
import java.util.List;

public class DocTableModel implements CustomTableModel<DocumentoFiscal> {

    private DocumentoDAO documentoDAO = new DocumentoDAO();

    private CustomTableModelImpl<DocumentoFiscal> baseModel;
    private List<DocumentoFiscal> list;

    private FilterModel filterModel;

    private final String[] columnNames = {
            "",
            "Data de Emissão",
            "Pasta Contábil",
            "Tipo de Documento",
            "Num. Documento",
            "Emissor",
            "Valor"
    };

    public DocTableModel(List<DocumentoFiscal> list) {
        baseModel = new CustomTableModelImpl<>(this, list);
        this.list = list;
        filterModel = new FilterModel(this);
    }

    @Override
    public CustomTableModelImpl<DocumentoFiscal> getBaseModel() { return baseModel; }

    @Override
    public int getRowCount() { return baseModel.getRowCount(); }

    @Override
    public int getColumnCount() { return columnNames.length; }

    @Override
    public String getColumnName(int columnIndex) { return columnNames[columnIndex]; }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> Integer.class;
            case 1 -> LocalDate.class;
            case 2, 3, 5 -> String.class;
            case 4 -> Long.class;
            case 6 -> Double.class;
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DocumentoFiscal d = getObject(rowIndex);
        return switch (columnIndex) {
            case 0 -> rowIndex + 1;
            case 1 -> d.getDataEmissao();
            case 2 -> d.getPasta() != null ? d.getPasta().getNome() : null;
            case 3 -> d.getTipoDoc().getValue();
            case 4 -> d.getNumDoc();
            case 5 -> d.getEmissor() != null ? d.getEmissor().getNome() : null;
            case 6 -> d.getValor();
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) { }

    @Override
    public DocumentoFiscal getObject(int rowIndex) { return baseModel.getObject(rowIndex); }

    public DocumentoFiscal getDocumento(int rowIndex) throws FetchFailException {
        long id = getObject(rowIndex).getId();
        return documentoDAO.findById(id).orElse(null);
    }

    public List<DocumentoFiscal> getList() { return list; }
    public FilterModel getFilterModel() { return filterModel; }

}
