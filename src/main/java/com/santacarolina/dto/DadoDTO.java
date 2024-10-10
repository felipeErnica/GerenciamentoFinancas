package com.santacarolina.dto;

import com.santacarolina.enums.TipoPix;
import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.DadoBancario;
import com.santacarolina.util.DocConversor;

public class DadoDTO implements FromDTO<DadoBancario> {

    private long id;
    private String agencia;
    private long bancoId;
    private String nomeBanco;
    private String numeroConta;
    private long contatoId;
    private String nomeContato;
    private Long pixId;
    private String chave;
    private TipoPix tipoPix;
    private PixDTO pixDTO;

    public DadoDTO (DadoBancario d) {
        this.id = d.getId();
        this.agencia = d.getAgencia();
        this.bancoId = d.getBancoId();
        this.numeroConta = d.getNumeroConta();
        this.contatoId = d.getContatoId();
        this.pixDTO = d.getChavePix() != null ? d.getChavePix().toDTO() : null;
    }

    public DadoDTO() {}

    public long getId() { return id; }
    public String getAgencia() { return agencia; }
    public long getBancoId() { return bancoId; }
    public String getNumeroConta() { return numeroConta; }
    public long getContatoId() { return contatoId; }
    public Long getPixId() { return pixId; }
    public PixDTO getPixDTO() { return pixDTO; }
    public String getNomeContato() { return nomeContato; }
    public String getChave() { return chave; }
    public TipoPix getTipoPix() { return tipoPix; }
    public String getNomeBanco() { return nomeBanco; }

    public String printChave() {
        if (pixId != null) {
            return switch (tipoPix) {
                case CPF -> DocConversor.docFormat(chave, DocConversor.CPF_FORMAT);
                case CNPJ -> DocConversor.docFormat(chave, DocConversor.CNPJ_FORMAT);
                case TELEFONE -> DocConversor.docFormat(chave, DocConversor.PHONE_FORMAT);
                default -> chave;
            };
        } else return chave;
    }

    @Override
    public DadoBancario fromDTO() { return new DadoBancario(this); }

}
