package com.santacarolina.areas.documentos.frmDoc.prodPanel;

import java.beans.PropertyChangeListener;

import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.DocumentoFiscal;
import com.santacarolina.util.PropertyFirer;
import com.santacarolina.util.StringConversor;

/**
 * ProdModel
 */
public class ProdModel implements ViewUpdater {

    public static final String TOTAL = "total";

    private ProdutoTableModel tableModel;
    private String valorTotal;
    private DocumentoFiscal documentoFiscal;
    private PropertyFirer pf;
    
    public ProdModel(DocumentoFiscal documentoFiscal) {
        this.documentoFiscal = documentoFiscal;
        tableModel = new ProdutoTableModel(documentoFiscal, this);
        pf = new PropertyFirer(this);
    }

    public void calculateValorTotal() { 
        double valorTotalSoma = tableModel.getProdutoList().stream()
            .mapToDouble(p -> p.getValorTotal())
            .sum();
        valorTotal = StringConversor.getCurrency(valorTotalSoma);
        pf.firePropertyChange(TOTAL, valorTotal);
    }

    public DocumentoFiscal getDocumentoFiscal() { return documentoFiscal; }
    public ProdutoTableModel getTableModel() { return tableModel; }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { pf.addPropertyChangeListener(listener); }

    @Override
    public void fireInitialChanges() { calculateValorTotal(); }

}
