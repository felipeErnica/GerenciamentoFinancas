package com.santacarolina.dto;

import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.ContaBancaria;

public class ContaDTO implements FromDTO<ContaBancaria> {

    private long id;
    private String nomeConta;
    private String agencia;
    private long bancoId;
    private String numeroConta;
    private String abreviacaoConta;

    public ContaDTO fromObject(ContaBancaria c) {
        this.id = c.getId();
        this.nomeConta = c.getNomeConta();
        this.agencia = c.getAgencia();
        this.bancoId = c.getBancoId();
        this.numeroConta = c.getNumeroConta();
        this.abreviacaoConta =c.getAbreviacaoConta();
        return this;
    }

    public ContaDTO() {}

    public long getId() { return id; }
    public String getNomeConta() { return nomeConta; }
    public String getAgencia() { return agencia; }
    public long getBancoId() { return bancoId; }
    public String getNumeroConta() { return numeroConta; }
    public String getAbreviacaoConta() { return abreviacaoConta; }

    @Override
    public ContaBancaria fromDTO() { return new ContaBancaria(this); }

}
