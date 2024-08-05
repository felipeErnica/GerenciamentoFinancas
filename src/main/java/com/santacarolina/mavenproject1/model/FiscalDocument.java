package com.santacarolina.mavenproject1.model;

import com.santacarolina.mavenproject1.dto.NfeDTO;
import com.santacarolina.mavenproject1.model.enums.DocType;
import com.santacarolina.mavenproject1.model.enums.DocValueType;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;

public class FiscalDocument {

    private long id;
    private String docNumber;
    private DocType docType;
    private Contact sender;
    private Contact receiver;
    private String filePath;
    private UserFolder userFolder;
    private double value;
    private LocalDate emissionDate;
    private DocValueType docValueType;
    private List<Payment> payments;
    private List<Product> products;

    public FiscalDocument(NfeDTO nfeDTO) {
        docNumber = nfeDTO.NFe().infNFe().ide().nNF();
        value =  nfeDTO.NFe().infNFe().total().ICMSTot().vNF();
        emissionDate = convertDate(nfeDTO.NFe().infNFe().ide().dhEmi());
        docValueType = DocValueType.EXPENSE;
        docType = DocType.NFE;
    }

    private LocalDate convertDate(String dhEmi) {
        final int DATE_SIZE = 10;
        String date = dhEmi.substring(0,DATE_SIZE);
        return LocalDate.parse(date,DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public long getId() {return id;}
    public String getDocNumber() {return docNumber;}
    public DocType getDocType() {return docType;}
    public Contact getSender() {return sender;}
    public Contact getReceiver() {return receiver;}
    public String getFilePath() {return filePath;}
    public UserFolder getUserFolder() {return userFolder;}
    public double getValue() {return value;}
    public LocalDate getEmissionDate() {return emissionDate;}
    public boolean isExpense() {return docValueType == DocValueType.EXPENSE;}
    public boolean isIncome() {return docValueType == DocValueType.INCOME;}
    public List<Payment> getPayments() {return payments;}
    public List<Product> getProducts() {return products;}
}
