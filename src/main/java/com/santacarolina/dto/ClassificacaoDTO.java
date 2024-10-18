package com.santacarolina.dto;

import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.ClassificacaoContabil;

public class ClassificacaoDTO implements FromDTO<ClassificacaoContabil> {

    private long id;
    private long categoriaId;
    private String nomeCategoria;
    private FluxoCaixa fluxoCaixa;
    private long numeroIdentificacao;
    private String nomeClassificacao;

    public ClassificacaoDTO() { }

    public ClassificacaoDTO (ClassificacaoContabil c) {
        this.id = c.getId();
        this.fluxoCaixa = c.getFluxoCaixa();
        this.numeroIdentificacao = c.getNumeroIdentificacao();
        this.nomeClassificacao = c.getNomeClassificacao();
    }

    public long getId() { return id; }
    public long getCategoriaId() { return categoriaId; }
    public FluxoCaixa getFluxoCaixa() { return fluxoCaixa; }
    public long getNumeroIdentificacao() { return numeroIdentificacao; }
    public String getNomeClassificacao() { return nomeClassificacao; }
    public String getNomeCategoria() { return nomeCategoria; }

    @Override
    public ClassificacaoContabil fromDTO() { return new ClassificacaoContabil(this); }

}
