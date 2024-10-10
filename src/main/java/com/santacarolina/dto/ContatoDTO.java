package com.santacarolina.dto;

import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.Contato;

public class ContatoDTO implements FromDTO<Contato> {

    private long id;
    private String nome;
    private String cpf;
    private String cnpj;
    private String ie;

    public ContatoDTO (Contato c) {
        this.id = c.getId();
        this.nome = c.getNome();
        this.cpf = c.getCpf();
        this.cnpj = c.getCnpj();
        this.ie = c.getIe();
    }

    public ContatoDTO() {}

    public long getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getCnpj() { return cnpj; }
    public String getIe() { return ie; }

    @Override
    public Contato fromDTO() { return new Contato(this); }

}
