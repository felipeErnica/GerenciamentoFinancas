package com.santacarolina.model.beans;

import com.santacarolina.dto.ConciliacaoDTO;
import com.santacarolina.enums.TipoMovimento;
import com.santacarolina.interfaces.DTOConversible;

public class Conciliacao implements DTOConversible<Conciliacao, ConciliacaoDTO> {

    private long id;
    private TipoMovimento tipoMovimento;
    private Long duplicataId;
    private Duplicata duplicata;
    private long extratoId;
    private Extrato extrato;

    public Conciliacao(Duplicata duplicata, Extrato extrato) {
        this.duplicata = duplicata;
        this.extrato = extrato;
        this.tipoMovimento = TipoMovimento.COMUM;
    }

    public Conciliacao(Extrato extrato, TipoMovimento tipoMovimento) {
        this.extrato = extrato;
        this.tipoMovimento = tipoMovimento;
    }

    public Conciliacao() {
    }

    public long getId() { return id; }
    public Duplicata getDuplicata() { return duplicata; }
    public Extrato getExtrato() { return extrato; }
    public void setDuplicata(Duplicata duplicata) { this.duplicata = duplicata; }
    public void setExtrato(Extrato extrato) { this.extrato = extrato; }

    @Override
    public Conciliacao returnNew() { return new Conciliacao(); }

    @Override
    public Conciliacao fromDTO(ConciliacaoDTO dto) {
        id = dto.id();
        tipoMovimento = dto.tipoMovimento();
        duplicataId = dto.duplicataId();
        extratoId = dto.extratoId();
        return this;
    }

    @Override
    public ConciliacaoDTO toDTO () {
        return new ConciliacaoDTO(
                id,
                tipoMovimento,
                duplicataId,
                extrato.getId());
    }

}
