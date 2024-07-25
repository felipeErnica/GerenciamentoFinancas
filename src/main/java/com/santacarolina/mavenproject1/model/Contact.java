package com.santacarolina.mavenproject1.model;

import java.util.ArrayList;
import java.util.List;

public class Contact {
    private long id;
    private String name;
    private String numDoc;
    private List<BankAccount> bankAccounts = new ArrayList<>();
}
