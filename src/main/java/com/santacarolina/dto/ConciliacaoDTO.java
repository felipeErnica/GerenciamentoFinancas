package com.santacarolina.dto;

import java.time.LocalDate;

import com.santacarolina.enums.TipoMovimento;
import com.santacarolina.enums.TipoPagamento;
import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.Conciliacao;

public class ConciliacaoDTO implements FromDTO<Conciliacao> {

    private long id;
    private TipoMovimento tipoMovimento;
    private Long duplicataId;
    private LocalDate dataVencimento;
    private TipoPagamento tipoPagamento;
    private double valorDuplicata;
    private long pastaId;
    private String nomePasta;
    private long contaId;
    private String contaBancaria;
    private long extratoId;
    private LocalDate dataExtrato;
    private String descExtrato;
    private String categoriaExtrato;
    private double valorExtrato;


    public ConciliacaoDTO (Conciliacao c) {
        this.id = c.getId();
        this.tipoMovimento = c.getTipoMovimento();
        this.duplicataId = c.getDuplicataId();
        this.extratoId = c.getExtratoId();
    }

    public ConciliacaoDTO() { }

    public long getId() { return id; }
    public TipoMovimento getTipoMovimento() { return tipoMovimento; }
    public Long getDuplicataId() { return duplicataId; }
    public long getExtratoId() { return extratoId; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public TipoPagamento getTipoPagamento() { return tipoPagamento; }
    public double getValorDuplicata() { return valorDuplicata; }
    public long getPastaId() { return pastaId; }
    public String getNomePasta() { return nomePasta; }
    public long getContaId() { return contaId; }
    public String getContaBancaria() { return contaBancaria; }
    public LocalDate getDataExtrato() { return dataExtrato; }
    public String getDescExtrato() { return descExtrato; }
    public String getCategoriaExtrato() { return categoriaExtrato; }
    public double getValorExtrato() { return valorExtrato; }

    @Override
    public Conciliacao fromDTO() { return new Conciliacao(this); }

}
