package com.santacarolina.dto;

import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.Extrato;

import java.time.LocalDate;

public class ExtratoDTO implements FromDTO<Extrato> {

    private long id;
    private Long contaId;
    private LocalDate dataTransacao;
    private String contaBancaria;
    private String categoriaExtrato;
    private String descricao;
    private double valor;
    private boolean conciliado;

    public ExtratoDTO() {}

    public ExtratoDTO (Extrato e) {
        this.id = e.getId();
        this.contaId = e.getContaId();
        this.dataTransacao = e.getDataTransacao();
        this.categoriaExtrato = e.getCatBancaria();
        this.descricao = e.getDescricao();
        this.valor = e.getValor();
        this.conciliado = e.isConciliated();
    }

    public long getId() { return id; }
    public Long getContaId() { return contaId; }
    public LocalDate getDataTransacao() { return dataTransacao; }
    public String getContaBancaria() { return contaBancaria; }
    public String getCategoriaExtrato() { return categoriaExtrato; }
    public String getDescricao() { return descricao; }
    public double getValor() { return valor; }
    public boolean isConciliado() { return conciliado; }

    @Override
    public Extrato fromDTO() { return new Extrato(this); }

}
