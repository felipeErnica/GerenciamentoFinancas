package com.santacarolina.model.beans;

import com.santacarolina.dao.BancoDAO;
import com.santacarolina.dto.ContaDTO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.DTOConversible;

public class ContaBancaria implements DTOConversible<ContaBancaria, ContaDTO> {

    private BancoDAO bancoDAO;
    private long id;
    private String nomeConta;
    private String agencia;
    private Banco banco;
    private long bancoId;
    private String numeroConta;

    public ContaBancaria() {
        bancoDAO = new BancoDAO();
    }

    public long getId() { return id; }
    public String getNomeConta() { return nomeConta; }
    public String getAgencia() { return agencia; }
    public String getNumeroConta() { return numeroConta; }

    public Banco getBanco() {
        try {
            if (banco == null) this.banco = bancoDAO.findById(bancoId).orElse(null);
            return banco;
        } catch (FetchFailException e) {
            return null;
        }
    }

    public void setId(long id) { this.id = id; }
    public void setNomeConta(String nomeConta) { this.nomeConta = nomeConta; }
    public void setAgencia(String agencia) { this.agencia = agencia; }
    public void setNumeroConta(String numeroConta) { this.numeroConta = numeroConta; }

    public void setBanco(Banco banco) {
        this.banco = banco;
        this.bancoId = banco.getId();
    }

    @Override
    public String toString() {
        return (getBanco().getApelidoBanco().isEmpty() ? banco.getNomeBanco() : banco.getApelidoBanco()) +
                " AG:" + agencia +
                " CC:" + numeroConta;
    }

    @Override
    public ContaBancaria returnNew() { return new ContaBancaria(); }

    @Override
    public ContaBancaria fromDTO(ContaDTO contaDTO) {
        id = contaDTO.id();
        nomeConta = contaDTO.nomeConta();
        agencia = contaDTO.agencia();
        bancoId = contaDTO.bancoId();
        numeroConta = contaDTO.numeroConta();
        return this;
    }

    @Override
    public ContaDTO toDTO() {
        return new ContaDTO(
                id,
                nomeConta,
                agencia,
                bancoId,
                numeroConta);
    }

}
