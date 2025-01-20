package com.santacarolina.model;

import com.santacarolina.dao.DuplicataDAO;
import com.santacarolina.dao.ExtratoDAO;
import com.santacarolina.enums.TipoMovimento;
import com.santacarolina.exceptions.FetchFailException;

public class Conciliacao {

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

    public Conciliacao() { }

    public long getId() { return id; }
    public TipoMovimento getTipoMovimento() { return tipoMovimento; }
    public Long getDuplicataId() { return duplicataId; }
    public long getExtratoId() { return extratoId; }

    public Duplicata getDuplicata() {
        try {
            if (duplicata == null && duplicataId != null) duplicata = new DuplicataDAO().findById(duplicataId).orElse(null);
        } catch (FetchFailException ignored) {}
        return duplicata;
    }

    public Extrato getExtrato() {
        try {
            if (extrato == null && extratoId != 0) extrato = new ExtratoDAO().findById(extratoId).orElse(null);
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

}
