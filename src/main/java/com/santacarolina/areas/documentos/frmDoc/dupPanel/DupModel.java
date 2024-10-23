package com.santacarolina.areas.documentos.frmDoc.dupPanel;

import com.santacarolina.enums.TipoPagamento;
import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.DocumentoFiscal;
import com.santacarolina.model.Duplicata;
import com.santacarolina.util.PropertyFirer;
import com.santacarolina.util.StringConversor;

import java.beans.PropertyChangeListener;

public class DupModel implements ViewUpdater {

    public static final String DADO_BUTTON = "dadoButton";
    public static final String TIPO_PAGTO = "tipoPagamento";
    public static final String TOTAL = "total";

    private TipoPagamento tipoPagamento;
    private DuplicataTableModel tableModel;
    private String valorTotal;
    private PropertyFirer pf;

    public DupModel(DocumentoFiscal d) {
        if (!d.getDuplicatas().isEmpty()) tipoPagamento = d.getDuplicatas().get(0).getTipoPagamento();
        this.tableModel = new DuplicataTableModel(d, this);
        pf = new PropertyFirer(this);
    }

    public DuplicataTableModel getTableModel() { return tableModel; }
    public TipoPagamento getTipoPagamento() { return tipoPagamento; }
    public Duplicata getObject(int rowIndex) { return tableModel.getObject(rowIndex); }

    public void addRow(Duplicata d) {
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
