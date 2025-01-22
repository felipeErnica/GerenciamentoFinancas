package com.santacarolina.dto;

import com.santacarolina.model.ClassificacaoContabil;
import com.santacarolina.model.Produto;

public class ProdutoDTO {

    private long id;
    private ClassificacaoContabil classificacao;
    private String descricao;
    private String und;
    private double quantidade;
    private double valorUnit;

    public ProdutoDTO() {}

    public ProdutoDTO(Produto produto) {
        this.id = produto.getId();
        this.classificacao = produto.getClassificacao();
        this.descricao = produto.getDescricao();
        this.und = produto.getUnd();
        this.quantidade = produto.getQuantidade();
        this.valorUnit = produto.getValorUnit();
    }

    public long getId() { return id; }
    public String getDescricao() { return descricao; }
    public String getUnd() { return und; }
    public double getQuantidade() { return quantidade; }
    public double getValorUnit() { return valorUnit; }
    public double getValorTotal() { return valorUnit*quantidade; }
    public ClassificacaoContabil getClassificacao() { return classificacao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setUnd(String und) { this.und = und; }
    public void setQuantidade(double quantidade) { this.quantidade = quantidade; }
    public void setValorUnit(double valorUnit) { this.valorUnit = valorUnit; }
    public void setClassificacao(ClassificacaoContabil classificacao) { this.classificacao = classificacao; }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Produto{");
        sb.append("id=").append(id);
        sb.append(", classificacao=").append(classificacao);
        sb.append(", descricao='").append(descricao).append('\'');
        sb.append(", und='").append(und).append('\'');
        sb.append(", quantidade=").append(quantidade);
        sb.append(", valorUnit=").append(valorUnit);
        sb.append('}');
        return sb.toString();
    }

}
