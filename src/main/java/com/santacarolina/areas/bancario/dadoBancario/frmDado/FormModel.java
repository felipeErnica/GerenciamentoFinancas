package com.santacarolina.areas.bancario.dadoBancario.frmDado;

import java.beans.PropertyChangeListener;

import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.Banco;
import com.santacarolina.model.Contato;
import com.santacarolina.model.DadoBancario;
import com.santacarolina.util.PropertyFirer;

public class FormModel implements ViewUpdater {

    public final static String CONTATO_COMBOBOX = "contato";
    public final static String BANCO_COMBOBOX = "banco";
    public final static String AGENCIA_TEXT = "agencia";
    public final static String NUMCONTA_TEXT = "numConta";

    private DadoBancario dadoBancario;
    private long idOriginal;
    private Contato contato;
    private Banco banco;
    private String agencia;
    private String numConta;

    private PropertyFirer pf;

    public FormModel(DadoBancario dadoBancario) {
        this.dadoBancario = dadoBancario;
        pf = new PropertyFirer(this);
        updateAllData();
    }

    private void updateAllData() {
        idOriginal = dadoBancario.getId();
        contato = dadoBancario.getContato();
        banco = dadoBancario.getBanco();
        numConta = dadoBancario.getNumeroConta();
        agencia = dadoBancario.getAgencia();
    }

    public DadoBancario getDadoBancario() { return dadoBancario; }
    public long getIdOriginal() { return idOriginal; }
    public Contato getContato() { return contato; }
    public Banco getBanco() { return banco; }
    public String getAgencia() { return agencia; }
    public String getNumConta() { return numConta; }

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

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { pf.addPropertyChangeListener(listener); }

    @Override
    public void fireInitialChanges() {
        pf.firePropertyChange(CONTATO_COMBOBOX, contato);
        pf.firePropertyChange(BANCO_COMBOBOX, banco);
        pf.firePropertyChange(AGENCIA_TEXT, agencia);
        pf.firePropertyChange(NUMCONTA_TEXT, numConta);
    }

}
