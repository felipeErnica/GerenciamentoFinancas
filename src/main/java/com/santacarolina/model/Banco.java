package com.santacarolina.model;

import com.santacarolina.interfaces.Copiable;

public class Banco implements Copiable<Banco> {

    private long id;
    private String nomeBanco;
    private String apelidoBanco;

    public long getId() { return id; }
    public String getNomeBanco() { return nomeBanco; }
    public String getApelidoBanco() { return apelidoBanco; }

    public void setId(long id) { this.id = id; }
    public void setNomeBanco(String nomeBanco) { this.nomeBanco = nomeBanco; }
    public void setApelidoBanco(String apelidoBanco) { this.apelidoBanco = apelidoBanco; }

    @Override
    public String toString() { return nomeBanco; }

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
