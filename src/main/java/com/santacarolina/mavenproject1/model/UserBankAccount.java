package com.santacarolina.mavenproject1.model;

import java.util.List;

public class UserBankAccount {
    private long id;
    private String agency;
    private Bank bank;
    private String accountNumber;
    private UserFolder userFolder;
    private List<BankStatment> bankStatmentList;
}
