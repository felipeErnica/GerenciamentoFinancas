package com.santacarolina.areas.documentos.frmDoc.frmAddTed;

import java.awt.EventQueue;

import org.jdesktop.swingx.combobox.ListComboBoxModel;

import com.santacarolina.areas.bancario.dadoBancario.frmAddDado.AddDadoBancarioForm;
import com.santacarolina.dao.ContatoDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.OnSelectListener;
import com.santacarolina.model.Contato;
import com.santacarolina.model.DadoBancario;
import com.santacarolina.util.ViewInvoker;

public class FormController {

    private FormView view;
    private FormModel model;

    public FormController(FormView view, FormModel model) throws FetchFailException {
        this.view = view;
        this.model = model;
        init();
    }

    private void init() throws FetchFailException {
        view.getContatoComboBox().setModel(new ListComboBoxModel<>(new ContatoDAO().findAll()));
        view.getAgenciaTextField().addFocusListener((OnSelectListener) e -> agenciaTextField_onFocus());
        view.getBancoTextField().addFocusListener((OnSelectListener) e -> bancoTextField_onFocus());

        view.getAddButton().addActionListener(e -> addButton_onClick());
        view.getContatoComboBox().addActionListener(e -> contatoComboBox_afterUpdate());
        view.getContaComboBox().addActionListener(e -> contaComboBox_afterUpdate());
        view.getAddNewAccount().addActionListener(e -> addNewAccount_onClick());
    }

    private void addButton_onClick() {
        model.getDuplicataList().forEach(d -> d.setDadoBancario(model.getDado()));
        view.getDialog().dispose();
    }

    private void addNewAccount_onClick() { EventQueue.invokeLater(() -> new AddDadoBancarioForm()); }
    private void bancoTextField_onFocus() { view.getBancoTextField().selectAll(); }
    private void agenciaTextField_onFocus() { view.getAgenciaTextField().selectAll(); }

    private void contaComboBox_afterUpdate() {
        DadoBancario dado = (DadoBancario) view.getContaComboBox().getSelectedItem();
        model.setDado(dado);
    }

    private void contatoComboBox_afterUpdate() {
        Contato contato = (Contato) view.getContatoComboBox().getSelectedItem();
        model.setContato(contato);
    }

    public void showView() { ViewInvoker.showView(view.getDialog()); }

}
