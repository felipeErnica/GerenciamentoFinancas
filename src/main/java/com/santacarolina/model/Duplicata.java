package com.santacarolina.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.santacarolina.dao.ConciliacaoDAO;
import com.santacarolina.dao.DadoDAO;
import com.santacarolina.dao.DocumentoDAO;
import com.santacarolina.dao.PixDAO;
import com.santacarolina.enums.TipoPagamento;
import com.santacarolina.exceptions.FetchFailException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Duplicata {

    private long id;
    private DocumentoFiscal documento;
    private long documentoId;
    private int numDup;
    private LocalDate dataVencimento;
    private TipoPagamento tipoPagamento;
    private double valor;
    private String boletoCaminho;
    private Long dadoId;
    private DadoBancario dado;
    private Long pixId;
    private ChavePix pix;
    private boolean paga;

    private List<Conciliacao> listConciliacao;

    public DocumentoFiscal getDocumento() {
        try {
            if (documento == null && documentoId != 0) documento = new DocumentoDAO().findById(documentoId).orElse(null);
        } catch (FetchFailException ignored) { }
        return documento;
    }

    public DadoBancario getDado() {
        try {
            if (dado == null && dadoId != null) dado = new DadoDAO().findById(dadoId).orElse(null);
        } catch (FetchFailException ignored) { }
        return dado;
    }

    public ChavePix getPix() {
        try {
            if (pix == null && pixId != null) pix = new PixDAO().findById(pixId).orElse(null);
        } catch (FetchFailException ignored) { }
        return pix;
    }

    public long getId() { return id; }
    public int getNumDup() { return numDup; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public TipoPagamento getTipoPagamento() { return tipoPagamento; }
    public double getValor() { return this.valor; }
    public String getBoletoCaminho() { return boletoCaminho; }
    public boolean isPaga() { return paga; }
    public Long getDocumentoId() { return documentoId; }
    public Long getDadoId() { return dadoId; }
    public Long getPixId() { return pixId; }

    public List<Conciliacao> getListConciliacao() {
        if (listConciliacao == null) {
            try {
                listConciliacao = new ConciliacaoDAO().findByDuplicata(id);
            } catch (FetchFailException e) {
                listConciliacao = null;
            }
        }
        return listConciliacao; 
    }

    public void setDocumento(DocumentoFiscal documento) {
        this.documento = documento;
        this.documentoId = documento != null ? documento.getId() : 0;
    }

    public void setDado(DadoBancario dadoBancario) {
        this.dado = dadoBancario;
        this.dadoId = dadoBancario != null ? dadoBancario.getId() : null;
    }

    public void setPix(ChavePix pix) {
        this.pix = pix;
        this.pixId = pix != null ? pix.getId() : null;
    }

    public void setNumDup(int numDup) { this.numDup = numDup; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }
    public void setTipoPagamento(TipoPagamento tipoPagamento) { this.tipoPagamento = tipoPagamento; }
    public void setValor(double valor) { this.valor = valor; }
    public void setBoletoCaminho(String boletoCaminho) { this.boletoCaminho = boletoCaminho; }
    public void setPaga(boolean payed) { paga = payed; }
    public void setPixId(Long pixId) { this.pixId = pixId; }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Duplicata{");
        sb.append("id=").append(id);
        sb.append(", parcela=").append(numDup);
        sb.append(", dataVenc=").append(dataVencimento);
        sb.append(", metodoPagto=").append(tipoPagamento);
        sb.append(", valor=").append(valor);
        sb.append(", caminhoPagto='").append(boletoCaminho).append('\'');
        sb.append(", dadoBancario=").append(dado);
        sb.append(", paga=").append(paga);
        sb.append('}');
        return sb.toString();
    }

}
