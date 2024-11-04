package com.santacarolina.areas.duplicatas.common;

import com.santacarolina.enums.TipoPagamento;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.DocumentChangeListener;
import com.santacarolina.model.ContaBancaria;

/**
 * FilterController
 */
public class FilterController {

    private FilterView view;
    private FilterModel model;

    public FilterController(FilterView view, FilterModel model) {
        this.view = view;
        this.model = model;
        model.addPropertyChangeListener(view);
        init();
    }

    private void init() {
        view.getDataFim().addFocusListener((AfterUpdateListener) e -> dataFim_onLostFocus());
        view.getDataInicio().addFocusListener((AfterUpdateListener) e -> dataInicio_onLostFocus());
        view.getTipoPagamentoField().addActionListener(e -> tipoPagamento_onAction());
        view.getContaField().addActionListener(e -> conta_onAction());
        view.getEmissorField().getDocument().addDocumentListener((DocumentChangeListener) e -> emissorField_onChange());
    }

    private void dataFim_onLostFocus() { model.setDataFim(view.getDataFim().getText()); }
    private void dataInicio_onLostFocus() { model.setDataInicio(view.getDataInicio().getText()); }
    private void emissorField_onChange() { model.setEmissor(view.getEmissorField().getText()); }

    private void tipoPagamento_onAction() {
        TipoPagamento tipoPagamento = (TipoPagamento) view.getTipoPagamentoField().getSelectedItem();
        model.setTipoPagamento(tipoPagamento);
    }

    private void conta_onAction() {
        ContaBancaria contaBancaria = (ContaBancaria) view.getContaField().getSelectedItem();
        model.setContaBancaria(contaBancaria);
    }

}
