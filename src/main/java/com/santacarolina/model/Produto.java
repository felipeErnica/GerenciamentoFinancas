package com.santacarolina.model;

import com.santacarolina.dao.ClassificacaoDAO;
import com.santacarolina.dao.DocumentoDAO;
import com.santacarolina.exceptions.FetchFailException;

public class Produto implements Comparable<Produto> {

    private long id;
    private long documentoId;
    private DocumentoFiscal documento;
    private long classificacaoId;
    private ClassificacaoContabil classificacao;
    private String descricao;
    private String und;
    private double quantidade;
    private double valorUnit;

    public long getId() { return id; }
    public String getDescricao() { return descricao; }
    public String getUnd() { return und; }
    public double getQuantidade() { return quantidade; }
    public double getValorUnit() { return valorUnit; }
    public double getValorTotal() { return valorUnit*quantidade; }
    public long getDocumentoId() { return documentoId; }
    public long getClassificacaoId() { return classificacaoId; }

    public ClassificacaoContabil getClassificacao() {
        try {
            if (classificacao == null && classificacaoId != 0) classificacao = new ClassificacaoDAO().findById(classificacaoId).orElse(null);
        } catch (FetchFailException ignored) {}
        return classificacao;
    }

    public DocumentoFiscal getDocumento() {
        try {
            if (documento == null && documentoId != 0) documento = new DocumentoDAO().findById(documentoId).orElse(null);
        } catch (FetchFailException ignored) {}
        return documento;
    }

    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setUnd(String und) { this.und = und; }
    public void setQuantidade(double quantidade) { this.quantidade = quantidade; }
    public void setValorUnit(double valorUnit) { this.valorUnit = valorUnit; }

    public void setDocumento(DocumentoFiscal documento) {
        this.documento = documento;
        this.documentoId = documento.getId();
    }

    public void setClassificacao(ClassificacaoContabil classificacao) {
        this.classificacao = classificacao;
        this.classificacaoId = classificacao != null ? classificacao.getId() : 0;
    }

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

    @Override
    public int compareTo(Produto o) { return Long.compare(o.classificacaoId, this.classificacaoId); }

}

