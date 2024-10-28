package com.santacarolina.areas.categoria.frmCategoria;

import com.santacarolina.dao.CategoriaDAO;
import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.util.CustomErrorThrower;
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

    private void addButton_onClick() {
        try {
            if (!CategoriaValidator.validate(model)) return;
            new CategoriaDAO().save(model.getCategoriaContabil());
            view.getDialog().dispose();
        } catch (FetchFailException | SaveFailException e) {
            CustomErrorThrower.throwError(e);
        }
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

