package com.santacarolina.dto;

import java.time.LocalDate;

import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.Produto;

public class ProdutoDTO implements FromDTO<Produto> {

    private long id;
    private DocumentoDTO documento;
    private ClassificacaoDTO classificacao;
    private String descricao;
    private String und;
    private double quantidade;
    private double valorUnit;
    private LocalDate dataEmissao;

    public ProdutoDTO (Produto p) {
        this.id = p.getId();
        this.documento = new DocumentoDTO(p.getDocumento());
        this.classificacao = p.getClassificacao() != null ? p.getClassificacao().toDTO() : null;
        this.descricao = p.getDescricao();
        this.und = p.getUnd();
        this.quantidade = p.getQuantidade();
        this.valorUnit = p.getValorUnit();
    }

    public ProdutoDTO() {}

    public long getId() { return id; }
    public String getDescricao() { return descricao; }
    public String getUnd() { return und; }
    public double getQuantidade() { return quantidade; }
    public double getValorUnit() { return valorUnit; }
    public LocalDate getDataEmissao() { return dataEmissao; }
    public DocumentoDTO getDocumento() { return documento; }
    public ClassificacaoDTO getClassificacao() { return classificacao; }

    public Produto fromDTO() { return new Produto(this); }

}
