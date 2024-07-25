package com.santacarolina.mavenproject1.model;

import com.santacarolina.mavenproject1.model.enums.PixType;

public class ContactBankAccount extends BankAccount{
    private long id;
    private PixType pixType;
    private String pixKey;
    private Contact contact;
}
