package com.santacarolina.model.beans;

public class PastaContabil {

    private long id;
    private String nome;
    private String caminhoPasta;
    private ContaBancaria contaBancaria;

    public PastaContabil() {
        nome = "";
        caminhoPasta = "";
    }

    public long getId() { return id; }
    public String getNome() { return nome; }
    public String getCaminhoPasta() { return caminhoPasta; }
    public ContaBancaria getContaBancaria() { return contaBancaria; }

    public void setId(long id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCaminhoPasta(String caminhoPasta) { this.caminhoPasta = caminhoPasta; }
    public void setContaBancaria(ContaBancaria contaBancaria) { this.contaBancaria = contaBancaria; }

    @Override
    public String toString() { return nome; }

}
