package com.santacarolina.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.santacarolina.dao.BancoDAO;
import com.santacarolina.dao.ContatoDAO;
import com.santacarolina.dto.DadoDTO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.dao.PixDAO;
import com.santacarolina.interfaces.ToDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DadoBancario implements ToDTO<DadoDTO>, Cloneable {

    private static final BancoDAO bancoDAO = new BancoDAO();
    private static final ContatoDAO contatoDAO = new ContatoDAO();
    private static final PixDAO pixDAO = new PixDAO();

    private long id;
    private String agencia;
    private Banco banco;
    private String numeroConta;
    private Long pixId;
    private ChavePix chavePix;
    private Contato contato;
    private long bancoId;
    private long contatoId;

    public DadoBancario() {}

    public DadoBancario (DadoDTO dto) {
        this.id = dto.getId();
        this.agencia = dto.getAgencia();
        this.numeroConta = dto.getNumeroConta();
        this.pixId = dto.getPixId();
        this.bancoId = dto.getBancoId();
        this.contatoId = dto.getContatoId();
    }

    public long getId() { return id; }
    public String getAgencia() { return agencia; }
    public String getNumeroConta() { return numeroConta; }
    public Long getPixId() { return pixId; }
    public long getBancoId() { return bancoId; }
    public long getContatoId() { return contatoId; }

    public ChavePix getChavePix() {
        try {
            if (chavePix == null && pixId != null) this.chavePix = pixDAO.findById(pixId).orElse(null);
            return chavePix;
        } catch (FetchFailException e) {
            return chavePix;
        }
    }

    public Contato getContato() {
        try {
            if (contato == null) this.contato = contatoDAO.findById(contatoId).orElse(null);
            return contato;
        } catch (FetchFailException e) {
            return contato;
        }
    }

    public Banco getBanco() {
        try {
            if (banco == null) this.banco = bancoDAO.findById(bancoId).orElse(null);
            return banco;
        } catch (FetchFailException e) {
            return banco;
        }
    }

    public void setId(long id) { this.id = id; }
    public void setAgencia(String agencia) { this.agencia = agencia; }
    public void setNumeroConta(String numeroConta) { this.numeroConta = numeroConta; }
    public void setPixId(Long pixId) { this.pixId = pixId; }
    public void setBancoId(long bancoId) { this.bancoId = bancoId; }
    public void setContatoId(long contatoId) { this.contatoId = contatoId; }

    public void setChavePix(ChavePix chavePix) {
        this.chavePix = chavePix;
        pixId = chavePix != null ? chavePix.getId() : null;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
        this.contatoId = contato != null ? contato.getId() : 0;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
        this.bancoId = banco != null ? banco.getId() : 0;
    }

    public void addChavePix(ChavePix chavePix) {
        setChavePix(chavePix);
        chavePix.setDadoBancario(this);
    }

    @Override
    public DadoBancario clone() {
        DadoBancario clone = new DadoBancario();
        clone.setId(id);
        clone.setContatoId(contatoId);
        clone.setAgencia(agencia);
        clone.setBancoId(bancoId);
        clone.setNumeroConta(numeroConta);
        clone.setPixId(pixId);
        return clone;
    }

    @Override
    public String toString() { return numeroConta; }

    @Override
    public DadoDTO toDTO() { return new DadoDTO(this); }

}
