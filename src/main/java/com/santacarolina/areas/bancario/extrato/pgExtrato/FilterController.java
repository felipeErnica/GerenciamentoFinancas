package com.santacarolina.areas.bancario.extrato.pgExtrato;

import com.santacarolina.interfaces.AfterUpdateListener;

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
        view.getDataFim().addFocusListener((AfterUpdateListener) e -> dataFim_onChange());
        view.getDataInicio().addFocusListener((AfterUpdateListener) e -> dataInicio_onChange());
    }

    private void dataFim_onChange() { model.setDataFim(view.getDataFim().getText()); }
    private void dataInicio_onChange() { model.setDataInicio(view.getDataInicio().getText()); }

}
