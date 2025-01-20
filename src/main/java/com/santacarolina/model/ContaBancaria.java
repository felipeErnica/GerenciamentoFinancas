package com.santacarolina.model;

import com.santacarolina.dao.BancoDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.Copiable;

public class ContaBancaria implements Copiable<ContaBancaria> {

    private long id;
    private String nomeConta;
    private String agencia;
    private Banco banco;
    private long bancoId;
    private String numeroConta;
    private String abreviacaoConta;

    public long getId() { return id; }
    public String getNomeConta() { return nomeConta; }
    public String getAgencia() { return agencia; }
    public String getNumeroConta() { return numeroConta; }
    public long getBancoId() { return bancoId; }
    public String getAbreviacaoConta() { return abreviacaoConta; }

    public Banco getBanco() {
        try {
            if (banco == null && bancoId != 0) this.banco = new BancoDAO().findById(bancoId).orElse(null);
        } catch (FetchFailException ignored) {}
        return banco;
    }

    public void setId(long id) { this.id = id; }
    public void setNomeConta(String nomeConta) { this.nomeConta = nomeConta; }
    public void setAgencia(String agencia) { this.agencia = agencia; }
    public void setNumeroConta(String numeroConta) { this.numeroConta = numeroConta; }
    public void setAbreviacao(String abreviacao) { this.abreviacaoConta = abreviacao; }

    public void setBanco(Banco banco) {
        this.banco = banco;
        this.bancoId = banco != null ? banco.getId() : 0;
    }

    public ContaBancaria generateCopy() {
        ContaBancaria copy = new ContaBancaria();
        copy.setAbreviacao(abreviacaoConta);
        copy.setBanco(banco);
        copy.setId(id);
        copy.setAgencia(agencia);
        copy.setNomeConta(nomeConta);
        copy.setNumeroConta(numeroConta);
        return copy;
    }

    @Override
    public String toString() { return abreviacaoConta != null ? abreviacaoConta : nomeConta; }

}
