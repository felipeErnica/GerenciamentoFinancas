package com.santacarolina.dto;

import com.santacarolina.enums.TipoPix;
import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.ChavePix;

public class PixDTO implements FromDTO<ChavePix> {

    private long id;
    private long contatoId;
    private Long dadoId;
    private TipoPix tipoPix;
    private String chave;
    private String nomeBanco;
    private String agencia;
    private String numeroConta;

    public PixDTO() { }

    public PixDTO (ChavePix c) {
        this.id = c.getId();
        this.contatoId = c.getContatoId();
        this.dadoId = c.getDadoId();
        this.tipoPix = c.getTipoPix();
        this.chave = c.getChave();
    }

    public long getId() { return id; }
    public long getContatoId() { return contatoId; }
    public Long getDadoId() { return dadoId; }
    public TipoPix getTipoPix() { return tipoPix; }
    public String getChave() { return chave; }
    public String getNomeBanco() { return nomeBanco; }
    public String getAgencia() { return agencia; }
    public String getNumeroConta() { return numeroConta; }

    @Override
    public ChavePix fromDTO() { return new ChavePix(this); }

}