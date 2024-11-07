package com.santacarolina.areas.bancario.pix.frmPix;

import java.beans.PropertyChangeListener;
import java.util.List;

import com.santacarolina.dao.DadoDAO;
import com.santacarolina.dao.PixDAO;
import com.santacarolina.enums.TipoPix;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.ChavePix;
import com.santacarolina.model.Contato;
import com.santacarolina.model.DadoBancario;
import com.santacarolina.util.PropertyFirer;

public class FormModel implements ViewUpdater {

    public static final String CONTA_CHECKBOX = "contaCheckBox";
    public static final String CHAVE_INVALID = "invalidFormat";
    public static final String CONTA_ENABLED = "contaEnabled";
    public static final String CONTA_LIST = "contaList";
    public static final String PIX_ENABLED  = "dadosEnabled";
    public static final String CONTATO = "contato";
    public static final String CONTA = "conta";
    public static final String BANCO = "banco";
    public static final String CHAVE = "chave";
    public static final String TIPO_PIX = "tipoPix";

    private boolean isUpdating;
    private List<DadoBancario> contaList;
    private ChavePix chavePix;
    private Contato contato;
    private DadoBancario dadoBancario;
    private String chave;
    private String banco;
    private TipoPix tipoPix;
    private boolean invalidFormat;
    private boolean contaSelected;
    private boolean contaEnabled;
    private boolean dadosEnabled;
    private final PropertyFirer ps;

    public FormModel(ChavePix chavePix) throws FetchFailException {
        ps = new PropertyFirer(this);
        this.dadosEnabled = true;
        this.chavePix = chavePix;
        updateAllData();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { ps.addPropertyChangeListener(listener); }

    private void updateAllData() throws FetchFailException {
        this.contato = this.chavePix.getContato();
        this.contaList = new DadoDAO().findByContato(this.contato);
        this.contaSelected = this.chavePix.getDadoBancario() != null || this.chavePix.getId() == 0;
        this.contaEnabled = this.contaSelected;
        this.dadoBancario = this.chavePix.getDadoBancario();
        this.tipoPix = this.chavePix.getTipoPix();
        this.chave = this.chavePix.toString();
        this.invalidFormat = this.chavePix.isInvalidFormat();
        if (this.dadoBancario != null) this.banco = this.dadoBancario.getBanco().getNomeBanco();
    }

    @Override
    public void fireInitialChanges() {
        isUpdating = true;
        ps.firePropertyChange(CONTATO, this.contato);
        ps.firePropertyChange(CONTA_CHECKBOX, this.contaSelected);
        ps.firePropertyChange(CONTA_ENABLED, this.contaEnabled);
        ps.firePropertyChange(CONTA_LIST, this.contaList);
        ps.firePropertyChange(CONTA, this.dadoBancario);
        ps.firePropertyChange(PIX_ENABLED, this.dadosEnabled);
        ps.firePropertyChange(TIPO_PIX, this.tipoPix);
        ps.firePropertyChange(CHAVE_INVALID, this.invalidFormat);
        ps.firePropertyChange(CHAVE, chave);
        ps.firePropertyChange(BANCO, banco);
        isUpdating = false;
    }


    public Contato getContato() { return contato; }
    public String getChave() { return chave; }
    public DadoBancario getDadoBancario() { return dadoBancario; }
    public TipoPix getTipoPix() { return tipoPix; }
    public boolean isInvalidFormat() { return invalidFormat; }
    public boolean isContaEnabled() { return contaEnabled; }
    public boolean isDadosEnabled() { return dadosEnabled; }
    public String getBanco() { return banco; }
    public List<DadoBancario> getContaList() { return contaList; }
    public ChavePix getChavePix() { return chavePix; }

    public void setContato(Contato contato) throws FetchFailException {
        if (isUpdating) return;
        isUpdating = true;
        triggerContato(contato);
        ps.firePropertyChange(CONTA_ENABLED, this.contaEnabled);
        ps.firePropertyChange(CONTA_LIST, this.contaList);
        ps.firePropertyChange(CONTA, this.dadoBancario);
        ps.firePropertyChange(PIX_ENABLED, this.dadosEnabled);
        ps.firePropertyChange(TIPO_PIX, this.tipoPix);
        ps.firePropertyChange(CHAVE_INVALID, this.invalidFormat);
        ps.firePropertyChange(CHAVE, this.chave);
        ps.firePropertyChange(BANCO, this.banco);
        isUpdating = false;
    }

    public void setContaSelected(boolean contaSelected) {
        if (isUpdating) return;
        isUpdating = true;
        this.contaSelected = contaSelected;
        triggerEnableConta();
        ps.firePropertyChange(CONTA_ENABLED, this.contaEnabled);
        ps.firePropertyChange(CONTA_LIST, this.contaList);
        ps.firePropertyChange(CONTA, this.dadoBancario);
        ps.firePropertyChange(PIX_ENABLED, this.dadosEnabled);
        ps.firePropertyChange(TIPO_PIX, this.tipoPix);
        ps.firePropertyChange(CHAVE_INVALID, this.invalidFormat);
        ps.firePropertyChange(CHAVE, this.chave);
        ps.firePropertyChange(BANCO, this.banco);
        isUpdating = false;
    }

    public void setDadoBancario(DadoBancario dadoBancario) {
        if (isUpdating) return;
        isUpdating = true;
        triggerDado(dadoBancario);
        ps.firePropertyChange(PIX_ENABLED, this.dadosEnabled);
        ps.firePropertyChange(TIPO_PIX, this.tipoPix);
        ps.firePropertyChange(CHAVE_INVALID, this.invalidFormat);
        ps.firePropertyChange(CHAVE, this.chave);
        ps.firePropertyChange(BANCO, this.banco);
        isUpdating = false;
    }

    public void setTipoPix(TipoPix tipoPix) {
        if (isUpdating) return;
        isUpdating = true;
        triggerTipoPix(tipoPix);
        triggerChave(this.chave);
        ps.firePropertyChange(CHAVE_INVALID, this.invalidFormat);
        ps.firePropertyChange(CHAVE, this.chave);
        isUpdating = false;
    }

    public void setChave(String chave) {
        if (isUpdating) return;
        isUpdating = true;
        triggerChave(chave);
        ps.firePropertyChange(CHAVE_INVALID, this.invalidFormat);
        ps.firePropertyChange(CHAVE, this.chave);
        isUpdating = false;
    }

    private void triggerContato(Contato contato) throws FetchFailException {
        this.contato = contato;
        this.chavePix.setContato(contato);
        this.contaList = new DadoDAO().findByContato(contato);
        triggerEnableConta();
    }

    private void triggerEnableConta() {
        if (this.contaSelected) {
            if (!this.contaList.isEmpty()) {
                contaEnabled = true;
                dadosEnabled = true;
                triggerDado(contaList.get(0));
            } else {
                contaEnabled = false;
                dadosEnabled = false;
                triggerDado(null);
            }
        } else {
            contaEnabled = false;
            dadosEnabled = true;
            triggerDado(null);
        }
    }

    private void triggerDado(DadoBancario d) {
        this.dadoBancario = d;
        this.chavePix.setDadoBancario(d);
        if (d != null) {
            this.banco = d.getBanco().getNomeBanco();
            
            ChavePix chavePix;
            try {
                chavePix = new PixDAO().findByDadoId(d.getId()).orElse(null);
            } catch (FetchFailException e) {
                chavePix = null;
            }

            triggerChavePix(chavePix);
        }
        else {
            this.banco = "";
            triggerChavePix(null);
        }
    }

    private void triggerChavePix(ChavePix c) {
        if (c != null) {
            triggerTipoPix(c.getTipoPix());
            triggerChave(c.getChave());
        } else {
            triggerTipoPix(null);
            triggerChave("");
        }
    }

    private void triggerTipoPix(TipoPix tipoPix) {
        this.tipoPix = tipoPix;
        this.chavePix.setTipoPix(tipoPix);
    }

    private void triggerChave(String chave) {
        this.chavePix.setChave(chave);
        this.chave = chavePix.toString();
        this.invalidFormat = chavePix.isInvalidFormat();
    }

}
