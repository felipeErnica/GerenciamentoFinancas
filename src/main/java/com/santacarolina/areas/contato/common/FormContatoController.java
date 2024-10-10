package com.santacarolina.areas.contato.common;

import com.santacarolina.dao.ContatoDAO;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.model.Contato;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FormContatoController implements Controller {

    private final Logger logger = LogManager.getLogger();

    private IContatoController child;
    private ContatoDAO dao;
    private ContatoValidator validator;
    private FormContatoView view;
    private FormContatoModel model;

    public FormContatoController(IContatoController child) {
        this.child = child;
        this.view = child.getView();
        this.model = child.getModel();
        this.dao = new ContatoDAO();
        this.validator = new ContatoValidator(model);
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
            if (!validator.validate()) return;
            if (child.nameExists()) return;
            if (child.docsExists()) return;
            model.setContatoSaved(dao.save(model.getContato()));
            OptionDialog.showSuccessSaveMessage();
            view.getDialog().dispose();
        } catch (SaveFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    public ContatoDAO getDao() { return dao; }

    @Override
    public void showView() { ViewInvoker.showView(view.getDialog()); }

}