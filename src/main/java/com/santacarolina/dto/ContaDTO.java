package com.santacarolina.dto;

import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.ContaBancaria;

public class ContaDTO implements FromDTO<ContaBancaria> {

    private long id;
    private String nomeConta;
    private String agencia;
    private BancoDTO banco;
    private String numeroConta;
    private String abreviacaoConta;

    public ContaDTO (ContaBancaria c) {
        this.id = c.getId();
        this.nomeConta = c.getNomeConta();
        this.agencia = c.getAgencia();
        this.banco = new BancoDTO(c.getBanco());
        this.numeroConta = c.getNumeroConta();
        this.abreviacaoConta =c.getAbreviacaoConta();
    }

    public ContaDTO() {}

    public long getId() { return id; }
    public String getNomeConta() { return nomeConta; }
    public String getAgencia() { return agencia; }
    public String getNumeroConta() { return numeroConta; }
    public String getAbreviacaoConta() { return abreviacaoConta; }
    public BancoDTO getBanco() { return banco; }

    @Override
    public ContaBancaria fromDTO() { return new ContaBancaria(this); }

}
