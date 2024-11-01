package com.santacarolina.areas.categoria.frmManageCategoria;

import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.interfaces.DocumentChangeListener;

/**
 * FilterController
 */
public class FilterController {

    private FilterView view;
    private FilterModel model;

    public FilterController(FilterView view, FilterModel model) {
        this.view = view;
        this.model = model;
        init();
    }

    private void init() {
        view.getCategoriaField().getDocument().addDocumentListener((DocumentChangeListener) e -> categoriaField_onChange());
        view.getFluxoCaixaField().addActionListener(e -> fluxoCaixaField_onUpdate());
    }

    private void categoriaField_onChange() { model.setNomeCategoria(view.getCategoriaField().getText()); }

    private void fluxoCaixaField_onUpdate() {
        FluxoCaixa fluxoCaixa = (FluxoCaixa) view.getFluxoCaixaField().getSelectedItem();
        model.setFluxoCaixa(fluxoCaixa);
    }

}
