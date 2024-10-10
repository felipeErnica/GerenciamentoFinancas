package com.santacarolina.areas.homePage;

import com.santacarolina.areas.homePage.graphData.ExpenseIncomeSerie;

public class FormController  {

    private FormView view;
    private FormModel model;

    public FormController(FormView view, FormModel model) {
        this.view = view;
        this.model = model;
        init();
    }

    private void init() {
        ExpenseIncomeSerie ei = model.getResultingLine();
        view.getResultsChart().addSeries(ei.getName(), ei.getDateList(), ei.getValueList());
        model.getPieSeriesList().forEach(ec -> view.getExpenseCategoryChart().addSeries(ec.getClassificacao(), ec.getValor()));
        model.getExpenseIncomeSeries().forEach(es -> view.getExpenseIncomeChart().addSeries(es.getName(),es.getDateList(),es.getValueList()));
        view.getExpenseIncomeChart().getStyler().setYAxisMax(model.getExpenseIncomeLimit()*1.4);
    }


}
