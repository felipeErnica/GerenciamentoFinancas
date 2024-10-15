package com.santacarolina.areas.pastaContabil.frmPastaContabil;

import java.beans.PropertyChangeListener;

import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.ContaBancaria;
import com.santacarolina.model.PastaContabil;
import com.santacarolina.util.PropertyFirer;

public class PastaContabilModel implements ViewUpdater {

    public static final String CONTA = "conta";
    public static final String NOME_PASTA = "nomePasta";
    public static final String CAMINHO = "caminho";

    private PastaContabil pastaContabil;
    private String nomePasta;
    private String folderPath;
    private ContaBancaria contaBancaria;
    private PropertyFirer pf;

    public PastaContabilModel(PastaContabil p) {
        this.pastaContabil = p;
        pf = new PropertyFirer(this);
        init();
    }

    private void init() {
        nomePasta = pastaContabil.getNome();
        folderPath = pastaContabil.getCaminhoPasta();
        System.out.println(pastaContabil.getContaId());
        contaBancaria = pastaContabil.getContaBancaria();
    }

    public PastaContabil getPastaContabil() { return pastaContabil; }
    public String getNomePasta() { return nomePasta; }
    public String getFolderPath() { return folderPath; }
    public ContaBancaria getContaBancaria() { return contaBancaria; }

    public void setNomePasta(String nomePasta) {
        this.nomePasta = nomePasta.toUpperCase();
        pastaContabil.setNome(this.nomePasta);
        pf.firePropertyChange(NOME_PASTA, this.nomePasta);
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
        pastaContabil.setCaminhoPasta(folderPath);
        pf.firePropertyChange(CAMINHO, this.folderPath);
    }

    public void setContaBancaria(ContaBancaria contaBancaria) {
        this.contaBancaria = contaBancaria;
        pastaContabil.setContaBancaria(contaBancaria);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { pf.addPropertyChangeListener(listener); }

    @Override
    public void fireInitialChanges() {
        pf.firePropertyChange(NOME_PASTA, this.nomePasta);
        pf.firePropertyChange(CAMINHO, this.folderPath);
        pf.firePropertyChange(CONTA, this.contaBancaria);
    }

}
