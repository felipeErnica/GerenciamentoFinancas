package com.santacarolina.model.beans;

import com.santacarolina.enums.FluxoCaixa;

public class ClassificacaoContabil {

    private long id;
    private FluxoCaixa fluxoCaixa;
    private long numeroIdentificacao;
    private String nomeClassificacao;

    public ClassificacaoContabil() {
        fluxoCaixa = FluxoCaixa.DESPESA;
        nomeClassificacao = "";
    }

    public long getId() { return id; }
    public FluxoCaixa getFluxoCaixa() { return fluxoCaixa; }
    public String getNomeClassificacao() { return nomeClassificacao; }
    public long getNumeroIdentificacao() { return numeroIdentificacao; }

    public void setId(long id) { this.id = id; }
    public void setFluxoCaixa(FluxoCaixa fluxoCaixa) { this.fluxoCaixa = fluxoCaixa; }
    public void setNumeroIdentificacao(long numeroIdentificacao) { this.numeroIdentificacao = numeroIdentificacao; }
    public void setNomeClassificacao(String nomeClassificacao) { this.nomeClassificacao = nomeClassificacao; }

    @Override
    public String toString() { return Long.toString(numeroIdentificacao); }

}
