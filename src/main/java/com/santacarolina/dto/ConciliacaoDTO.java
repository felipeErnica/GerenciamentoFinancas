package com.santacarolina.dto;

import com.santacarolina.enums.TipoMovimento;
import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.Conciliacao;

public class ConciliacaoDTO implements FromDTO<Conciliacao> {

    private long id;
    private TipoMovimento tipoMovimento;

    private DuplicataDTO duplicata;
    private ExtratoDTO extrato;

    public ConciliacaoDTO (Conciliacao c) {
        this.id = c.getId();
        this.tipoMovimento = c.getTipoMovimento();
        this.duplicata = new DuplicataDTO(c.getDuplicata());
        this.extrato = new ExtratoDTO(c.getExtrato());
    }

    public ConciliacaoDTO() { }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public TipoMovimento getTipoMovimento() { return tipoMovimento; }
    public void setTipoMovimento(TipoMovimento tipoMovimento) { this.tipoMovimento = tipoMovimento; }
    public DuplicataDTO getDuplicata() { return duplicata; }
    public void setDuplicata(DuplicataDTO duplicata) { this.duplicata = duplicata; }
    public ExtratoDTO getExtrato() { return extrato; }
    public void setExtrato(ExtratoDTO extrato) { this.extrato = extrato; }

    @Override
    public Conciliacao fromDTO() { return new Conciliacao(this); }

}
