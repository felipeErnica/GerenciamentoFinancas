package com.santacarolina.dto;

import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.DadoBancario;

public class DadoDTO implements FromDTO<DadoBancario> {

    private long id;
    private String agencia;
    private BancoDTO banco;
    private String numeroConta;
    private ContatoDTO contato;

    public DadoDTO (DadoBancario d) {
        this.id = d.getId();
        this.agencia = d.getAgencia();
        this.numeroConta = d.getNumeroConta();
        this.banco = new BancoDTO(d.getBanco());
        this.contato = new ContatoDTO(d.getContato());
    }

    public DadoDTO() {}

    public long getId() { return id; }
    public String getAgencia() { return agencia; }
    public String getNumeroConta() { return numeroConta; }
    public BancoDTO getBanco() { return banco; }
    public ContatoDTO getContato() { return contato; }

    @Override
    public DadoBancario fromDTO() { return new DadoBancario(this); }

}
