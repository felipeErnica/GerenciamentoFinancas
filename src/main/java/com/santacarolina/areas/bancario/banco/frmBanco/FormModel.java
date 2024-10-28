package com.santacarolina.areas.bancario.banco.frmBanco;

import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.Banco;
import com.santacarolina.util.PropertyFirer;

import java.beans.PropertyChangeListener;

public class FormModel implements ViewUpdater {

    public static final String NOME_BANCO = "banco";
    public static final String APELIDO  = "apelidoBanco";

    private Banco banco;
    private String apelidoBanco;
    private String nomeBanco;
    private PropertyFirer pf;

    public FormModel(Banco banco) {
        this.banco = banco;
        nomeBanco = banco.getNomeBanco();
        apelidoBanco = banco.getApelidoBanco();
        pf = new PropertyFirer(this);
    }

    public Banco getBanco() { return banco; }
    public String getApelidoBanco() { return apelidoBanco; }
    public String getNomeBanco() { return nomeBanco; }

    public void setApelidoBanco(String apelidoBanco) {
        this.apelidoBanco = apelidoBanco.toUpperCase();
        this.banco.setApelidoBanco(this.apelidoBanco);
        pf.firePropertyChange(APELIDO, this.apelidoBanco);
    }

    public void setNomeBanco(String nomeBanco) {
        this.nomeBanco = nomeBanco.toUpperCase();
        this.banco.setNomeBanco(this.nomeBanco);
        pf.firePropertyChange(NOME_BANCO, this.nomeBanco);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { pf.addPropertyChangeListener(listener); }

    @Override
    public void fireInitialChanges() {
        pf.firePropertyChange(NOME_BANCO, nomeBanco);
        pf.firePropertyChange(APELIDO, apelidoBanco);
    }
}
