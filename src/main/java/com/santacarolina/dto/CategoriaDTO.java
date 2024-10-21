package com.santacarolina.dto;

import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.CategoriaContabil;

public class CategoriaDTO implements FromDTO<CategoriaContabil>{

    private long id;
    private FluxoCaixa fluxoCaixa;
    private String numeroCategoria;
    private String nome;

    public CategoriaDTO() { }

    public CategoriaDTO(CategoriaContabil categoriaContabil) {
        this.id = categoriaContabil.getId();
        this.fluxoCaixa = categoriaContabil.getFluxoCaixa();
        this.nome = categoriaContabil.getNome();
    }

    public long getId() { return id; }
    public FluxoCaixa getFluxoCaixa() { return fluxoCaixa; }
    public String getNome() { return nome; }
    public String getNumeroCategoria() { return numeroCategoria; }

    public void setId(long id) { this.id = id; }
    public void setFluxoCaixa(FluxoCaixa fluxoCaixa) { this.fluxoCaixa = fluxoCaixa; }
    public void setNome(String nome) { this.nome = nome; }
    public void setNumeroCategoria(String numeroCategoria) { this.numeroCategoria = numeroCategoria; }

    @Override
    public CategoriaContabil fromDTO() { return new CategoriaContabil(this); }

}

