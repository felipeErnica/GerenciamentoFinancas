package com.santacarolina.areas.documentos.frmSelectNFe;

import com.santacarolina.model.DocumentoFiscal;

import java.util.List;

public class FormModel {

    private List<DocumentoFiscal> nfeList;
    private DocumentoFiscal nfe;

    public FormModel(List<DocumentoFiscal> nfeList) {
        this.nfeList = nfeList;
    }

    public List<DocumentoFiscal> getNfeList() { return nfeList; }
    public DocumentoFiscal getNfe() { return nfe; }

    public void setNfe(DocumentoFiscal nfe) { this.nfe = nfe; }
    public void deleteNfe(DocumentoFiscal nfe) { nfeList.remove(nfe); }

}
