package com.santacarolina.enums;

public enum TipoMovimento {

    COMUM(""),
    ESTORNO("ESTORNO BANCÁRIO"),
    TRANSFERENCIA("TRANSFERÊNCIA ENTRE CONTAS"),
    PAGAMENTO_INDEVIDO("PAGAMENTO INDEVIDO"),
    APLICACAO("APLICAÇÃO/RESGATE"),
    TARIFA_BANCARIA("TARIFAS E TRIBUTOS BANCÁRIOS"),
    SEGURO_BANCARIO("SEGUROS E OUTROS PRODUTOS BANCÁRIOS"),
    ACOES("AÇÕES E DERIVATIVOS"),
    EMPRESTIMO("OPERAÇÕES DE TESOURARIA - EMPRÉSTIMO"),
    VALOR_DESC("VALOR NÃO CONCILIADO");

    private final String tipoMovimento;
    TipoMovimento(String tipoMovimento) { this.tipoMovimento = tipoMovimento; }
    public String getTipoMovimento() { return tipoMovimento; }

    @Override
    public String toString() { return tipoMovimento; }

}
