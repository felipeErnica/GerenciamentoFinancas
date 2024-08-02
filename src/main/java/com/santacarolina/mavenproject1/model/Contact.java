package com.santacarolina.mavenproject1.model;

import com.santacarolina.mavenproject1.dto.ContactDTO;
import java.util.List;

public class Contact {

    private long id;
    private String name;
    private String cpf;
    private String cnpj;
    private String ie;
    private List<ContactBankAccount> contactBankAccounts;

    public Contact(){
    }

    public Contact(ContactDTO contactDTO) {
        name = contactDTO.xNome();
        cpf = contactDTO.CPF();
        cnpj = contactDTO.CNPJ();
        ie = contactDTO.IE();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getIe() {
        return ie;
    }

    public List<ContactBankAccount> getContactBankAccounts() {
        return contactBankAccounts;
    }
}
