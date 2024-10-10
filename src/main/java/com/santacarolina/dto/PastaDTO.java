package com.santacarolina.dto;

import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.PastaContabil;

public class PastaDTO implements FromDTO<PastaContabil> {

    private long id;
    private String nome;
    private String caminhoPasta;
    private long contaId;

    public PastaDTO() {}

    public PastaDTO (PastaContabil p) {
        this.id = p.getId();
        this.nome = p.getNome();
        this.caminhoPasta = p.getCaminhoPasta();
        this.contaId = p.getContaId();
    }

    public long getId() { return id; }
    public String getNome() { return nome; }
    public String getCaminhoPasta() { return caminhoPasta; }
    public long getContaId() { return contaId; }

    @Override
    public PastaContabil fromDTO() { return new PastaContabil(this); }

}
