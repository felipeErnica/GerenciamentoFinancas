package com.santacarolina.model.beans;

import com.santacarolina.dto.ExtratoDTO;
import com.santacarolina.interfaces.DTOConversible;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Extrato implements DTOConversible<Extrato, ExtratoDTO> {

    private long id;
    private LocalDate dataTransacao;
    private Long contaId;
    private ContaBancaria contaBancaria;
    private String catBancaria;
    private String descricao;
    private double valor;
    private boolean isConciliated;
    private List<Conciliacao> conciliacaoList;

    public Extrato() {
        dataTransacao = LocalDate.now();
        contaBancaria = new ContaBancaria();
        catBancaria = "";
        descricao = "";
        conciliacaoList = new ArrayList<>();
    }

    public long getId() { return id; }
    public LocalDate getDataTransacao() { return dataTransacao; }
    public ContaBancaria getContaBancaria() { return contaBancaria; }
    public String getCatBancaria() { return catBancaria; }
    public String getDescricao() { return descricao; }
    public double getValor() { return valor; }
    public boolean isConciliated() { return isConciliated; }
    public List<Conciliacao> getConciliacaoList() { return conciliacaoList; }

    public void setId(long id) { this.id = id; }
    public void setDataTransacao(LocalDate dataTransacao) { this.dataTransacao = dataTransacao; }
    public void setContaBancaria(ContaBancaria contaBancaria) { this.contaBancaria = contaBancaria; }
    public void setCatBancaria(String catBancaria) { this.catBancaria = catBancaria; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setValor(double valor) { this.valor = valor; }
    public void setConciliated(boolean conciliated) { isConciliated = conciliated; }
    public void setConciliacaoList(List<Conciliacao> conciliacaoList) { this.conciliacaoList = conciliacaoList; }

    @Override
    public Extrato returnNew() {
        return new Extrato();
    }

    @Override
    public Extrato fromDTO (ExtratoDTO e) {
        id = e.id();
        dataTransacao = LocalDate.parse(e.dataTransacao());
        contaId = e.contaId();
        catBancaria = e.categoriaExtrato();
        descricao = e.descricao();
        valor = e.valor();
        isConciliated = e.conciliado();
        return this;
    }

    @Override
    public ExtratoDTO toDTO() {
        return new ExtratoDTO(
                id,
                contaId,
                dataTransacao.toString(),
                null,
                catBancaria,
                descricao,
                valor,
                isConciliated
        );
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Extrato{");
        sb.append("id=").append(id);
        sb.append(", dataTransacao=").append(dataTransacao);
        sb.append(", catBancaria='").append(catBancaria).append('\'');
        sb.append(", descricao='").append(descricao).append('\'');
        sb.append(", valor=").append(valor);
        sb.append('}');
        return sb.toString();
    }
}
