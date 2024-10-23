package com.santacarolina.areas.homePage;

import com.santacarolina.model.PastaContabil;

public class FormController  {

    private FormView view;
    private FormModel model;

    public FormController(FormView view, FormModel model) {
        this.view = view;
        this.model = model;
        init();
    }

    private void init() {
        view.getPastaComboBox().addActionListener(e -> pastaComboBox_afterUpdate());
    }

    private void pastaComboBox_afterUpdate() {
        PastaContabil pastaContabil = (PastaContabil) view.getPastaComboBox().getSelectedItem();
        model.setPastaContabil(pastaContabil);
    }

}
