package com.santacarolina.model;

import com.santacarolina.dao.DuplicataDAO;
import com.santacarolina.dao.ExtratoDAO;
import com.santacarolina.dto.ConciliacaoDTO;
import com.santacarolina.enums.TipoMovimento;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ToDTO;

public class Conciliacao implements ToDTO<ConciliacaoDTO> {

    private static final DuplicataDAO duplicataDAO = new DuplicataDAO();
    private static final ExtratoDAO extratoDAO = new ExtratoDAO();

    private long id;
    private TipoMovimento tipoMovimento;
    private Long duplicataId;
    private Duplicata duplicata;
    private long extratoId;
    private Extrato extrato;

    public Conciliacao (ConciliacaoDTO dto) {
        this.id = dto.getId();
        this.tipoMovimento = dto.getTipoMovimento();
        this.duplicataId = dto.getDuplicataId();
        this.extratoId = dto.getExtratoId();
    }

    public Conciliacao(Duplicata duplicata, Extrato extrato) {
        this.duplicata = duplicata;
        this.extrato = extrato;
        this.tipoMovimento = TipoMovimento.COMUM;
    }

    public Conciliacao(Extrato extrato, TipoMovimento tipoMovimento) {
        this.extrato = extrato;
        this.tipoMovimento = tipoMovimento;
    }

    public Conciliacao() { }

    public long getId() { return id; }
    public TipoMovimento getTipoMovimento() { return tipoMovimento; }
    public Long getDuplicataId() { return duplicataId; }
    public long getExtratoId() { return extratoId; }

    public Duplicata getDuplicata() {
        try {
            if (duplicata == null && duplicataId != null) duplicata = duplicataDAO.findById(duplicataId).orElse(null);
        } catch (FetchFailException ignored) {}
        return duplicata;
    }

    public Extrato getExtrato() {
        try {
            if (extrato == null) extrato = extratoDAO.findById(extratoId).orElse(null);
        } catch (FetchFailException ignored) {}
        return extrato; 
    }

    public void setDuplicata(Duplicata duplicata) {
        this.duplicata = duplicata;
        this.duplicataId = duplicata != null ? duplicata.getId() : null;
    }

    public void setExtrato(Extrato extrato) {
        this.extrato = extrato;
        this.extratoId = extrato != null ? extrato.getId() : 0;
    }

    @Override
    public ConciliacaoDTO toDTO () { return new ConciliacaoDTO(this); }

}
