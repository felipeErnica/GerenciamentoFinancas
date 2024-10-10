package com.santacarolina.areas.bancario.dadoBancario.common;

import com.santacarolina.enums.TipoPix;
import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.Banco;
import com.santacarolina.model.ChavePix;
import com.santacarolina.model.Contato;
import com.santacarolina.model.DadoBancario;
import com.santacarolina.util.PropertyFirer;

import javax.swing.*;
import java.beans.PropertyChangeListener;

public class DadoBancarioFormModel implements ViewUpdater {

    public final static String CONTATO_COMBOBOX = "contato";
    public final static String BANCO_COMBOBOX = "banco";
    public final static String TIPOPIX_COMBOBOX = "tipoPix";
    public final static String TIPOPIX_ENABLED = "tipoPixEnabled";
    public final static String CHAVE_TEXT = "chavePix";
    public final static String AGENCIA_TEXT = "agencia";
    public final static String NUMCONTA_TEXT = "numConta";
    public final static String PIX_CHECKBOX = "pixCheckBox";
    public final static String CHAVE_INVALID = "chaveInvalid";
    public final static String CHAVE_ENABLED = "chaveEnabled";

    private DadoBancario dadoBancario;
    private DadoBancario dadoSaved;
    private ChavePix chavePix;
    private Contato contato;
    private Banco banco;
    private String agencia;
    private String numConta;
    private TipoPix tipoPix;
    private String chave;
    private boolean pixEnabled;
    private boolean pixInvalidFormat;
    private boolean updated;
    private boolean isUpdating;
    private PropertyFirer pf;

    public DadoBancarioFormModel(DadoBancario dadoBancario) {
        this.dadoBancario = dadoBancario;
        pf = new PropertyFirer(this);
        updateAllData();
    }

    private void updateAllData() {
        contato = dadoBancario.getContato();
        banco = dadoBancario.getBanco();
        numConta = dadoBancario.getNumeroConta();
        agencia = dadoBancario.getAgencia();
        if (dadoBancario.getChavePix() != null) {
            pixEnabled = true;
            chavePix  = dadoBancario.getChavePix();
            tipoPix = chavePix.getTipoPix();
            chave = chavePix.toString();
        }
    }


    public DadoBancario getDadoBancario() { return dadoBancario; }
    public DadoBancario getDadoSaved() { return dadoSaved; }
    public ChavePix getChavePix() { return chavePix; }
    public Contato getContato() { return contato; }
    public Banco getBanco() { return banco; }
    public String getAgencia() { return agencia; }
    public String getNumConta() { return numConta; }
    public TipoPix getTipoPix() { return tipoPix; }
    public String getChave() { return chave; }
    public boolean isPixInvalidFormat() { return pixInvalidFormat; }
    public boolean isPixEnabled() { return pixEnabled; }

    public void setContato(Contato contato) {
        this.contato = contato;
        this.dadoBancario.setContato(contato);
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
        this.dadoBancario.setBanco(banco);
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
        this.dadoBancario.setAgencia(agencia);
    }

    public void setNumConta(String numConta) {
        this.numConta = numConta;
        this.dadoBancario.setNumeroConta(numConta);
    }

    public void setPixEnabled(boolean pixEnabled) {
        if (isUpdating) return;
        isUpdating = true;
        this.pixEnabled = pixEnabled;
        if (!pixEnabled) {
            this.chavePix = null;
            triggerTipoPix(null);
        } else {
            if (dadoBancario.getChavePix() != null) {
                this.chavePix  = dadoBancario.getChavePix();
                triggerTipoPix(chavePix.getTipoPix());
            } else {
                chavePix = new ChavePix();
                chavePix.setContato(this.contato);
                triggerTipoPix(null);
            }
        }
        pf.firePropertyChange(CHAVE_ENABLED, pixEnabled);
        pf.firePropertyChange(TIPOPIX_ENABLED, pixEnabled);
        isUpdating = false;
    }

    public void setTipoPix(TipoPix tipoPix) {
        if (isUpdating) return;
        isUpdating =  true;
        triggerTipoPix(tipoPix);
        isUpdating = false;
    }

    public void setChave(String chave) {
        if (isUpdating) return;
        isUpdating = true;
        triggerChave(chave);
        isUpdating = false;
    }

    public void setDadoSaved(DadoBancario dadoSaved) {
        this.dadoSaved = dadoSaved;
    }

    private void triggerTipoPix(TipoPix tipoPix) {
        this.tipoPix = tipoPix;
        if (chavePix != null) {
            chavePix.setTipoPix(tipoPix);
            triggerChave(chavePix.getChave());
        } else {
            triggerChave(null);
        }
        pf.firePropertyChange(TIPOPIX_COMBOBOX, tipoPix);
    }

    private void triggerChave(String chave) {
        if (chavePix != null) {
            chavePix.setChave(chave);
            pixInvalidFormat = chavePix.isInvalidFormat();
            this.chave = chavePix.toString();
        } else {
            this.chave = null;
            pixInvalidFormat = false;
        }
        pf.firePropertyChange(CHAVE_INVALID, pixInvalidFormat);
        pf.firePropertyChange(CHAVE_TEXT, this.chave);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { pf.addPropertyChangeListener(listener); }

    @Override
    public void fireInitialChanges() {
        isUpdating = true;
        pf.firePropertyChange(CONTATO_COMBOBOX, contato);
        pf.firePropertyChange(BANCO_COMBOBOX, banco);
        pf.firePropertyChange(TIPOPIX_COMBOBOX, tipoPix);
        pf.firePropertyChange(CHAVE_TEXT, chave);
        pf.firePropertyChange(PIX_CHECKBOX, pixEnabled);
        pf.firePropertyChange(CHAVE_ENABLED, pixEnabled);
        pf.firePropertyChange(TIPOPIX_ENABLED, pixEnabled);
        pf.firePropertyChange(AGENCIA_TEXT, agencia);
        pf.firePropertyChange(NUMCONTA_TEXT, numConta);
        isUpdating = false;
    }

}
