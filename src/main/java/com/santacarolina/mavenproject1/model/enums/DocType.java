package com.santacarolina.mavenproject1.model.enums;

public enum DocType {

    NOTA_FISCAL("NOTA FISCAL"),
    RECEIPT("RECIBO"),
    DARF("DARF"),
    GARE("GARE"),
    HOLERITE("HOLERITE"),
    DARE("DARE"),
    NFE("NFe"),
    BOLETO("BOLETO"),
    CONTRATO("CONTRATO"),
    BANK_TRANSFER("TRANSFERÊNCIA BANCÁRIA"),
    IPTU("CARNÊ DE IPTU"),
    GRU("GRU"),
    CUPOM_FISCAL("CUPOM FISCAL"),
    GRF("GRF");

    private String docType;

    DocType(String docType) {
        this.docType = docType;
    }

    public String getDocType() {
        return docType;
    }

}
