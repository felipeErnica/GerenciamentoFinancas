package com.santacarolina.mavenproject1.model.enums;

public enum DocValueType {

    EXPENSE(1),
    INCOME(2);

    private int docValueType;

    DocValueType(int docValueType) {
        this.docValueType = docValueType;
    }

    public int getDocValueType() {
        return docValueType;
    }

}
