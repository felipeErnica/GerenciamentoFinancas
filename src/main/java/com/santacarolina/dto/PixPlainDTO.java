package com.santacarolina.dto;

import com.santacarolina.enums.TipoPix;
import com.santacarolina.model.ChavePix;

public class PixPlainDTO {

    private long id;
    private long contatoId;
    private Long dadoId;
    private TipoPix tipoPix;
    private String chave;

    public PixPlainDTO(ChavePix pix) {
        this.id = pix.getId();
        this.contatoId = pix.getContatoId();
        this.dadoId = pix.getDadoId();
        this.tipoPix = pix.getTipoPix();
        this.chave = pix.getChave();
    }

    public long getId() { return id; }
    public long getContatoId() { return contatoId; }
    public Long getDadoId() { return dadoId; }
    public TipoPix getTipoPix() { return tipoPix; }
    public String getChave() { return chave; }

}
