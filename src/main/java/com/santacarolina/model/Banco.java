package com.santacarolina.model;

import com.santacarolina.dto.BancoDTO;
import com.santacarolina.interfaces.Copiable;
import com.santacarolina.interfaces.ToDTO;

public class Banco implements ToDTO<BancoDTO>, Copiable<Banco> {

    private long id;
    private String nomeBanco;
    private String apelidoBanco;

    public Banco() { }

    public Banco (BancoDTO b) {
        this.id = b.getId();
        this.nomeBanco = b.getNomeBanco();
        this.apelidoBanco = b.getApelidoBanco();
    }

    public long getId() { return id; }
    public String getNomeBanco() { return nomeBanco; }
    public String getApelidoBanco() { return apelidoBanco; }

    public void setId(long id) { this.id = id; }
    public void setNomeBanco(String nomeBanco) { this.nomeBanco = nomeBanco; }
    public void setApelidoBanco(String apelidoBanco) { this.apelidoBanco = apelidoBanco; }

    @Override
    public String toString() { return nomeBanco; }

    @Override
    public BancoDTO toDTO() { return new BancoDTO(this); }

    public String print() {
        return "Banco{id=" + id + ", nomeBanco=" + nomeBanco + ", apelidoBanco=" + apelidoBanco + "}";
    }

    @Override
    public Banco generateCopy() {
        Banco clone = new Banco();
        clone.setId(id);
        clone.setNomeBanco(nomeBanco);
        clone.setApelidoBanco(apelidoBanco);
        return clone;
    }

}
