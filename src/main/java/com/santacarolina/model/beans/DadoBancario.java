package com.santacarolina.model.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.dao.PixDao;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DadoBancario {

    private long id;
    private String agencia;
    private Banco banco;
    private String numeroConta;
    private Long pixId;
    private ChavePix chavePix;
    private Contato contato;

    public DadoBancario() {
        agencia = "";
        numeroConta = "";
    }

    public DadoBancario(Builder builder) {
        this.id = builder.id;
        this.agencia = builder.agencia;
        this.banco = builder.banco;
        this.numeroConta = builder.numeroConta;
        this.pixId = builder.pixId;
        this.contato = builder.contato;
    }

    public long getId() { return id; }
    public String getAgencia() { return agencia; }
    public Banco getBanco() { return banco; }
    public String getNumeroConta() { return numeroConta; }
    public Long getPixId() { return pixId; }

    public ChavePix getChavePix() {
        try {
            if (chavePix == null) {
                if (pixId != null) new PixDao().findById(pixId).ifPresent(c -> {
                    c.setDadoBancario(null);
                    chavePix = c;
                });
            }
            return chavePix;
        } catch (FetchFailException e) {
            return null;
        }
    }

    public Contato getContato() { return contato; }

    public void setId(long id) { this.id = id; }
    public void setAgencia(String agencia) { this.agencia = agencia; }
    public void setBanco(Banco banco) { this.banco = banco; }
    public void setNumeroConta(String numeroConta) { this.numeroConta = numeroConta; }

    public void setChavePix(ChavePix chavePix) {
        this.pixId = chavePix != null ? chavePix.getId() : null;
        this.chavePix = chavePix;
    }
    public void setContato(Contato contato) { this.contato = contato; }

    @Override
    public String toString() { return numeroConta; }

    public static class Builder {

        private long id;
        private String agencia;
        private Banco banco;
        private String numeroConta;
        private Long pixId;
        private Contato contato;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setAgencia(String agencia) {
            this.agencia = agencia;
            return this;
        }

        public Builder setBanco(Banco banco) {
            this.banco = banco;
            return this;
        }

        public Builder setNumeroConta(String numeroConta) {
            this.numeroConta = numeroConta;
            return this;
        }

        public Builder setPixId(Long pixId) {
            this.pixId = pixId;
            return this;
        }

        public Builder setContato(Contato contato) {
            this.contato = contato;
            return this;
        }

        public DadoBancario build() { return new DadoBancario(this); }

    }

}
