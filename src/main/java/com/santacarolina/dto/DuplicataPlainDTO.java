package com.santacarolina.dto;

import java.time.LocalDate;

import com.santacarolina.enums.TipoPagamento;
import com.santacarolina.model.Duplicata;

public class DuplicataPlainDTO {

    private long id;
    private long documentoId;
    private int numDup;
    private LocalDate dataVencimento;
    private TipoPagamento tipoPagamento;
    private double valor;
    private String boletoCaminho;
    private Long pixId;
    private Long dadoId;
    private boolean paga;

    public DuplicataPlainDTO(DuplicataDTO dup) {
        this.id = dup.getId();
        this.documentoId = dup.getDocId();
        this.numDup = dup.getNumDup();
        this.dataVencimento = dup.getDataVencimento();
        this.tipoPagamento = dup.getTipoPagamento();
        this.valor = dup.getValor();
        this.boletoCaminho = dup.getBoletoCaminho();
        this.pixId = dup.getPixId();
        this.dadoId = dup.getDadoId();
        this.paga = dup.isPaga();
    }

    public DuplicataPlainDTO(Duplicata dup) {
        this.id = dup.getId();
        this.documentoId = dup.getDocumentoId();
        this.numDup = dup.getNumDup();
        this.dataVencimento = dup.getDataVencimento();
        this.tipoPagamento = dup.getTipoPagamento();
        this.valor = dup.getValor();
        this.boletoCaminho = dup.getBoletoCaminho();
        this.pixId = dup.getPixId();
        this.dadoId = dup.getDadoId();
        this.paga = dup.isPayed();
    }

    public long getId() { return id; }
    public long getDocumentoId() { return documentoId; }
    public int getNumDup() { return numDup; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public TipoPagamento getTipoPagamento() { return tipoPagamento; }
    public double getValor() { return valor; }
    public String getBoletoCaminho() { return boletoCaminho; }
    public Long getPixId() { return pixId; }
    public Long getDadoId() { return dadoId; }
    public boolean isPaga() { return paga; }

    @Override
    public String toString() {
        return "DuplicataPlainDTO{id=" + id + ", documentoId=" + documentoId + ", numDup=" + numDup
                + ", dataVencimento=" + dataVencimento + ", tipoPagamento=" + tipoPagamento + ", valor=" + valor
                + ", boletoCaminho=" + boletoCaminho + ", pixId=" + pixId + ", dadoId=" + dadoId + "}";
    }

}

