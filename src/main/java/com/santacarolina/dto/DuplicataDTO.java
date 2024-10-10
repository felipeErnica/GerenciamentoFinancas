package com.santacarolina.dto;

import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.enums.TipoPagamento;
import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.Duplicata;

import java.time.LocalDate;

public class DuplicataDTO implements FromDTO<Duplicata> {

    private Long docId;
    private Long dadoId;
    private long id;
    private int numDup;
    private TipoPagamento tipoPagamento;
    private LocalDate dataVencimento;
    private String nomeContato;
    private String boletoCaminho;
    private double valor;
    private boolean paga;
    private String conta;
    private Long pixId;
    private FluxoCaixa fluxoCaixa;

    public DuplicataDTO() {}

    public DuplicataDTO (Duplicata d) {
        this.docId = d.getDocumentoId();
        this.dadoId = d.getDadoId();
        this.id = d.getId();
        this.numDup = d.getNumDup();
        this.tipoPagamento = d.getTipoPagamento();
        this.dataVencimento = d.getDataVencimento();
        this.boletoCaminho = d.getBoletoCaminho();
        this.valor = d.getValor();
        this.paga = d.isPayed();
        this.pixId = d.getPixId();
    }

    public Long getDocId() { return docId; }
    public Long getDadoId() { return dadoId; }
    public long getId() { return id; }
    public int getNumDup() { return numDup; }
    public TipoPagamento getTipoPagamento() { return tipoPagamento; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public String getNomeContato() { return nomeContato; }
    public String getBoletoCaminho() { return boletoCaminho; }
    public double getValor() { return valor; }
    public boolean isPaga() { return paga; }
    public String getConta() { return conta; }
    public Long getPixId() { return pixId; }
    public FluxoCaixa getFluxoCaixa() { return fluxoCaixa; }

    public void setValor(double valor) { this.valor = valor; }

    @Override
    public Duplicata fromDTO() { return new Duplicata(this); }

}
