package com.santacarolina.model;

import com.santacarolina.dto.ClassificacaoDTO;
import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.interfaces.ToDTO;

public class ClassificacaoContabil implements ToDTO<ClassificacaoDTO> {

    private long id;
    private FluxoCaixa fluxoCaixa;
    private long numeroIdentificacao;
    private String nomeClassificacao;

    public ClassificacaoContabil() { }

    public ClassificacaoContabil (ClassificacaoDTO dto) {
        this.id = dto.getId();
        this.fluxoCaixa = dto.getFluxoCaixa();
        this.numeroIdentificacao = dto.getNumeroIdentificacao();
        this.nomeClassificacao = dto.getNomeClassificacao();
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

    @Override
    public ClassificacaoDTO toDTO() { return new ClassificacaoDTO(this); }

}
