package com.santacarolina.areas.relatorio;

import org.jdesktop.swingx.combobox.ListComboBoxModel;

import com.santacarolina.dao.PastaDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.util.ViewInvoker;

@SuppressWarnings("unchecked")
public class FormController implements Controller {

    private FormView view;
    private FormModel model;

    public FormController(FormView view, FormModel model) throws FetchFailException {
        this.view = view;
        this.model = model;
        model.addPropertyChangeListener(view);
        init();
    }

    private void init() throws FetchFailException {
        view.getListaPasta().setModel(new ListComboBoxModel<>(new PastaDAO().findAll()));
    }

    @Override
    public void showView() {
        ViewInvoker.showView(view.getDialog());
    }
    
}
