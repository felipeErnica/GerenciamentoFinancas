package com.santacarolina.model.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.dao.DadoDao;
import com.santacarolina.enums.TipoPix;
import com.santacarolina.util.DocConversor;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChavePix {

    private long id;
    private Long dadoId;
    private DadoBancario dadoBancario;
    private Contato contato;
    private TipoPix tipoPix;
    private String chave;

    public ChavePix() {
        this.contato = new Contato();
        this.tipoPix = TipoPix.CHAVE_ALEATORIA;
        this.chave = "";
    }

    public ChavePix(Builder builder) {
        this.id = builder.id;
        this.dadoId = builder.dadoId;
        this.contato = builder.contato;
        this.tipoPix = builder.tipoPix;
        this.chave = builder.chave;
    }

    public long getId() { return id; }
    public TipoPix getTipoPix() { return tipoPix; }
    public String getChave() { return chave; }
    public Contato getContato() { return contato; }
    public Long getDadoId() { return dadoId; }

    public DadoBancario getDadoBancario() {
        try {
            if (dadoBancario == null) {
                if (dadoId != null) {
                    new DadoDao().findById(dadoId).ifPresent(d -> {
                        d.setChavePix(null);
                        dadoBancario = d;
                    });
                }
            }
            return dadoBancario;
        } catch (FetchFailException e) {
            return null;
        }
    }

    public boolean isInvalidFormat() {
        if (tipoPix == null) return true;
        return switch (tipoPix) {
            case CPF, TELEFONE -> chave.length() != 11;
            case CNPJ -> chave.length() != 14;
            default -> false;
        };
    }

    public void setId(long id) { this.id = id; }
    public void setContato(Contato contato) { this.contato = contato; }
    public void setTipoPix(TipoPix tipoPix) { this.tipoPix = tipoPix; }

    public void setDadoBancario(DadoBancario dadoBancario) {
        this.dadoBancario = dadoBancario;
        this.dadoId = dadoBancario != null ? dadoBancario.getId() : null;
    }

    public void setChave(String chave) {
        if (tipoPix != null) {
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

        if (!isInvalidFormat()) {
            return switch (tipoPix) {
                case CPF -> DocConversor.docFormat(chave, DocConversor.CPF_FORMAT);
                case CNPJ -> DocConversor.docFormat(chave, DocConversor.CNPJ_FORMAT);
                case TELEFONE -> DocConversor.docFormat(chave, DocConversor.PHONE_FORMAT);
                default -> chave;
            };
        } else return chave;

    }

    public static class Builder {

        private long id;
        private Long dadoId;
        private Contato contato;
        private TipoPix tipoPix;
        private String chave;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setDadoId(Long dadoId) {
            this.dadoId = dadoId;
            return this;
        }

        public Builder setContato(Contato contato) {
            this.contato = contato;
            return this;
        }

        public Builder setTipoPix(TipoPix tipoPix) {
            this.tipoPix = tipoPix;
            return this;
        }

        public Builder setChave(String chave) {
            this.chave = chave;
            return this;
        }

        public ChavePix build() {
            return new ChavePix(this);
        }

    }

}

