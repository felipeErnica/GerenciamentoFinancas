package com.santacarolina.areas.classificacao.frmManageClassificacao;

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
        view.getClassificacaoField().getDocument().addDocumentListener((DocumentChangeListener) e -> classificacaoField_onChange());
        view.getFluxoField().addActionListener(e -> fluxoField_onUpdate());
    }

    private void categoriaField_onChange() { model.setCategoria(view.getCategoriaField().getText()); }
    private void classificacaoField_onChange() { model.setClassificacao(view.getClassificacaoField().getText()); }
    
    private void fluxoField_onUpdate() {
        FluxoCaixa fluxoCaixa = (FluxoCaixa) view.getFluxoField().getSelectedItem();
        model.setFluxoCaixa(fluxoCaixa);
    }

}
