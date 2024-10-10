package com.santacarolina.areas.homePage.graphData;

public class ExpenseCategory {

    private String classificacao;
    private double valor;

    public ExpenseCategory(String classificacao, double valor) {
        this.classificacao = classificacao;
        this.valor = valor;
    }

    public String getClassificacao() { return classificacao; }
    public double getValor() { return valor; }

}
