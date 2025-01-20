package com.santacarolina.model;

import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.interfaces.Copiable;

public class CategoriaContabil implements Copiable<CategoriaContabil> {

    private long id;
    private FluxoCaixa fluxoCaixa;
    private String numeroCategoria;
    private String nome;

    public long getId() { return id; }
    public FluxoCaixa getFluxoCaixa() { return fluxoCaixa; }
    public String getNome() { return nome; }
    public String getNumeroCategoria() { return numeroCategoria; }

    public void setId(long id) { this.id = id; }
    public void setFluxoCaixa(FluxoCaixa fluxoCaixa) { this.fluxoCaixa = fluxoCaixa; }
    public void setNome(String nomeCategoria) { this.nome = nomeCategoria; }
    public void setNumeroCategoria(String numeroCategoria) { this.numeroCategoria = numeroCategoria; }

    @Override
    public String toString() { return numeroCategoria + " - " + nome; }

    @Override
    public CategoriaContabil generateCopy() {
        CategoriaContabil clone = new CategoriaContabil();
        clone.setId(id);
        clone.setNome(nome);
        clone.setNumeroCategoria(numeroCategoria);
        clone.setFluxoCaixa(fluxoCaixa);
        return clone;
    }

}

