package com.santacarolina.enums;

public enum TipoPagamento {

    CARTAO_DEBITO("CARTÃO DE DÉBITO"),
    CARTAO_CREDITO("CARTÃO DE CRÉDITO"),
    PIX("PIX"),
    TED("TED"),
    BOLETO("BOLETO"),
    DINHEIRO_VIVO("DINHEIRO VIVO"),
    DEBITO_CONTA("DÉBITO EM CONTA");

    private final String tipoPagamento;

    TipoPagamento(String tipoPagamento) { this.tipoPagamento = tipoPagamento; }
    public String getValue() { return tipoPagamento; }

    @Override
    public String toString() { return tipoPagamento; }
}
