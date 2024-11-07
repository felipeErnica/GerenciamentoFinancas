package com.santacarolina.areas.bancario.pix.frmPix;

import java.awt.EventQueue;

import org.jdesktop.swingx.combobox.EnumComboBoxModel;
import org.jdesktop.swingx.combobox.ListComboBoxModel;

import com.santacarolina.areas.bancario.dadoBancario.frmDado.DadoForm;
import com.santacarolina.dao.ContatoDAO;
import com.santacarolina.dao.PixDAO;
import com.santacarolina.enums.TipoPix;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.model.Contato;
import com.santacarolina.model.DadoBancario;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.ViewInvoker;

@SuppressWarnings("unchecked")
public class FormController implements Controller {

    private FormView view;
    private FormModel model;

    public FormController(FormView view, FormModel model) throws FetchFailException {
        this.view = view;
        this.model = model;
        initComponents();
    }

    private void initComponents() throws FetchFailException {
        view.getContatoComboBox().setModel(new ListComboBoxModel<>(new ContatoDAO().findAll()));
        view.getTipoPixComboBox().setModel(new EnumComboBoxModel<>(TipoPix.class));
        view.getContatoComboBox().addActionListener(e -> contatoComboBox_afterUpdate());
        view.getContaComboBox().addActionListener(e -> contaComboBox_afterUpdate());
        view.getTipoPixComboBox().addActionListener(e -> tipoPix_afterUpdate());
        view.getChaveTextField().addFocusListener((AfterUpdateListener) e -> chaveField_afterUpdate());
        view.getAddButton().addActionListener(e -> addButton_onClick());
        view.getAddDadoBancarioButton().addActionListener(e -> addContaBancaria_onClick());
        view.getContaCheckBox().addActionListener(e -> contaCheckBox_onClick());
    }

    private void addContaBancaria_onClick() {
        DadoForm.openNew();
        try {
            model.refreshContaList();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    private void addButton_onClick() {
        EventQueue.invokeLater(() -> {
            try {
                if (!PixValidator.validate(model)) return;
                new PixDAO().save(model.getChavePix());
                view.getDialog().dispose();
            } catch (FetchFailException | SaveFailException e) {
                CustomErrorThrower.throwError(e);
            }
        });
    }

    private void chaveField_afterUpdate() {  model.setChave(view.getChaveTextField().getText()); }
    private void contaCheckBox_onClick() { model.setContaSelected(view.getContaCheckBox().isSelected()); }

    private void tipoPix_afterUpdate() {
        TipoPix t = (TipoPix) view.getTipoPixComboBox().getSelectedItem();
        model.setTipoPix(t);
    }

    private void contaComboBox_afterUpdate() {
        DadoBancario d = (DadoBancario) view.getContaComboBox().getSelectedItem();
        model.setDadoBancario(d);
    }

    private void contatoComboBox_afterUpdate() {
        try {
            Contato c = (Contato) view.getContatoComboBox().getSelectedItem();
            model.setContato(c);
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    @Override
    public void showView() { ViewInvoker.showView(view.getDialog()); }

}

