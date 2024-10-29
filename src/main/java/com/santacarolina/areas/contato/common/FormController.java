package com.santacarolina.areas.contato.common;

import com.santacarolina.dao.ContatoDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;

public class FormController implements Controller {

    private FormView view;
    private FormModel model;

    public FormController(FormView view, FormModel model) {
        this.view = view;
        this.model = model;
        initComponents();
    }

    private void initComponents() {
        view.getDocCheckBox().addActionListener(e -> docCheckBox_onClick());
        view.getNameTextField().addFocusListener((AfterUpdateListener) e -> nomeTextField_afterUpdate());
        view.getCpfTextField().addFocusListener((AfterUpdateListener) e -> cpfTextField_afterUpdate());
        view.getCnpjTextField().addFocusListener((AfterUpdateListener) e -> cnpjTextField_afterUpdate());
        view.getIeTextField().addFocusListener((AfterUpdateListener) e -> ieTextField_afterUpdate());
        view.getAddContact().addActionListener(e -> addButton_onClick());
    }

    private void nomeTextField_afterUpdate() { model.setName(view.getNameTextField().getText()); }
    private void ieTextField_afterUpdate() { model.setIe(view.getIeTextField().getText()); }
    private void cnpjTextField_afterUpdate() { model.setCnpj(view.getCnpjTextField().getText()); }
    private void cpfTextField_afterUpdate() { model.setCpf(view.getCpfTextField().getText()); }
    private void docCheckBox_onClick() { model.setDocsEnabled(view.getDocCheckBox().isSelected()); }

    private void addButton_onClick() {
        try {
            if (!ContatoValidator.validate(model)) return;
            new ContatoDAO().save(model.getContato());
            OptionDialog.showSuccessSaveMessage();
            view.getDialog().dispose();
        } catch (SaveFailException | FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    @Override
    public void showView() { ViewInvoker.showView(view.getDialog()); }

}
