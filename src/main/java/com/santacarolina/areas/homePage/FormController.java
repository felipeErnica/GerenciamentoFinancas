package com.santacarolina.areas.homePage;

import java.time.LocalDate;

import org.jdesktop.swingx.combobox.ListComboBoxModel;

import com.santacarolina.dao.PastaDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.model.PastaContabil;
import com.santacarolina.util.StringConversor;

public class FormController  {

    private FormView view;
    private FormModel model;

    public FormController(FormView view, FormModel model) throws FetchFailException {
        this.view = view;
        this.model = model;
        init();
    }

    @SuppressWarnings("unchecked")
    private void init() throws FetchFailException {
        view.getPastaComboBox().setModel(new ListComboBoxModel<>(new PastaDAO().findAll()));
        view.getPastaComboBox().addActionListener(e -> pastaComboBox_afterUpdate());
        view.getDataFimText().addFocusListener((AfterUpdateListener) e -> dataFimText_afterUpdate());
        view.getDataInicioText().addFocusListener((AfterUpdateListener) e -> dataInicioText_afterUpdate());
    }

    private void dataFimText_afterUpdate() {
        LocalDate dataFim = StringConversor.transformDate(view.getDataFimText().getText());
        model.setDataFim(dataFim);
    }

    private void dataInicioText_afterUpdate() {
        LocalDate dataInicio = StringConversor.transformDate(view.getDataInicioText().getText());
        model.setDataInicio(dataInicio);
    }

    private void pastaComboBox_afterUpdate() {
        PastaContabil pastaContabil = (PastaContabil) view.getPastaComboBox().getSelectedItem();
        model.setPastaContabil(pastaContabil);
    }

}
