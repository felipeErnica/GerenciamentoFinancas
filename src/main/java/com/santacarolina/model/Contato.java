package com.santacarolina.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.santacarolina.dto.ContatoDTO;
import com.santacarolina.interfaces.ToDTO;
import com.santacarolina.util.DocConversor;
import org.apache.commons.lang3.StringUtils;

public class Contato implements Cloneable, ToDTO<ContatoDTO> {

    private long id;
    private String nome;
    private String cpf;
    private String cnpj;
    private String ie;

    public Contato (ContatoDTO dto) {
        this.id = dto.getId();
        this.nome = dto.getNome();
        this.cpf = dto.getCpf();
        this.cnpj = dto.getCnpj();
        this.ie = dto.getIe();
    }

    public Contato() {}

    public long getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getCnpj() { return cnpj; }
    public String getIe() { return ie; }

    public void setId(long id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public void setIe(String ie) { this.ie = ie; }

    public String printCpf() { return DocConversor.docFormat(cpf, DocConversor.CPF_FORMAT); }
    public String printCnpj() { return DocConversor.docFormat(cnpj, DocConversor.CNPJ_FORMAT); }
    public String printIe() { return DocConversor.docFormat(ie, DocConversor.IE_FORMAT); }

    @JsonIgnore
    public boolean isValidCpf() { return DocConversor.isValidFormat(cpf, DocConversor.CPF_FORMAT); }

    @JsonIgnore
    public boolean isValidCnpj() { return DocConversor.isValidFormat(cnpj, DocConversor.CNPJ_FORMAT); }

    @JsonIgnore
    public boolean isValidIe() { return DocConversor.isValidFormat(ie, DocConversor.IE_FORMAT); }

    @JsonIgnore
    public boolean isEmptyDocuments() {
        return StringUtils.isBlank(ie) && StringUtils.isBlank(cnpj) && StringUtils.isBlank(cpf);
    }

    public Contato ofContato(Contato other) {
        id = other.getId();
        nome = other.getNome();
        cpf  = other.getCpf();
        cnpj = other.getCnpj();
        ie = other.getIe();
        return this;
    }

    @Override
    public Contato clone() {
        Contato contato = new Contato();
        contato.setId(id);
        contato.setNome(nome);
        contato.setCnpj(cnpj);
        contato.setCpf(cpf);
        contato.setIe(ie);
        return contato;
    }

    @Override
    public String toString() { return nome; }

    public String print() {
        final StringBuilder sb = new StringBuilder("Contato{");
        sb.append("nome='").append(nome).append('\'');
        sb.append(", cpf='").append(cpf).append('\'');
        sb.append(", cnpj='").append(cnpj).append('\'');
        sb.append(", ie='").append(ie).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contato that)) return false;
        return  id == that.id;
    }

    @Override
    public ContatoDTO toDTO() { return new ContatoDTO(this); }

}
