package com.santacarolina.dto;

import com.santacarolina.dao.ClassificacaoDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.Produto;

import java.time.LocalDate;

public class ProdutoDTO implements FromDTO<Produto> {

    private long id;
    private long docId;
    private long classificacaoId;
    private ClassificacaoDTO classificacao;
    private String descricao;
    private String und;
    private double quantidade;
    private double valorUnit;
    private LocalDate dataEmissao;
    private long pastaId;
    private String nomePasta;
    private long emissorId;
    private String nomeContato;
    private String nomeClassificacao;

    public ProdutoDTO (Produto p) {
        this.id = p.getId();
        this.docId = p.getDocumentoId();
        this.classificacaoId = p.getClassificacaoId();
        this.classificacao = p.getClassificacao() != null ? p.getClassificacao().toDTO() : null;
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
    public String getNomeClassificacao() { return nomeClassificacao; }
    public long getPastaId() { return pastaId; }
    public long getEmissorId() { return emissorId; }

    public ClassificacaoDTO getClassificacao() {
        if (classificacaoId != 0) {
            try {
                classificacao = new ClassificacaoDAO().findByIdDTO(classificacaoId).orElse(null);
            } catch (FetchFailException e) {
                classificacao = null;
            }
        }
        return classificacao; 
    }

    public Produto fromDTO() { return new Produto(this); }

}
