package com.santacarolina.model;

import com.santacarolina.dao.ContaDAO;
import com.santacarolina.exceptions.FetchFailException;

public class PastaContabil implements Cloneable {

    private static final ContaDAO contaDAO = new ContaDAO();

    private long id;
    private String nome;
    private String caminhoPasta;
    private long contaId;
    private ContaBancaria conta;

    public long getId() { return id; }
    public String getNome() { return nome; }
    public String getCaminhoPasta() { return caminhoPasta; }
    public long getContaId() { return contaId; }

    public ContaBancaria getConta() {
        try {
            if (conta == null && contaId != 0) conta = contaDAO.findById(contaId).orElse(null);
        } catch (FetchFailException ignored) {}
        return conta;
    }

    public void setId(long id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCaminhoPasta(String caminhoPasta) { this.caminhoPasta = caminhoPasta; }
    public void setContaId(long contaId) { this.contaId = contaId; }

    public void setConta(ContaBancaria contaBancaria) {
        this.conta = contaBancaria;
        this.contaId = contaBancaria != null ? contaBancaria.getId() : 0;
    }

    @Override
    public String toString() { return nome; }

    @Override
    public PastaContabil clone() {
        PastaContabil clone = new PastaContabil();
        clone.setId(id);
        clone.setNome(nome);
        clone.setCaminhoPasta(caminhoPasta);
        clone.setContaId(contaId);
        return clone;
    }

}
