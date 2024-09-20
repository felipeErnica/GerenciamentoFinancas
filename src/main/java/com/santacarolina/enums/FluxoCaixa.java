package com.santacarolina.enums;

public enum FluxoCaixa {
    DESPESA (0),
    RECEITA (1);

    private int fluxoCaixa;
    FluxoCaixa(int fluxoCaixa) { this.fluxoCaixa = fluxoCaixa; }
    public int getFluxoCaixa() { return fluxoCaixa; }

}
