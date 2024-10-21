package com.santacarolina.areas.categoria.frmCategoria;

import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.util.ViewInvoker;

public class FormController implements Controller {

    private FormView view;
    private FormModel model;

    public FormController(FormView view, FormModel model) {
        this.view = view;
        this.model = model;
        init();
    }

    private void init() {
        view.getFluxoCaixaComboBox().addActionListener(e -> fluxoComboBox_afterUpdate());
        view.getNumeroTextField().addFocusListener((AfterUpdateListener) e -> numeroTextField_afterUpdate());
        view.getNomeTextField().addFocusListener((AfterUpdateListener) e -> nomeTextField_afterUpdate());
        view.getAddButton().addActionListener(e -> addButton_onClick());
    }

    private Object addButton_onClick() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addButton_onClick'");
    }

    private void nomeTextField_afterUpdate() { model.setNomeCategoria(view.getNomeTextField().getText()); }
    private void numeroTextField_afterUpdate() { model.setNumeroEtiqueta(view.getNumeroTextField().getText()); }

    private void fluxoComboBox_afterUpdate() {
        FluxoCaixa fluxo = (FluxoCaixa) view.getFluxoCaixaComboBox().getSelectedItem();
        model.setFluxoCaixa(fluxo);
    }

    @Override
    public void showView() {
        ViewInvoker.showView(view.getDialog());
    } 

}

