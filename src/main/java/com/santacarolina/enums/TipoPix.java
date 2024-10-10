package com.santacarolina.enums;

public enum TipoPix {

    EMAIL("E-MAIL", 0),
    TELEFONE("TELEFONE", 1),
    CPF("CPF", 2),
    CNPJ("CNPJ", 3),
    CHAVE_ALEATORIA("CHAVE ALEATÓRIA", 4);

    private String tipoPix;
    private int tipoPixInt;

    TipoPix(String tipoPix, int tipoPixInt) {
        this.tipoPix = tipoPix;
        this.tipoPixInt = tipoPixInt;
    }

    public int getInt() { return tipoPixInt; }
    public String getString() { return tipoPix; }

    @Override
    public String toString() { return tipoPix; }
}