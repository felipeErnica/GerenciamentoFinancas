package com.santacarolina.dto;

import com.santacarolina.model.DadoBancario;

public class DadoPlainDTO {

    private long id;
    private String agencia;
    private long bancoId;
    private String numeroConta;
    private long contatoId;

    public DadoPlainDTO(DadoBancario dado) {
        this.id = dado.getId();
        this.agencia = dado.getAgencia();
        this.bancoId = dado.getBancoId();
        this.numeroConta = dado.getNumeroConta();
        this.contatoId = dado.getContatoId();
    }

    public long getId() { return id; }
    public String getAgencia() { return agencia; }
    public long getBancoId() { return bancoId; }
    public String getNumeroConta() { return numeroConta; }
    public long getContatoId() { return contatoId; }
}

