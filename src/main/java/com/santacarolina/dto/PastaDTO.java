package com.santacarolina.dto;

import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.PastaContabil;

public class PastaDTO implements FromDTO<PastaContabil> {

    private long id;
    private String nome;
    private String caminhoPasta;
    private ContaDTO conta;

    public PastaDTO() {}

    public PastaDTO (PastaContabil p) {
        this.id = p.getId();
        this.nome = p.getNome();
        this.caminhoPasta = p.getCaminhoPasta();
        this.conta = new ContaDTO(p.getContaBancaria());
    }

    public long getId() { return id; }
    public String getNome() { return nome; }
    public String getCaminhoPasta() { return caminhoPasta; }
    public ContaDTO getConta() { return conta; }

    @Override
    public PastaContabil fromDTO() { return new PastaContabil(this); }

}
