package com.santacarolina.areas.documentos.frmDoc.frmAddPix;

import com.santacarolina.dao.PixDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.ChavePix;
import com.santacarolina.model.Contato;
import com.santacarolina.model.DadoBancario;
import com.santacarolina.model.Duplicata;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.PropertyFirer;

import java.beans.PropertyChangeListener;
import java.util.List;

public class FormModel implements ViewUpdater {

    public static final String CONTATO = "contato";
    public static final String CHAVE_ENABLE = "chaveEnable";
    public static final String CHAVE_LIST = "chaveList";
    public static final String CHAVE = "chave";
    public static final String TIPO_PIX = "tipoPix";
    public static final String BANCO = "banco";
    public static final String AGENCIA = "agencia";
    public static final String CONTA = "conta";

    private ChavePix chave;
    private List<Duplicata> duplicataList;

    private PixDAO pixDAO;
    private boolean updating;
    private Contato contato;
    private boolean chaveEnabled;
    private List<ChavePix> chaveList;
    private String tipoPix;
    private String banco;
    private String agencia;
    private String conta;
    private PropertyFirer pf;

    public FormModel(List<Duplicata> list) throws FetchFailException {
        this.duplicataList = list;
        pixDAO = new PixDAO();
        pf = new PropertyFirer(this);
        if (!list.isEmpty()) {
            Duplicata dup = list.get(0);
            chave = dup.getPix();
            if (chave != null) {
                init();
            } else {
                chave = dup.getDadoBancario() != null ? dup.getDadoBancario().getChavePix() : null;
                if (chave != null) init();
                else initEmpty(dup);
            }
        }
    }

    private void initEmpty(Duplicata dup) throws FetchFailException {
        contato = dup.getDocumento().getEmissor();
        if (contato == null) return;
        chaveList = pixDAO.findByContato(contato);
        if (!chaveList.isEmpty()) {
            chaveEnabled = true;
            chave = chaveList.get(0);
            tipoPix = chave.getTipoPix().getString();
            if (chave.getDadoBancario() != null) {
                DadoBancario dado = chave.getDadoBancario();
                agencia = dado.getAgencia();
                conta = dado.getNumeroConta();
                banco = dado.getBanco().getNomeBanco();
            }
        }
    }

    private void init() throws FetchFailException {
        contato = chave.getContato();
        chaveList  = pixDAO.findByContato(contato);
        chaveEnabled = true;
        tipoPix = chave.getTipoPix().getString();
        if (chave.getDadoBancario() != null) {
            DadoBancario dado = chave.getDadoBancario();
            agencia = dado.getAgencia();
            conta = dado.getNumeroConta();
            banco = dado.getBanco().getNomeBanco();
        }
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { pf.addPropertyChangeListener(listener); }

    @Override
    public void fireInitialChanges() {
        updating = true;
        pf.firePropertyChange(CONTATO, contato);
        pf.firePropertyChange(CHAVE_ENABLE, chaveEnabled);
        pf.firePropertyChange(CHAVE_LIST, chaveList);
        pf.firePropertyChange(CHAVE, chave);
        pf.firePropertyChange(TIPO_PIX, tipoPix);
        pf.firePropertyChange(BANCO, banco);
        pf.firePropertyChange(AGENCIA, agencia);
        pf.firePropertyChange(CONTA, conta);
        updating = false;
    }

    public ChavePix getChave() { return chave; }
    public List<Duplicata> getDuplicataList() { return duplicataList; }

    public void setContato(Contato contato) {
        if (updating) return;
        updating = true;
        this.contato = contato;
        triggerChaveList();
        updating = false;
    }

    public void setChave(ChavePix chave) {
        if (updating) return;
        updating = true;
        triggerChave(chave);
        updating = false;
    }

    public void triggerChaveList() {
        try {
            this.chaveList = pixDAO.findByContato(contato);
        } catch (FetchFailException e) {
            this.chaveList = null;
            CustomErrorThrower.throwError(e);
        }
        triggerChaveEnabled();
        pf.firePropertyChange(CHAVE_LIST, chaveList);
    }

    public void triggerChaveEnabled() {
        if (chaveList != null && !chaveList.isEmpty()) {
            chaveEnabled = true;
            triggerChave(chaveList.get(0));
        } else {
            chaveEnabled = false;
            triggerChave(null);
        }
        pf.firePropertyChange(CHAVE_ENABLE, chaveEnabled);
    }

    public void triggerChave(ChavePix chave) {
        this.chave = chave;
        triggerAgencia(chave);
        triggerBanco(chave);
        triggerConta(chave);
        triggerTipoPix(chave);
    }

    public void triggerTipoPix(ChavePix chave) {
        this.tipoPix = chave != null ? chave.getTipoPix().getString() : null;
        pf.firePropertyChange(TIPO_PIX, tipoPix);
    }

    public void triggerBanco(ChavePix chave) {
        if (chave != null && chave.getDadoBancario() != null) banco = chave.getDadoBancario().getBanco().getNomeBanco();
        else banco = null;
        pf.firePropertyChange(BANCO, banco);
    }

    public void triggerAgencia(ChavePix chave) {
        if (chave != null && chave.getDadoBancario() != null) agencia = chave.getDadoBancario().getAgencia();
        else agencia = null;
        pf.firePropertyChange(AGENCIA, agencia);
    }

    public void triggerConta(ChavePix chave) {
        if (chave != null && chave.getDadoBancario() != null) conta = chave.getDadoBancario().getNumeroConta();
        else conta = null;
        pf.firePropertyChange(CONTA, conta);
    }

}
