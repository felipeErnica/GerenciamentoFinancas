package com.santacarolina.areas.documentos.frmImportNFe;

import com.santacarolina.model.PastaContabil;

import java.io.File;
import java.util.List;

public class FormModel {

    private PastaContabil pastaContabil;
    private List<File> nfeList;

    public PastaContabil getPastaContabil() { return pastaContabil; }
    public List<File> getNfeList() { return nfeList; }

    public void setPastaContabil(PastaContabil pastaContabil) { this.pastaContabil = pastaContabil; }
    public void setNfeList(List<File> nfeList) { this.nfeList = nfeList; }

}
