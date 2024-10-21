package com.santacarolina.model;

import com.santacarolina.dao.CategoriaDAO;
import com.santacarolina.dto.ClassificacaoDTO;
import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ToDTO;

public class ClassificacaoContabil implements ToDTO<ClassificacaoDTO> {

    private long id;
    private CategoriaContabil categoriaContabil;
    private long categoriaId;
    private FluxoCaixa fluxoCaixa;
    private long numeroIdentificacao;
    private String nomeClassificacao;

    public ClassificacaoContabil() { }

    public ClassificacaoContabil (ClassificacaoDTO dto) {
        this.id = dto.getId();
        this.fluxoCaixa = dto.getFluxoCaixa();
        this.categoriaId = dto.getCategoriaId();
        this.numeroIdentificacao = dto.getNumeroIdentificacao();
        this.nomeClassificacao = dto.getNomeClassificacao();
    }

    public long getId() { return id; }
    public FluxoCaixa getFluxoCaixa() { return fluxoCaixa; }
    public String getNomeClassificacao() { return nomeClassificacao; }
    public long getNumeroIdentificacao() { return numeroIdentificacao; }
    public long getCategoriaId() { return categoriaId; }

    public CategoriaContabil getCategoriaContabil() {
        try {
            if (categoriaId != 0) categoriaContabil = new CategoriaDAO().findById(categoriaId).orElse(null);
        } catch (FetchFailException e) {}
        return categoriaContabil;
    }

    public void setId(long id) { this.id = id; }
    public void setFluxoCaixa(FluxoCaixa fluxoCaixa) { this.fluxoCaixa = fluxoCaixa; }
    public void setNumeroIdentificacao(long numeroIdentificacao) { this.numeroIdentificacao = numeroIdentificacao; }
    public void setNomeClassificacao(String nomeClassificacao) { this.nomeClassificacao = nomeClassificacao; }
    public void setCategoriaId(long categoriaId) { this.categoriaId = categoriaId; }

    public void setCategoriaContabil(CategoriaContabil categoriaContabil) {
        this.categoriaContabil = categoriaContabil;
        this.categoriaId = categoriaContabil != null ? categoriaContabil.getId() : 0;
    }

    @Override
    public String toString() { return Long.toString(numeroIdentificacao); }

    @Override
    public ClassificacaoDTO toDTO() { return new ClassificacaoDTO(this); }

}
