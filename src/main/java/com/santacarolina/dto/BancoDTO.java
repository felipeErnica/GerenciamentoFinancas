package com.santacarolina.dto;

import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.Banco;

public class BancoDTO implements FromDTO<Banco> {

    private long id;
    private String nomeBanco;
    private String apelidoBanco;

    public BancoDTO (Banco b) {
        this.id = b.getId();
        this.nomeBanco = b.getNomeBanco();
        this.apelidoBanco = b.getApelidoBanco();
    }

    public BancoDTO() { }

    public long getId() { return id; }
    public String getNomeBanco() { return nomeBanco; }
    public String getApelidoBanco() { return apelidoBanco; }

    @Override
    public Banco fromDTO() { return new Banco(this); }

}
