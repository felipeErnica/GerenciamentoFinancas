package com.santacarolina.enums;

public enum TipoDoc {

    NOTA_FISCAL("NOTA FISCAL"),
    RECIBO("RECIBO"),
    DARF("DARF"),
    GARE("GARE"),
    HOLERITE("HOLERITE"),
    DARE("DARE"),
    NFE("NFe"),
    BOLETO("BOLETO"),
    CONTRATO("CONTRATO"),
    TRANSFERENCIA("TRANSFERÊNCIA BANCÁRIA"),
    IPTU("CARNÊ DE IPTU"),
    GRU("GRU"),
    CUPOM_FISCAL("CUPOM FISCAL"),
    GRF("GRF");

    private String tipoDoc;

    TipoDoc(String docType) {
        this.tipoDoc = docType;
    }
    public String getTipoDoc() {
        return tipoDoc;
    }

    @Override
    public String toString() {
        return tipoDoc;
    }
}
