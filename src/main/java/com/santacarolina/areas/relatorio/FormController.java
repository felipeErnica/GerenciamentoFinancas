package com.santacarolina.areas.relatorio;

import java.awt.EventQueue;
import java.io.IOException;

import org.jdesktop.swingx.combobox.ListComboBoxModel;

import com.santacarolina.dao.PastaDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;

@SuppressWarnings("unchecked")
public class FormController implements Controller {

    private FormView view;
    private FormModel model;

    public FormController(FormView view, FormModel model) throws FetchFailException {
        this.view = view;
        this.model = model;
        view.getRelatorioButton().addActionListener(e -> relatorioButton_onClick());
        view.getDataFim().addFocusListener((AfterUpdateListener) e -> dataFim_onLostFocus());
        view.getDataInicio().addFocusListener((AfterUpdateListener) e -> dataInicio_onLostFocus());
        model.addPropertyChangeListener(view);
        init();
    }

    private void dataFim_onLostFocus() { model.setDataFim(view.getDataFim().getText()); }
    private void dataInicio_onLostFocus() { model.setDataInicio(view.getDataInicio().getText()); }

    private void relatorioButton_onClick() {
        EventQueue.invokeLater(() -> {
            try {
                ExportExcel.exportToExcel(model.getListaFiltrada());
            } catch (IOException e) {
                OptionDialog.showErrorDialog("Não foi possível exportar o relatório", "ERRO: Exportação de Relatório");
            }
        });
    }

    private void init() throws FetchFailException {
        view.getListaPasta().setModel(new ListComboBoxModel<>(new PastaDAO().findAll()));
    }

    @Override
    public void showView() { ViewInvoker.showView(view.getDialog()); }
    
}
