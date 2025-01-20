package com.santacarolina.model;

import java.time.LocalDate;
import java.util.List;

import com.santacarolina.dao.ContaDAO;
import com.santacarolina.dto.ExtratoDTO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ToDTO;

public class Extrato implements ToDTO<ExtratoDTO> {

    private long id;
    private LocalDate dataTransacao;
    private long contaId;
    private ContaBancaria contaBancaria;
    private String catBancaria;
    private String descricao;
    private double valor;
    private boolean isConciliated;
    private List<Conciliacao> conciliacaoList;

    public Extrato() {}

    public Extrato (ExtratoDTO dto) {
        this.id = dto.getId();
        this.dataTransacao = dto.getDataTransacao();
        this.contaBancaria = new ContaBancaria(dto.getConta());
        this.catBancaria = dto.getCategoriaExtrato();
        this.descricao = dto.getDescricao();
        this.valor = dto.getValor();
        this.isConciliated = dto.isConciliado();
    }

    public long getId() { return id; }
    public LocalDate getDataTransacao() { return dataTransacao; }
    public String getCatBancaria() { return catBancaria; }
    public String getDescricao() { return descricao; }
    public double getValor() { return valor; }
    public boolean isConciliated() { return isConciliated; }
    public List<Conciliacao> getConciliacaoList() { return conciliacaoList; }
    public long getContaId() { return contaId; }

    public ContaBancaria getContaBancaria() {
        try {
            if (contaBancaria == null) contaBancaria = new ContaDAO().findById(contaId).orElse(null);
            return contaBancaria;
        } catch (FetchFailException e) {
            return contaBancaria;
        }
    }

    public void setId(long id) { this.id = id; }
    public void setDataTransacao(LocalDate dataTransacao) { this.dataTransacao = dataTransacao; }
    public void setCatBancaria(String catBancaria) { this.catBancaria = catBancaria; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setValor(double valor) { this.valor = valor; }
    public void setConciliated(boolean conciliated) { isConciliated = conciliated; }
    public void setConciliacaoList(List<Conciliacao> conciliacaoList) { this.conciliacaoList = conciliacaoList; }

    public void setContaBancaria(ContaBancaria contaBancaria) {
        this.contaBancaria = contaBancaria;
        this.contaId = contaBancaria != null ? contaBancaria.getId() : 0;
    }

    @Override
    public ExtratoDTO toDTO() { return new ExtratoDTO(this); }

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
