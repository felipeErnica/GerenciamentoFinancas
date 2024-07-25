package com.santacarolina.mavenproject1.model;

import java.time.LocalDate;
import java.util.List;

public class BankStatment {

    private long id;
    private LocalDate statmentDate;
    private UserBankAccount userBankAccount;
    private String bankCategory;
    private String bankDesc;
    private double statmentValue;
    private boolean isConciliated;
    private List<Conciliation> conciliationList;

}
