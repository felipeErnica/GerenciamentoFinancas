package com.santacarolina.areas.documentos.frmDoc.frmAddTed;

import java.beans.PropertyChangeListener;
import java.util.List;

import com.santacarolina.dao.DadoDAO;
import com.santacarolina.dto.DuplicataDTO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.ChavePix;
import com.santacarolina.model.Contato;
import com.santacarolina.model.DadoBancario;
import com.santacarolina.model.DocumentoFiscal;
import com.santacarolina.util.PropertyFirer;

public class FormModel implements ViewUpdater {

    public static final String CONTATO = "contato";
    public static final String DADO = "dado";
    public static final String DADO_LIST = "dadoList";
    public static final String DADO_ENABLE = "dadoEnable";
    public static final String AGENCIA = "agencia";
    public static final String BANCO = "banco";

    private DadoBancario dado;
    private List<DuplicataDTO> duplicataList;
    private DadoDAO dadoDAO;

    private boolean updating;
    private Contato contato;
    private boolean dadoEnable;
    private List<DadoBancario> dadoList;
    private String agencia;
    private String banco;
    private PropertyFirer pf;

    public FormModel(List<DuplicataDTO> duplicataList, DocumentoFiscal documento) throws FetchFailException {
        this.duplicataList = duplicataList;
        dadoDAO = new DadoDAO();
        pf = new PropertyFirer(this);
        if (!duplicataList.isEmpty()) {
            DuplicataDTO dup = duplicataList.get(0);
            dado = dup.getDado();
            if (dado != null) {
                init();
            } else {
                ChavePix pix = dup.getPix();
                dado = pix != null ? pix.getDado() : null;
                if (dado != null) init();
                else initEmpty(dup, documento);
            }
        }
    }

    private void initEmpty(DuplicataDTO dup, DocumentoFiscal documento) throws FetchFailException {
        contato = documento.getEmissor();
        if (contato == null) return;
        dadoList = dadoDAO.findByContato(contato);
        if (!dadoList.isEmpty()) {
            dadoEnable = true;
            dado = dadoList.get(0);
            agencia = dado.getAgencia();
            banco = dado.getBanco().getNomeBanco();
        }
    }

    private void init() throws FetchFailException {
        contato = dado.getContato();
        dadoEnable = true;
        dadoList = dadoDAO.findByContato(contato);
        agencia = dado.getAgencia();
        banco = dado.getBanco().getNomeBanco();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { pf.addPropertyChangeListener(listener); }

    @Override
    public void fireInitialChanges() {
        updating = true;
        pf.firePropertyChange(CONTATO, contato);
        pf.firePropertyChange(DADO_LIST, dadoList);
        pf.firePropertyChange(DADO_ENABLE, dadoEnable);
        pf.firePropertyChange(AGENCIA, agencia);
        pf.firePropertyChange(BANCO, banco);
        updating = false;
    }

    public DadoBancario getDado() { return dado; }
    public List<DuplicataDTO> getDuplicataList() { return duplicataList; }

    public void setContato(Contato contato) {
        if (updating) return;
        updating = true;
        try {
            this.contato = contato;
            triggerDadoList();
        } catch (FetchFailException e) {
            this.contato = null;
        }
        updating = false;
    }

    public void setDado(DadoBancario dado) {
        if (updating) return;
        updating = true;
        this.dado = dado;
        triggerDado(dado);
        updating = false;
    }

    public void triggerDadoList() throws FetchFailException {
        this.dadoList = dadoDAO.findByContato(contato);
        triggerDadoEnable();
        pf.firePropertyChange(DADO_LIST, dadoList);
    }

    public void triggerDadoEnable() {
        if (!dadoList.isEmpty()) {
            this.dadoEnable = true;
            triggerDado(dadoList.get(0));
        } else {
            this.dadoEnable = false;
            triggerDado(null);
        }
        pf.firePropertyChange(DADO_ENABLE, dadoEnable);
    }

    public void triggerDado(DadoBancario dado) {
        this.dado = dado;
        triggerAgencia(dado);
        pf.firePropertyChange(DADO, dado);
    }

    public void triggerAgencia(DadoBancario dado) {
        this.agencia = dado != null ? dado.getAgencia() : null;
        triggerBanco(dado);
        pf.firePropertyChange(AGENCIA, agencia);
    }

    public void triggerBanco(DadoBancario dado) {
        this.banco = dado != null ? dado.getBanco().getNomeBanco() : null;
        pf.firePropertyChange(BANCO, banco);
    }

}
