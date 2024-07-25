package com.santacarolina.mavenproject1.model;

import com.santacarolina.mavenproject1.model.enums.DocValueType;

import java.time.LocalDate;
import java.util.List;

public class FiscalDocument {

    private long id;
    private String docNumber;
    private Contact sender;
    private Contact receiver;
    private String filePath;
    private UserFolder userFolder;
    private double value;
    private LocalDate emissionDate;
    private DocValueType docValueType;
    private List<Payment> payments;
    private List<Product> products;

}
