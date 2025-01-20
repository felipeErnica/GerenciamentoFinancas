package com.santacarolina.model;

import java.time.LocalDate;
import java.util.List;

import com.santacarolina.dao.ContaDAO;
import com.santacarolina.exceptions.FetchFailException;

public class Extrato {

    private long id;
    private LocalDate dataTransacao;
    private long contaId;
    private ContaBancaria conta;
    private String categoriaExtrato;
    private String descricao;
    private double valor;
    private boolean conciliado;
    private List<Conciliacao> conciliacaoList;

    public long getId() { return id; }
    public LocalDate getDataTransacao() { return dataTransacao; }
    public String getCategoriaExtrato() { return categoriaExtrato; }
    public String getDescricao() { return descricao; }
    public double getValor() { return valor; }
    public boolean isConciliado() { return conciliado; }
    public List<Conciliacao> getConciliacaoList() { return conciliacaoList; }
    public long getContaId() { return contaId; }

    public ContaBancaria getConta() {
        try {
            if (conta == null && contaId != 0) conta = new ContaDAO().findById(contaId).orElse(null);
            return conta;
        } catch (FetchFailException e) {
            return conta;
        }
    }

    public void setId(long id) { this.id = id; }
    public void setDataTransacao(LocalDate dataTransacao) { this.dataTransacao = dataTransacao; }
    public void setCategoriaExtrato(String catBancaria) { this.categoriaExtrato = catBancaria; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setValor(double valor) { this.valor = valor; }
    public void setConciliado(boolean conciliated) { conciliado = conciliated; }
    public void setConciliacaoList(List<Conciliacao> conciliacaoList) { this.conciliacaoList = conciliacaoList; }

    public void setConta(ContaBancaria contaBancaria) {
        this.conta = contaBancaria;
        this.contaId = contaBancaria != null ? contaBancaria.getId() : 0;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Extrato{");
        sb.append("id=").append(id);
        sb.append(", dataTransacao=").append(dataTransacao);
        sb.append(", catBancaria='").append(categoriaExtrato).append('\'');
        sb.append(", descricao='").append(descricao).append('\'');
        sb.append(", valor=").append(valor);
        sb.append('}');
        return sb.toString();
    }

}
