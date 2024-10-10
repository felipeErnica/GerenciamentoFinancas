package com.santacarolina.areas.pastaContabil.frmAddContaBancaria;

import com.santacarolina.dao.BancoDAO;
import com.santacarolina.dao.ContaDAO;
import com.santacarolina.enums.Replacement;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.interfaces.FormListener;
import com.santacarolina.model.Banco;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;
import org.jdesktop.swingx.combobox.ListComboBoxModel;

import java.awt.*;

public class AddContaController implements Controller, FormListener {

    private AddContaView view;
    private AddContaModel model;

    public AddContaController(AddContaView view, AddContaModel model) throws FetchFailException {
        this.view = view;
        this.model = model;
        initComponents();
    }

    private void initComponents() throws FetchFailException {
        view.getBancoComboBox().setModel(new ListComboBoxModel<>(new BancoDAO().findAll()));
        view.getBancoComboBox().addActionListener(e -> bancoComboBox_afterUpdate());
        view.getAgenciaTextField().addFocusListener((AfterUpdateListener) e -> agenciaTextField_afterUpdate());
        view.getNumeroContaTextField().addFocusListener((AfterUpdateListener) e -> numeroContaTextField_afterUpdate());
        view.getApelidoContaTextField().addFocusListener((AfterUpdateListener) e -> apelidoContaTextField_afterUpdate());
        view.getAddConta().addActionListener(e -> addConta_onClick());
    }

    private void bancoComboBox_afterUpdate() {
        Banco banco = (Banco) view.getBancoComboBox().getSelectedItem();
        model.setBanco(banco);
    }

    private void agenciaTextField_afterUpdate() { model.setAgencia(view.getAgenciaTextField().getText()); }
    private void numeroContaTextField_afterUpdate() { model.setNumeroConta(view.getNumeroContaTextField().getText()); }

    private void apelidoContaTextField_afterUpdate() {
        EventQueue.invokeLater(() -> model.setApelidoConta(view.getApelidoContaTextField().getText()));
    }

    private void addConta_onClick() {
        EventQueue.invokeLater(() -> {
            try {
                if (model.updatingNotAllowed()) return;
                if (model.replaceContaRepetida() == Replacement.REPLACE_REJECTED) return;
                new ContaDAO().save(model.getContaBancaria());
                OptionDialog.showSuccessSaveMessage();
                view.getDialog().dispose();
            } catch (SaveFailException e) {
                CustomErrorThrower.throwError(e);
            }
        });
    }

    @Override
    public void showView() { ViewInvoker.showView(view.getDialog()); }

    @Override
    public void update(String property) { view.getApelidoContaTextField().setText(model.getApelidoConta()); }

}
