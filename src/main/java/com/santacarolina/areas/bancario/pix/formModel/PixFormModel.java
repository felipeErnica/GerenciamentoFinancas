package com.santacarolina.areas.bancario.pix.formModel;

import com.santacarolina.dao.ContatoDao;
import com.santacarolina.enums.TipoPix;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.NewFormModel;
import com.santacarolina.model.beans.ChavePix;
import com.santacarolina.model.beans.Contato;
import com.santacarolina.model.beans.DadoBancario;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.PropertyFirer;

import java.beans.PropertyChangeListener;
import java.util.List;

public class PixFormModel implements NewFormModel {

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
    private boolean isUpdated;
    private final PropertyFirer ps;

    public PixFormModel(ChavePix chavePix) throws FetchFailException {
        ps = new PropertyFirer(this);
        this.chavePix = chavePix;
        this.dadosEnabled = true;
        updateAllData();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        ps.addPropertyChangeListener(listener);
    }

    private void updateAllData() throws FetchFailException {
        this.contato = this.chavePix.getContato();
        this.contaList = new ContatoDao().findContas(this.contato);
        this.contaSelected = this.chavePix.getDadoBancario() != null || this.chavePix.getId() == 0;
        this.contaEnabled = this.contaSelected;
        this.dadoBancario = this.chavePix.getDadoBancario();
        this.tipoPix = this.chavePix.getTipoPix();
        this.chave = this.chavePix.getChave();
        if (this.dadoBancario != null) this.banco = this.dadoBancario.getBanco().getNomeBanco();
    }

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
        ps.firePropertyChange(CHAVE, this.chave);
        ps.firePropertyChange(BANCO, this.banco);
        isUpdating = false;
    }

    public boolean updatingNotAllowed() {
        if (this.chave.isEmpty()) {
            OptionDialog.showErrorDialog(
                    "A chave não pode estar vazia!",
                    "Informação vazia!"
            );
            return true;
        } else if (this.tipoPix == null) {
            OptionDialog.showErrorDialog(
                    "Selecione o Tipo de Chave!",
                    "Informação vazia!"
            );
            return true;
        } else if (this.invalidFormat) {
            OptionDialog.showOptionDialog(
                    "O formato da chave é inválido!",
                    "Formato Inválido!"
            );
            return true;
        } else if (contaSelected && this.dadoBancario == null) {
            OptionDialog.showOptionDialog(
                    "Informe uma Conta Bancária!",
                    "Informação Incompleta!"
            );
            return true;
        }   else return false;
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
    public boolean isUpdated() { return isUpdated; }

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
        System.out.println("inicio");
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
        System.out.println("fim");
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

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }

    private void triggerContato(Contato contato) throws FetchFailException {
        this.contato = contato;
        this.chavePix.setContato(contato);
        this.contaList = new ContatoDao().findContas(contato);
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
            triggerChavePix(d.getChavePix());
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
