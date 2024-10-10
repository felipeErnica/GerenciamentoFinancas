package com.santacarolina.areas.documentos.frmDoc.dupPanel;

import com.santacarolina.enums.TipoPagamento;
import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.DocumentoFiscal;
import com.santacarolina.model.Duplicata;
import com.santacarolina.util.PropertyFirer;

import java.beans.PropertyChangeListener;

public class DupModel implements ViewUpdater {

    public static final String DADO_BUTTON = "dadoButton";
    public static final String TIPO_PAGTO = "tipoPagamento";

    private TipoPagamento tipoPagamento;
    private DuplicataTableModel tableModel;
    private PropertyFirer pf;

    public DupModel(DocumentoFiscal d) {
        if (!d.getDuplicatas().isEmpty()) tipoPagamento = d.getDuplicatas().get(0).getTipoPagamento();
        this.tableModel = new DuplicataTableModel(d, tipoPagamento);
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

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pf.addPropertyChangeListener(listener);
        pf.firePropertyChange(DADO_BUTTON, tipoPagamento == TipoPagamento.PIX || tipoPagamento == TipoPagamento.TED);
    }

    @Override
    public void fireInitialChanges() { pf.firePropertyChange(TIPO_PAGTO, tipoPagamento); }
    public void setValueAt(Object aValue, int row, int column) { tableModel.setValueAt(aValue, row, column); }
    public Object getValueAt(int row, int column) { return tableModel.getValueAt(row, column); }

}
