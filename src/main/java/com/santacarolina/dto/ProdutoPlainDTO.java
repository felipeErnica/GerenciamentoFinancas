package com.santacarolina.dto;

import com.santacarolina.model.Produto;

public class ProdutoPlainDTO {

    private long id;
    private long documentoId;
    private long classificacaoId;
    private String descricao;
    private String und;
    private double quantidade;
    private double valorUnit;

    public ProdutoPlainDTO(ProdutoDTO prod) {
        this.id = prod.getId();
        this.documentoId = prod.getDocId();
        this.classificacaoId = prod.getClassificacaoId();
        this.descricao = prod.getDescricao();
        this.und = prod.getUnd();
        this.quantidade = prod.getQuantidade();
        this.valorUnit = prod.getValorUnit();
    }

    public ProdutoPlainDTO(Produto prod) {
        this.id = prod.getId();
        this.documentoId = prod.getDocumentoId();
        this.classificacaoId = prod.getClassificacaoId();
        this.descricao = prod.getDescricao();
        this.und = prod.getUnd();
        this.quantidade = prod.getQuantidade();
        this.valorUnit = prod.getValorUnit();
    }

    public long getId() { return id; }
    public long getDocumentoId() { return documentoId; }
    public long getClassificacaoId() { return classificacaoId; }
    public String getDescricao() { return descricao; }
    public String getUnd() { return und; }
    public double getQuantidade() { return quantidade; }
    public double getValorUnit() { return valorUnit; }

}

