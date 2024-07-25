package com.santacarolina.mavenproject1.model.enums;

public enum PixType {

    EMAIL("E-MAIL"),
    PHONE_NUMBER("TELEFONE"),
    CPF("CPF"),
    CNPJ("CNPJ"),
    RANDOM_KEY("CHAVE ALEATÓRIA");

    private String pixType;

    PixType(String pixType) {
        this.pixType = pixType;
    }

    public String getPixType() {
        return pixType;
    }
}
