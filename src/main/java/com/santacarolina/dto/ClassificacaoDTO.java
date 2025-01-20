package com.santacarolina.dto;

import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.ClassificacaoContabil;

public class ClassificacaoDTO implements FromDTO<ClassificacaoContabil> {

    private long id;
    private long categoriaId;
    private CategoriaDTO categoriaDTO;
    private String numeroIdentificacao;
    private String nomeClassificacao;

    public ClassificacaoDTO() { }

    public ClassificacaoDTO (ClassificacaoContabil c) {
        this.id = c.getId();
        this.categoriaId = c.getCategoriaId();
        this.numeroIdentificacao = c.getNumeroIdentificacao();
        this.nomeClassificacao = c.getNomeClassificacao();
    }

    public long getId() { return id; }
    public long getCategoriaId() { return categoriaId; }
    public String getNumeroIdentificacao() { return numeroIdentificacao; }
    public String getNomeClassificacao() { return nomeClassificacao; }

    @Override
    public ClassificacaoContabil fromDTO() { return new ClassificacaoContabil(this); }

    public void setId(long id) { this.id = id; }
    public void setCategoriaId(long categoriaId) { this.categoriaId = categoriaId; }
    public CategoriaDTO getCategoriaDTO() { return categoriaDTO; }
    public void setCategoriaDTO(CategoriaDTO categoriaDTO) { this.categoriaDTO = categoriaDTO; }
    public void setNumeroIdentificacao(String numeroIdentificacao) { this.numeroIdentificacao = numeroIdentificacao; }
    public void setNomeClassificacao(String nomeClassificacao) { this.nomeClassificacao = nomeClassificacao; }

}
