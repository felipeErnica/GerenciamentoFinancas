
package com.santacarolina.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.santacarolina.enums.TipoPagamento;
import com.santacarolina.model.ChavePix;
import com.santacarolina.model.DadoBancario;
import com.santacarolina.model.Duplicata;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DuplicataDTO {

    private long id;
    private int numDup;
    private LocalDate dataVencimento;
    private TipoPagamento tipoPagamento;
    private double valor;
    private String boletoCaminho;
    private DadoBancario dado;
    private ChavePix pix;
    private boolean paga;

    public DuplicataDTO() {}

    public DuplicataDTO(Duplicata duplicata) {
        this.id = duplicata.getId();
        this.numDup = duplicata.getNumDup();
        this.dataVencimento = duplicata.getDataVencimento();
        this.tipoPagamento = duplicata.getTipoPagamento();
        this.valor = duplicata.getValor();
        this.boletoCaminho = duplicata.getBoletoCaminho();
        this.dado = duplicata.getDado();
        this.pix = duplicata.getPix();
        this.paga = duplicata.isPaga();
    }


    public long getId() { return id; }
    public int getNumDup() { return numDup; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public TipoPagamento getTipoPagamento() { return tipoPagamento; }
    public double getValor() { return valor; }
    public String getBoletoCaminho() { return boletoCaminho; }
    public boolean isPaga() { return paga; }
    public DadoBancario getDado() { return dado; }
    public ChavePix getPix() { return pix; }

    public void setNumDup(int numDup) { this.numDup = numDup; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }
    public void setTipoPagamento(TipoPagamento tipoPagamento) { this.tipoPagamento = tipoPagamento; }
    public void setValor(double valor) { this.valor = valor; }
    public void setBoletoCaminho(String boletoCaminho) { this.boletoCaminho = boletoCaminho; }
    public void setPaga(boolean payed) { paga = payed; }
    public void setDado(DadoBancario dadoBancario) { this.dado = dadoBancario; }
    public void setPix(ChavePix pix) { this.pix = pix; }

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
