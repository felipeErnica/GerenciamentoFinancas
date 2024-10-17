package com.santacarolina.model;

import com.santacarolina.dao.BancoDAO;
import com.santacarolina.dto.ContaDTO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ToDTO;

public class ContaBancaria implements ToDTO<ContaDTO> {

    private long id;
    private String nomeConta;
    private String agencia;
    private Banco banco;
    private long bancoId;
    private String numeroConta;

    public ContaBancaria() { }

    public ContaBancaria (ContaDTO dto) {
        this.id = dto.getId();
        this.nomeConta = dto.getNomeConta();
        this.agencia = dto.getAgencia();
        this.bancoId = dto.getBancoId();
        this.numeroConta = dto.getNumeroConta();
    }

    public long getId() { return id; }
    public String getNomeConta() { return nomeConta; }
    public String getAgencia() { return agencia; }
    public String getNumeroConta() { return numeroConta; }
    public long getBancoId() { return bancoId; }

    public Banco getBanco() {
        try {
            if (banco == null) this.banco = new BancoDAO().findById(bancoId).orElse(null);
        } catch (FetchFailException ignored) {}
        return banco;
    }

    public void setId(long id) { this.id = id; }
    public void setNomeConta(String nomeConta) { this.nomeConta = nomeConta; }
    public void setAgencia(String agencia) { this.agencia = agencia; }
    public void setNumeroConta(String numeroConta) { this.numeroConta = numeroConta; }

    public void setBanco(Banco banco) {
        this.banco = banco;
        this.bancoId = banco != null ? banco.getId() : 0;
    }

    @Override
    public String toString() {
        return (getBanco().getApelidoBanco().isEmpty() ? banco.getNomeBanco() : banco.getApelidoBanco()) +
                " AG:" + agencia +
                " CC:" + numeroConta;
    }

    @Override
    public ContaDTO toDTO() { return new ContaDTO().fromObject(this); }

}
