package com.santacarolina.areas.documentos.frmDoc;

import com.santacarolina.areas.documentos.frmDoc.docPanel.DocModel;
import com.santacarolina.areas.documentos.frmDoc.dupPanel.DupModel;
import com.santacarolina.areas.documentos.frmDoc.prodPanel.ProdutoTableModel;
import com.santacarolina.model.DocumentoFiscal;

public class MainModel {

    private DocumentoFiscal documentoFiscal;
    private DupModel dupModel;
    private ProdutoTableModel produtoTableModel;
    private DocModel docModel;

    public MainModel(DocumentoFiscal d) {
        this.dupModel = new DupModel(d);
        this.produtoTableModel = new ProdutoTableModel(d);
        this.docModel = new DocModel(d);
        this.documentoFiscal = d;
    }

    public DocumentoFiscal getDocumentoFiscal() { return documentoFiscal; }
    public DupModel getDupModel() { return dupModel; }
    public ProdutoTableModel getProdutoTableModel() { return produtoTableModel; }
    public DocModel getDocModel() { return docModel; }

}
