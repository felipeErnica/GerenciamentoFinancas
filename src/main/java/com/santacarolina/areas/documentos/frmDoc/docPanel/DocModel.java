package com.santacarolina.areas.documentos.frmDoc.docPanel;

import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.enums.TipoDoc;
import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.Contato;
import com.santacarolina.model.DocumentoFiscal;
import com.santacarolina.model.PastaContabil;
import com.santacarolina.util.PropertyFirer;
import com.santacarolina.util.StringConversor;

import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DocModel implements ViewUpdater {

    public static final String FLUXO_CAIXA = "fluxoCaixa";
    public static final String EMISSOR = "emissor";
    public static final String PASTA = "pasta";
    public static final String TIPO_DOC = "tipoDoc";
    public static final String DATA_EMISSAO = "data";
    public static final String INVALID_DATE = "dataInvalida";
    public static final String VALOR = "valor";
    public static final String VALOR_INVALID = "invalidValor";
    public static final String CAMINHO  = "caminho";
    public static final String NUM_DOC = "numDoc";
    public static final String NUM_DOC_INVALID = "numDocInvalid";
    public static final String NUM_DOC_ENABLE = "numDocEnable";

    private DocumentoFiscal documentoFiscal;
    private FluxoCaixa fluxoCaixa;
    private Contato emissor;
    private PastaContabil pastaContabil;
    private TipoDoc tipoDoc;
    private LocalDate emissionDate;
    private boolean invalidDate;
    private Double docValue;
    private boolean invalidValue;
    private String docPath;
    private Long docNumber;
    private boolean docNumberEnable;
    private boolean isUpdating;
    private final PropertyFirer pf;
    private boolean invalidDocNumber;

    public DocModel(DocumentoFiscal d) {
        this.documentoFiscal = d;
        this.pf = new PropertyFirer(this);
        init();
    }

    private void init() {
        fluxoCaixa = documentoFiscal.getFluxoCaixa();
        emissor = documentoFiscal.getEmissor();
        pastaContabil = documentoFiscal.getPasta();
        tipoDoc = documentoFiscal.getTipoDoc();
        emissionDate = documentoFiscal.getDataEmissao();
        docValue = documentoFiscal.getValor();
        docPath = documentoFiscal.getCaminhoDocumento();
        docNumber = documentoFiscal.getNumDoc();
        docNumberEnable = tipoDoc == TipoDoc.NFE || tipoDoc == TipoDoc.NOTA_FISCAL;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { pf.addPropertyChangeListener(listener); }

    @Override
    public void fireInitialChanges() {
        isUpdating = true;
        pf.firePropertyChange(FLUXO_CAIXA, fluxoCaixa);
        pf.firePropertyChange(EMISSOR, emissor);
        pf.firePropertyChange(PASTA, pastaContabil);
        pf.firePropertyChange(TIPO_DOC, tipoDoc);

        pf.firePropertyChange(DATA_EMISSAO, emissionDate != null ?
                emissionDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) :
                null);

        pf.firePropertyChange(VALOR, StringConversor.getCurrency(docValue));
        pf.firePropertyChange(CAMINHO, docPath);
        pf.firePropertyChange(NUM_DOC, docNumber);
        pf.firePropertyChange(NUM_DOC_ENABLE, docNumberEnable);
        isUpdating = false;
    }

    public FluxoCaixa getFluxoCaixa() { return fluxoCaixa; }
    public Contato getEmissor() { return emissor; }
    public PastaContabil getPastaContabil() { return pastaContabil; }
    public TipoDoc getTipoDoc() { return tipoDoc; }
    public LocalDate getEmissionDate() { return emissionDate; }
    public Double getDocValue() { return docValue; }
    public String getDocPath() { return docPath; }
    public Long getDocNumber() { return docNumber; }
    public boolean isDocNumberEnable() { return docNumberEnable; }
    public boolean isInvalidDocNumber() { return invalidDocNumber; }
    public boolean isInvalidDate() { return invalidDate; }
    public boolean isInvalidValue() { return invalidValue; }

    public void setFluxoCaixa(FluxoCaixa fluxoCaixa) {
        if (isUpdating) return;
        isUpdating  = true;
        this.fluxoCaixa = fluxoCaixa;
        documentoFiscal.setFluxoCaixa(fluxoCaixa);
        triggerDocValue(docValue);
        if (fluxoCaixa == FluxoCaixa.DESPESA) {
            documentoFiscal.getDuplicataList().forEach(d -> d.setValor(Math.abs(d.getValor())*-1));
            documentoFiscal.getProdutoList().forEach(d -> d.setValorUnit(Math.abs(d.getValorUnit())*-1));
        } else {
            documentoFiscal.getDuplicataList().forEach(d -> d.setValor(Math.abs(d.getValor())));
            documentoFiscal.getProdutoList().forEach(d -> d.setValorUnit(Math.abs(d.getValorUnit())));
        }
        isUpdating = false;
    }

    public void setEmissor(Contato emissor) {
        this.emissor = emissor;
        documentoFiscal.setEmissor(emissor);
    }

    public void setPastaContabil(PastaContabil pastaContabil) {
        this.pastaContabil = pastaContabil;
        documentoFiscal.setPasta(pastaContabil);
    }

    public void setTipoDoc(TipoDoc tipoDoc) {
        if (isUpdating) return;
        isUpdating  = true;
        this.tipoDoc = tipoDoc;
        documentoFiscal.setTipoDoc(tipoDoc);
        triggerDocNumberEnable();
        isUpdating = false;
    }

    public void setEmissionDate(LocalDate emissionDate) {
        if (isUpdating) return;
        isUpdating  = true;
        this.emissionDate = emissionDate;
        this.invalidDate = false;
        documentoFiscal.setDataEmissao(emissionDate);
        if (emissionDate != null) {
            String fieldValue = emissionDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            pf.firePropertyChange(DATA_EMISSAO, fieldValue);
        }
        isUpdating = false;
    }

    public void setDocPath(String docPath) {
        if (isUpdating) return;
        isUpdating = true;
        this.docPath = docPath;
        documentoFiscal.setCaminhoDocumento(docPath);
        pf.firePropertyChange(CAMINHO, docPath);
        isUpdating = false;
    }

    public void setDocValue(Double docValue) {
        if (isUpdating) return;
        isUpdating = true;
        triggerDocValue(docValue);
        isUpdating = false;
    }

    public void setDocNumber(Long docNumber) {
        if (isUpdating) return;
        isUpdating = true;
        this.docNumber = docNumber;
        this.invalidDocNumber = false;
        documentoFiscal.setNumDoc(docNumber);
        pf.firePropertyChange(NUM_DOC_INVALID, false);
        isUpdating = false;
    }

    public void setInvalidDocNumber(boolean invalidDocNumber) {
        if (isUpdating) return;
        isUpdating = true;
        this.invalidDocNumber = invalidDocNumber;
        pf.firePropertyChange(NUM_DOC_INVALID, true);
        isUpdating = false;
    }

    public void setInvalidDate(boolean invalidDate) {
        if (isUpdating) return;
        isUpdating = true;
        this.invalidDate = true;
        pf.firePropertyChange(INVALID_DATE, invalidDate);
        isUpdating = false;
    }

    public void setInvalidValue(boolean invalidValue) {
        if (isUpdating) return;
        isUpdating = true;
        this.invalidValue = invalidValue;
        pf.firePropertyChange(VALOR_INVALID, invalidValue);
        isUpdating = false;
    }

    public void triggerDocNumber(Long docNumber) {
        this.docNumber = docNumber;
        documentoFiscal.setNumDoc(docNumber);
        pf.firePropertyChange(NUM_DOC, docNumber);
    }

    public void triggerDocNumberEnable() {
        docNumberEnable = tipoDoc == TipoDoc.NOTA_FISCAL || tipoDoc == TipoDoc.NFE;
        if (!docNumberEnable) triggerDocNumber(null);
        pf.firePropertyChange(NUM_DOC_ENABLE, docNumberEnable);
    }

    public void triggerDocValue(Double docValue) {
        this.docValue = docValue;
        if (docValue != null) {
            if (fluxoCaixa == FluxoCaixa.DESPESA) docValue = Math.abs(docValue)*-1;
            else docValue = Math.abs(docValue);
            documentoFiscal.setValor(docValue);
            String fieldValue = StringConversor.getCurrency(docValue);
            pf.firePropertyChange(VALOR, fieldValue);
        } else {
            documentoFiscal.setValor(0d);
        }
    }

}
