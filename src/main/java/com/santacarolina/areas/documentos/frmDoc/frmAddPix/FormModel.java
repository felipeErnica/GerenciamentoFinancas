package com.santacarolina.areas.documentos.frmDoc.frmAddPix;

import java.beans.PropertyChangeListener;
import java.util.List;

import com.santacarolina.dao.PixDAO;
import com.santacarolina.dto.DuplicataDTO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.ChavePix;
import com.santacarolina.model.Contato;
import com.santacarolina.model.DadoBancario;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.PropertyFirer;

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
    private List<DuplicataDTO> duplicataList;

    private boolean updating;
    private Contato contato;
    private boolean chaveEnabled;
    private List<ChavePix> chaveList;
    private String tipoPix;
    private String banco;
    private String agencia;
    private String conta;
    private PropertyFirer pf;

    public FormModel(List<DuplicataDTO> list) throws FetchFailException {
        this.duplicataList = list;
        pf = new PropertyFirer(this);
        if (!list.isEmpty()) {
            DuplicataDTO dup = list.get(0);
            chave = dup.getPix();
            if (chave != null) {
                init();
            } else {
                if (dup.getDado() != null) chave = new PixDAO().findByDadoId(dup.getDado().getId()).orElse(null);
                if (chave != null) init();
                else initEmpty(dup);
            }
        }
    }

    private void initEmpty(DuplicataDTO dup) throws FetchFailException {
        contato = dup.getDocumento().getEmissor();
        if (contato == null) return;
        chaveList = new PixDAO().findByContato(contato);
        if (!chaveList.isEmpty()) {
            chaveEnabled = true;
            chave = chaveList.get(0);
            tipoPix = chave.getTipoPix().getString();
            if (chave.getDado() != null) {
                DadoBancario dado = chave.getDado();
                agencia = dado.getAgencia();
                conta = dado.getNumeroConta();
                banco = dado.getBanco().getNomeBanco();
            }
        }
    }

    private void init() throws FetchFailException {
        contato = chave.getContato();
        chaveList  = new PixDAO().findByContato(contato);
        chaveEnabled = true;    
        tipoPix = chave.getTipoPix().getString();
        if (chave.getDado() != null) {
            DadoBancario dado = chave.getDado();
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
    public List<DuplicataDTO> getDuplicataList() { return duplicataList; }

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
            this.chaveList = new PixDAO().findByContato(contato);
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
        if (chave != null && chave.getDado() != null) banco = chave.getDado().getBanco().getNomeBanco();
        else banco = null;
        pf.firePropertyChange(BANCO, banco);
    }

    public void triggerAgencia(ChavePix chave) {
        if (chave != null && chave.getDado() != null) agencia = chave.getDado().getAgencia();
        else agencia = null;
        pf.firePropertyChange(AGENCIA, agencia);
    }

    public void triggerConta(ChavePix chave) {
        if (chave != null && chave.getDado() != null) conta = chave.getDado().getNumeroConta();
        else conta = null;
        pf.firePropertyChange(CONTA, conta);
    }

}
