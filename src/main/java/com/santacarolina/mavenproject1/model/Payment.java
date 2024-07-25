package com.santacarolina.mavenproject1.model;

import com.santacarolina.mavenproject1.model.enums.PaymentType;

import java.time.LocalDate;
import java.util.List;

public class Payment {

    private long id;
    private FiscalDocument document;
    private int number;
    private LocalDate dueDate;
    private PaymentType paymentType;
    private double value;
    private String paymentPath;
    private UserBankAccount userBankAccount;
    private ContactBankAccount contactBankAccount;
    private boolean isPayed;
    private List<Conciliation> conciliations;

}
