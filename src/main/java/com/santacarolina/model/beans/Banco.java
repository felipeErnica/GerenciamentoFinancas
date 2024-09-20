package com.santacarolina.model.beans;

import com.santacarolina.dto.BancoDTO;
import com.santacarolina.interfaces.DTOConversible;

public class Banco implements DTOConversible<Banco, BancoDTO> {
    private long id;
    private String nomeBanco;
    private String apelidoBanco;

    public Banco() {
        nomeBanco = "";
        apelidoBanco = "";
    }

    public long getId() { return id; }
    public String getNomeBanco() { return nomeBanco; }
    public String getApelidoBanco() { return apelidoBanco; }

    public void setId(long id) { this.id = id; }
    public void setNomeBanco(String nomeBanco) { this.nomeBanco = nomeBanco; }
    public void setApelidoBanco(String apelidoBanco) { this.apelidoBanco = apelidoBanco; }

    @Override
    public String toString() {
        if (this.apelidoBanco != null && !this.apelidoBanco.isEmpty()) return apelidoBanco + " - " + nomeBanco;
        else return nomeBanco;
    }

    @Override
    public Banco returnNew() { return new Banco(); }

    @Override
    public Banco fromDTO(BancoDTO bancoDTO) {
        id = bancoDTO.id();
        nomeBanco = bancoDTO.nomeBanco();
        apelidoBanco = bancoDTO.apelidoBanco();
        return this;
    }

    @Override
    public BancoDTO toDTO() { return new BancoDTO(id, nomeBanco, apelidoBanco); }
}
