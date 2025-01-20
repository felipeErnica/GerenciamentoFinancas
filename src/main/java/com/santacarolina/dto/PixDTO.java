package com.santacarolina.dto;

import com.santacarolina.enums.TipoPix;
import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.ChavePix;
import com.santacarolina.util.DocConversor;

public class PixDTO implements FromDTO<ChavePix> {

    private long id;
    private ContatoDTO contato;
    private DadoDTO dado;
    private TipoPix tipoPix;
    private String chave;

    public PixDTO() { }

    public PixDTO (ChavePix c) {
        this.id = c.getId();
        this.contato = new ContatoDTO(c.getContato());
        this.dado = new DadoDTO(c.getDadoBancario());
        this.tipoPix = c.getTipoPix();
        this.chave = c.getChave();
    }

    public long getId() { return id; }
    public TipoPix getTipoPix() { return tipoPix; }
    public String getChave() { return chave; }
    public ContatoDTO getContato() { return contato; }
    public DadoDTO getDado() { return dado; }

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
        return "PixDTO{id=" + id + ", nomeContato=" + contato.getNome() + ", tipoPix=" + tipoPix + ", chave=" + chave
                + ", nomeBanco=" + dado.getBanco().getNomeBanco() + ", agencia=" + dado.getAgencia() + ", numeroConta=" + dado.getNumeroConta() + "}";
    }

}
