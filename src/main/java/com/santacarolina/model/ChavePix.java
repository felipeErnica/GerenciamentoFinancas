package com.santacarolina.model;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.santacarolina.dao.ContatoDAO;
import com.santacarolina.dao.DadoDAO;
import com.santacarolina.enums.TipoPix;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.DocConversor;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChavePix implements Cloneable {

    private long id;
    private Long dadoId;
    private DadoBancario dado;
    private long contatoId;
    private Contato contato;
    private TipoPix tipoPix;
    private String chave;

    public long getId() { return id; }
    public TipoPix getTipoPix() { return tipoPix; }
    public String getChave() { return chave; }
    public Long getDadoId() { return dadoId; }
    public long getContatoId() { return contatoId; }

    public Contato getContato() {
        try {
            if (contato == null && contatoId != 0) this.contato = new ContatoDAO().findById(contatoId).orElse(null);
        } catch (FetchFailException ignored) {}
        return contato;
    }

    public DadoBancario getDado() {
        try {
            if (dado == null && dadoId != null) this.dado = new DadoDAO().findById(dadoId).orElse(null);
            return dado;
        } catch (FetchFailException ignored) {}
        return dado;
    }

    public boolean isInvalidFormat() {
        if (tipoPix == null || StringUtils.isBlank(chave)) return false;
        return switch (tipoPix) {
            case CPF, TELEFONE -> chave.length() != 11;
            case CNPJ -> chave.length() != 14;
            default -> false;
        };
    }

    public void setId(long id) { this.id = id; }
    public void setTipoPix(TipoPix tipoPix) { this.tipoPix = tipoPix; }
    public void setContatoId(long contatoId) { this.contatoId = contatoId; }
    public void setDadoId(Long dadoId) { this.dadoId = dadoId; }

    public void setContato(Contato contato) {
        this.contato = contato;
        this.contatoId = contato != null ? contato.getId() : 0;
    }

    public void setDado(DadoBancario dadoBancario) {
        this.dado = dadoBancario;
        this.dadoId = dadoBancario != null ? dadoBancario.getId() : null;
    }

    public void setChave(String chave) {
        if (tipoPix != null && !StringUtils.isBlank(chave)) {
            this.chave = switch (tipoPix) {
                case CPF, TELEFONE, CNPJ -> chave.replaceAll("[^\\d]", "");
                default -> chave;
            };
        } else this.chave = chave;
    }

    public String print() {
        final StringBuffer sb = new StringBuffer("ChavePix{");
        sb.append("id=").append(id);
        sb.append(", dadoId=").append(dadoId);
        sb.append(", contato=").append(contato);
        sb.append(", tipoPix=").append(tipoPix);
        sb.append(", chave='").append(chave).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public String toString() {
        if (tipoPix != null && !isInvalidFormat()) {
            return switch (tipoPix) {
                case CPF -> DocConversor.docFormat(chave, DocConversor.CPF_FORMAT);
                case CNPJ -> DocConversor.docFormat(chave, DocConversor.CNPJ_FORMAT);
                case TELEFONE -> DocConversor.docFormat(chave, DocConversor.PHONE_FORMAT);
                default -> chave;
            };
        } else return chave;
    }

    @Override
    public ChavePix clone() {
        ChavePix pix = new ChavePix();
        pix.setId(id);
        pix.setDadoId(dadoId);
        pix.setContatoId(contatoId);
        pix.setTipoPix(tipoPix);
        pix.setChave(chave);
        return pix;
    }

    public String printChave() {
        return switch (tipoPix) {
            case CPF -> DocConversor.docFormat(chave, DocConversor.CPF_FORMAT);
            case CNPJ -> DocConversor.docFormat(chave, DocConversor.CNPJ_FORMAT);
            case TELEFONE -> DocConversor.docFormat(chave, DocConversor.PHONE_FORMAT);
            default -> chave;
        };
    }

}

