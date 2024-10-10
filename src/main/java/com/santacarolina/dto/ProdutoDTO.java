package com.santacarolina.dto;

import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.Produto;

import java.time.LocalDate;

public class ProdutoDTO implements FromDTO<Produto> {

    private long id;
    private long docId;
    private long classificacaoId;
    private String descricao;
    private String und;
    private double quantidade;
    private double valorUnit;
    private LocalDate dataEmissao;
    private String nomePasta;
    private String nomeContato;
    private String classificacao;

    public ProdutoDTO (Produto p) {
        this.id = p.getId();
        this.docId = p.getDocumentoId();
        this.classificacaoId = p.getClassificacaoId();
        this.descricao = p.getDescricao();
        this.und = p.getUnd();
        this.quantidade = p.getQuantidade();
        this.valorUnit = p.getValorUnit();
    }

    public ProdutoDTO() {}

    public long getId() { return id; }
    public long getDocId() { return docId; }
    public long getClassificacaoId() { return classificacaoId; }
    public String getDescricao() { return descricao; }
    public String getUnd() { return und; }
    public double getQuantidade() { return quantidade; }
    public double getValorUnit() { return valorUnit; }
    public LocalDate getDataEmissao() { return dataEmissao; }
    public String getNomePasta() { return nomePasta; }
    public String getNomeContato() { return nomeContato; }
    public String getClassificacao() { return classificacao; }

    public Produto fromDTO() { return new Produto(this); }


}
