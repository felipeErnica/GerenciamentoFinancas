package com.santacarolina.dto;

import com.santacarolina.enums.TipoMovimento;
import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.Conciliacao;

import java.util.Optional;

public class ConciliacaoDTO implements FromDTO<Conciliacao> {

    private long id;
    private TipoMovimento tipoMovimento;
    private Long  duplicataId;
    private long extratoId;

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

    @Override
    public Conciliacao fromDTO() { return new Conciliacao(this); }

}
