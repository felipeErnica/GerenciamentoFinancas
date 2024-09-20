package com.santacarolina.areas.bancario.extrato.frmAddExtrato;

import com.santacarolina.interfaces.Controller;
import com.santacarolina.util.ViewInvoker;

public class AddExtratoController implements Controller {

    private AddExtratoView view;
    private AddExtratoModel model;

    public AddExtratoController(AddExtratoView view, AddExtratoModel model) {
        this.view = view;
        this.model = model;
        initComponents();
    }

    private void initComponents() {
        view.getTable().setModel(model.getTableModel().getBaseModel());
    }

    @Override
    public void showView() {
        ViewInvoker.showMaximizedView(view.getDialog());
    }

}
