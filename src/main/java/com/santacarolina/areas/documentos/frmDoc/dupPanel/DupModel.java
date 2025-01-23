package com.santacarolina.areas.documentos.frmDoc.dupPanel;

import java.beans.PropertyChangeListener;

import com.santacarolina.dto.DuplicataDTO;
import com.santacarolina.enums.TipoPagamento;
import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.DocumentoFiscal;
import com.santacarolina.util.PropertyFirer;
import com.santacarolina.util.StringConversor;

public class DupModel implements ViewUpdater {

    public static final String DADO_BUTTON = "dadoButton";
    public static final String TIPO_PAGTO = "tipoPagamento";
    public static final String TOTAL = "total";

    private TipoPagamento tipoPagamento;
    private DuplicataTableModel tableModel;
    private String valorTotal;
    private DocumentoFiscal documentoFiscal;
    private PropertyFirer pf;

    public DupModel(DocumentoFiscal doc) {
        if (!doc.getDuplicataList().isEmpty()) tipoPagamento = doc.getDuplicataList().get(0).getTipoPagamento();
        this.tableModel = new DuplicataTableModel(doc, this);
        pf = new PropertyFirer(this);
    }

    public DuplicataTableModel getTableModel() { return tableModel; }
    public TipoPagamento getTipoPagamento() { return tipoPagamento; }
    public DuplicataDTO getObject(int rowIndex) { return tableModel.getObject(rowIndex); }
    public DocumentoFiscal getDocumentoFiscal() { return documentoFiscal; }

    public void addRow(DuplicataDTO d) {
        d.setTipoPagamento(tipoPagamento);
        tableModel.addRow(d);
    }

    public void removeRows(int[] rows) { tableModel.removeRows(rows); }
    public void addNewRow() { tableModel.addNewRow(); }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
        tableModel.setTipoPagamento(tipoPagamento);
        pf.firePropertyChange(DADO_BUTTON, tipoPagamento == TipoPagamento.PIX || tipoPagamento == TipoPagamento.TED);
    }

    public void calculateValorTotal() {
        double valorSoma = tableModel.getDuplicataList().stream()
            .mapToDouble(d -> d.getValor())
            .sum();
        valorTotal = StringConversor.getCurrency(valorSoma);
        pf.firePropertyChange(TOTAL, valorTotal);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pf.addPropertyChangeListener(listener);
        pf.firePropertyChange(DADO_BUTTON, tipoPagamento == TipoPagamento.PIX || tipoPagamento == TipoPagamento.TED);
    }

    @Override
    public void fireInitialChanges() { 
        pf.firePropertyChange(TIPO_PAGTO, tipoPagamento); 
        calculateValorTotal();
    }

    public void setValueAt(Object aValue, int row, int column) { tableModel.setValueAt(aValue, row, column); }
    public Object getValueAt(int row, int column) { return tableModel.getValueAt(row, column); }

}
