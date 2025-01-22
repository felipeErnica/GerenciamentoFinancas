package com.santacarolina.model;

import com.santacarolina.dao.CategoriaDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.Copiable;

public class ClassificacaoContabil implements Copiable<ClassificacaoContabil> {

    private long id;
    private CategoriaContabil categoria;
    private long categoriaId;
    private String numeroIdentificacao;
    private String nomeClassificacao;

    public ClassificacaoContabil() {}

    public long getId() { return id; }
    public String getNomeClassificacao() { return nomeClassificacao; }
    public String getNumeroIdentificacao() { return numeroIdentificacao; }
    public long getCategoriaId() { return categoriaId; }

    public CategoriaContabil getCategoria() {
        try {
            if (categoria == null && categoriaId != 0) categoria = new CategoriaDAO().findById(categoriaId).orElse(null);
        } catch (FetchFailException e) {}
        return categoria;
    }

    public void setId(long id) { this.id = id; }
    public void setNumeroIdentificacao(String numeroIdentificacao) { this.numeroIdentificacao = numeroIdentificacao; }
    public void setNomeClassificacao(String nomeClassificacao) { this.nomeClassificacao = nomeClassificacao; }
    public void setCategoriaId(long categoriaId) { this.categoriaId = categoriaId; }

    public void setCategoria(CategoriaContabil categoriaContabil) {
        this.categoria = categoriaContabil;
        this.categoriaId = categoriaContabil != null ? categoriaContabil.getId() : 0;
    }

    @Override
    public String toString() { return numeroIdentificacao; }

    @Override
    public ClassificacaoContabil generateCopy() {
        ClassificacaoContabil copy = new ClassificacaoContabil();
        copy.setId(id);
        copy.setCategoriaId(categoriaId);
        copy.setNomeClassificacao(nomeClassificacao);
        copy.setNumeroIdentificacao(numeroIdentificacao);
        return copy;
    }

}
