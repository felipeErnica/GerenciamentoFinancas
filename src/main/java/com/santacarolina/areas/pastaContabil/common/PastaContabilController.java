package com.santacarolina.areas.pastaContabil.common;

import org.jdesktop.swingx.combobox.ListComboBoxModel;

import com.santacarolina.dao.ContaDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.ViewInvoker;

public class PastaContabilController {

    private PastaContabilView view;
    private PastaContabilModel model;

    public PastaContabilController(PastaContabilView view, PastaContabilModel model) throws FetchFailException {
        this.view = view;
        this.model = model;
        init();
    }

    private void init() throws FetchFailException {
        view.getBankAccountComboBox().setModel(new ListComboBoxModel<>(new ContaDAO().findAll()));
    }

    public void showView() { ViewInvoker.showView(view.getDialog()); }
     
}

