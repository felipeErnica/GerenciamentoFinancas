package com.santacarolina.dto;

import com.santacarolina.enums.TipoPix;
import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.ChavePix;
import com.santacarolina.util.DocConversor;

public class PixDTO implements FromDTO<ChavePix> {

    private long id;
    private long contatoId;
    private String nomeContato;
    private Long dadoId;
    private TipoPix tipoPix;
    private String chave;
    private String nomeBanco;
    private String agencia;
    private String numeroConta;

    public PixDTO() { }

    public PixDTO (ChavePix c) {
        this.id = c.getId();
        this.contatoId = c.getContatoId();
        this.dadoId = c.getDadoId();
        this.tipoPix = c.getTipoPix();
        this.chave = c.getChave();
    }

    public long getId() { return id; }
    public long getContatoId() { return contatoId; }
    public Long getDadoId() { return dadoId; }
    public TipoPix getTipoPix() { return tipoPix; }
    public String getChave() { return chave; }
    public String getNomeBanco() { return nomeBanco; }
    public String getAgencia() { return agencia; }
    public String getNumeroConta() { return numeroConta; }
    public String getNomeContato() { return nomeContato; }

    @Override
    public ChavePix fromDTO() { return new ChavePix(this); }

    public String printChave() {
        if (chave == null) return null;
        if (!isValidFormat()) return chave;
        return switch (tipoPix) {
            case TELEFONE -> DocConversor.docFormat(chave, DocConversor.PHONE_FORMAT);
            case CPF -> DocConversor.docFormat(chave, DocConversor.CPF_FORMAT);
            case CNPJ -> DocConversor.docFormat(chave, DocConversor.CNPJ_FORMAT);
            default -> chave;
        };
    }

    private boolean isValidFormat() {
        return switch (tipoPix) {
            case TELEFONE -> DocConversor.isValidFormat(chave, DocConversor.PHONE_FORMAT);
            case CPF -> DocConversor.isValidFormat(chave, DocConversor.CPF_FORMAT);
            case CNPJ -> DocConversor.isValidFormat(chave, DocConversor.CNPJ_FORMAT);
            default -> true;
        };
    }

    @Override
    public String toString() {
        return "PixDTO{id=" + id + ", nomeContato=" + nomeContato + ", tipoPix=" + tipoPix + ", chave=" + chave
                + ", nomeBanco=" + nomeBanco + ", agencia=" + agencia + ", numeroConta=" + numeroConta + "}";
    }

}
