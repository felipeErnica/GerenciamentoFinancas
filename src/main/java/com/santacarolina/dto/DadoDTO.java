package com.santacarolina.dto;

import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.DadoBancario;

public class DadoDTO implements FromDTO<DadoBancario> {

    private long id;
    private String agencia;
    private long bancoId;
    private String nomeBanco;
    private String numeroConta;
    private long contatoId;
    private String nomeContato;

    public DadoDTO (DadoBancario d) {
        this.id = d.getId();
        this.agencia = d.getAgencia();
        this.bancoId = d.getBancoId();
        this.numeroConta = d.getNumeroConta();
        this.contatoId = d.getContatoId();
    }

    public DadoDTO() {}

    public long getId() { return id; }
    public String getAgencia() { return agencia; }
    public long getBancoId() { return bancoId; }
    public String getNumeroConta() { return numeroConta; }
    public long getContatoId() { return contatoId; }
    public String getNomeContato() { return nomeContato; }
    public String getNomeBanco() { return nomeBanco; }

    @Override
    public DadoBancario fromDTO() { return new DadoBancario(this); }

}
