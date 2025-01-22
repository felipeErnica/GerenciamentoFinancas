package com.santacarolina.areas.documentos.frmDoc;

import com.santacarolina.areas.documentos.frmDoc.docPanel.DocModel;
import com.santacarolina.areas.documentos.frmDoc.dupPanel.DupModel;
import com.santacarolina.areas.documentos.frmDoc.prodPanel.ProdModel;
import com.santacarolina.model.DocumentoFiscal;

public class MainModel {

    private DocumentoFiscal documentoFiscal;
    private DupModel dupModel;
    private ProdModel produtoModel;
    private DocModel docModel;
    private boolean newDoc;

    public MainModel(DocumentoFiscal d, String statusDoc) {
        this.dupModel = new DupModel(d);
        this.produtoModel = new ProdModel(d);
        this.docModel = new DocModel(d);
        this.documentoFiscal = d;
        this.newDoc = statusDoc == DocForm.DOC_NEW;
    }

    public DocumentoFiscal getDocumentoFiscal() { return documentoFiscal; }
    public DupModel getDupModel() { return dupModel; }
    public ProdModel getProdutoModel() { return produtoModel; }
    public DocModel getDocModel() { return docModel; }
    public boolean isNewDoc() { return newDoc; }

}
