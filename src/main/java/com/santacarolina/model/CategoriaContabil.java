package com.santacarolina.model;

import com.santacarolina.dto.CategoriaDTO;
import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.interfaces.Copiable;
import com.santacarolina.interfaces.ToDTO;

public class CategoriaContabil implements ToDTO<CategoriaDTO>, Copiable<CategoriaContabil> {

    private long id;
    private FluxoCaixa fluxoCaixa;
    private String numeroCategoria;
    private String nome;

    public CategoriaContabil() { }

    public CategoriaContabil(CategoriaDTO dto) {
        this.id = dto.getId();
        this.fluxoCaixa = dto.getFluxoCaixa();
        this.numeroCategoria = dto.getNumeroCategoria();
        this.nome = dto.getNome();
    }

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
    public CategoriaDTO toDTO() { return new CategoriaDTO(this); }

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

