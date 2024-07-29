package com.santacarolina.mavenproject1.model;

import com.santacarolina.mavenproject1.model.enums.PixType;

public class ContactBankAccount {
    private long id;
    private String agency;
    private Bank bank;
    private String accountNumber;
    private PixType pixType;
    private String pixKey;
    private Contact contact;
}
