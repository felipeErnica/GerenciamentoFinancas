package com.santacarolina.model.beans;

import com.santacarolina.dto.DocumentoDTO;
import com.santacarolina.dto.ProdutoDTO;
import com.santacarolina.interfaces.DTOConversible;

public class Produto implements DTOConversible<Produto, ProdutoDTO> {

    private long id;
    private long documentoId;
    private DocumentoFiscal documento;
    private long classificacaoId;
    private ClassificacaoContabil classificacao;
    private String descricao;
    private String und;
    private double quantidade;
    private double valorUnit;

    public Produto() {
        documento = new DocumentoFiscal();
        classificacao = new ClassificacaoContabil();
        descricao = "";
        und ="";
    }

    public Produto(ProdutoBuilder builder) {
        this.descricao = builder.descricao;
        this.und = builder.und;
        this.quantidade = builder.quantidade;
        this.valorUnit = builder.valorUnit;
    }

    @Override
    public Produto returnNew() { return new Produto(); }

    @Override
    public Produto fromDTO (ProdutoDTO p) {
        id = p.id();
        documentoId = p.docId();
        classificacaoId = p.classificacaoId();
        descricao = p.descricao();
        und = p.und();
        quantidade = p.quantidade();
        valorUnit = p.valorUnit();
        return this;
    }

    @Override
    public ProdutoDTO toDTO() {
        return new ProdutoDTO(
                id,
                documentoId,
                classificacaoId,
                descricao,
                und,
                quantidade,
                valorUnit,
                null,
                null,
                null,
                null);
    }

    public long getId() { return id; }
    public DocumentoFiscal getDocumento() { return documento; }
    public ClassificacaoContabil getClassificacao() { return classificacao; }
    public String getDescricao() { return descricao; }
    public String getUnd() { return und; }
    public double getQuantidade() { return quantidade; }
    public double getValorUnit() { return valorUnit; }
    public double getValorTotal() { return valorUnit*quantidade; }

    public void setClassificacao(ClassificacaoContabil classificacao) { this.classificacao = classificacao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setUnd(String und) { this.und = und; }
    public void setQuantidade(double quantidade) { this.quantidade = quantidade; }
    public void setValorUnit(double valorUnit) { this.valorUnit =documento.isExpense() ? Math.abs(valorUnit)*-1 : Math.abs(valorUnit); }
    public void setDocumento(DocumentoFiscal documento) { this.documento = documento; }

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

    public static class ProdutoBuilder{

        private String descricao;
        private String und;
        private double quantidade;
        private double valorUnit;

        public ProdutoBuilder setDescricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public ProdutoBuilder setUnd(String und) {
            this.und = und;
            return this;
        }

        public ProdutoBuilder setQuantidade(double quantidade) {
            this.quantidade = quantidade;
            return this;
        }

        public ProdutoBuilder setValorUnit(double valorUnit) {
            this.valorUnit = valorUnit;
            return this;
        }

        public Produto build(){
            return  new Produto(this);
        }

    }
}

