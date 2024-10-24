package com.santacarolina.areas.bancario.contaBancaria.frmContaBancaria;

import org.jdesktop.swingx.combobox.ListComboBoxModel;

import com.santacarolina.dao.BancoDAO;
import com.santacarolina.dao.ContaDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.model.Banco;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;

public class FormController implements Controller {

    private FormView view;
    private FormModel model;

    public FormController(FormView view, FormModel model) throws FetchFailException {
        this.view = view;
        this.model = model;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() throws FetchFailException {
        view.getBancoComboBox().setModel(new ListComboBoxModel<>(new BancoDAO().findAll()));
        view.getBancoComboBox().addActionListener(e -> bancoComboBox_afterUpdate());
        view.getAgenciaTextField().addFocusListener((AfterUpdateListener) e -> agenciaTextField_afterUpdate());
        view.getNumeroContaTextField().addFocusListener((AfterUpdateListener) e -> numeroContaTextField_afterUpdate());
        view.getApelidoContaTextField().addFocusListener((AfterUpdateListener) e -> apelidoContaTextField_afterUpdate());
        view.getAbreviacaoText().addFocusListener((AfterUpdateListener) e -> abreviacaoText_afterUpdate());
        view.getAddConta().addActionListener(e -> addConta_onClick());
    }

    private void bancoComboBox_afterUpdate() {
        Banco banco = (Banco) view.getBancoComboBox().getSelectedItem();
        model.setBanco(banco);
    }

    private void agenciaTextField_afterUpdate() { model.setAgencia(view.getAgenciaTextField().getText()); }
    private void numeroContaTextField_afterUpdate() { model.setNumeroConta(view.getNumeroContaTextField().getText()); }
    private void abreviacaoText_afterUpdate() { model.setAbreviacao(view.getAbreviacaoText().getText()); }
    private void apelidoContaTextField_afterUpdate() { model.setApelidoConta(view.getApelidoContaTextField().getText()); }

    private void addConta_onClick() {
        try {
            if (!new ContaValidator().validate(model)) return;
            new ContaDAO().save(model.getContaBancaria());
            OptionDialog.showSuccessSaveMessage();
            view.getDialog().dispose();
        } catch (SaveFailException | FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    @Override
    public void showView() { ViewInvoker.showView(view.getDialog()); }

}
