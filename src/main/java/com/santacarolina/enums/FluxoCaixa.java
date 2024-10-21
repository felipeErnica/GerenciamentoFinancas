package com.santacarolina.enums;

public enum FluxoCaixa {
    DESPESA (0),
    RECEITA (1);

    private int fluxoCaixa;
    FluxoCaixa(int fluxoCaixa) { this.fluxoCaixa = fluxoCaixa; }
    public int getValue() { return fluxoCaixa; }

    public String print() { return super.toString(); }

    @Override
    public String toString() { return (fluxoCaixa + 1) + " - " + super.toString(); }

}
