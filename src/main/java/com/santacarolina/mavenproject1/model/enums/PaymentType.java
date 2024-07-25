package com.santacarolina.mavenproject1.model.enums;

public enum PaymentType {

    DEBIT_CARD("CARTÃO DE DÉBITO"),
    PIX("PIX"),
    BANK_TRANSFER("TED"),
    BANK_SLIP("BOLETO"),
    ON_MONEY("DINHEIRO VIVO"),
    DEBIT_ACCOUNT("DÉBITO EM CONTA");

    private String paymentType;

    PaymentType (String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentType() {
        return paymentType;
    }

}
