package com.santacarolina.areas.bancario.dadoBancario.frmDado;

import org.jdesktop.swingx.combobox.ListComboBoxModel;

import com.santacarolina.dao.BancoDAO;
import com.santacarolina.dao.ContatoDAO;
import com.santacarolina.dao.DadoDAO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.model.Banco;
import com.santacarolina.model.Contato;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;

@SuppressWarnings("unchecked")
public class FormController implements Controller {

    private FormView view;
    private FormModel model;

    public FormController (FormView view, FormModel model) throws FetchFailException {
        this.view = view;
        this.model = model;
        initComponents();
    }

    private void initComponents() throws FetchFailException {
        view.getContactComboBox().setModel(new ListComboBoxModel<>(new ContatoDAO().findAll()));
        view.getBankComboBox().setModel(new ListComboBoxModel<>(new BancoDAO().findAll()));

        view.getContactComboBox().addActionListener(e -> contactComboBox_afterUpdate());
        view.getBankComboBox().addActionListener(e -> bankComboBox_afterUpdate());
        view.getAgencyTextField().addFocusListener((AfterUpdateListener) e -> agencyTextField_afterUpdate());
        view.getContaTextField().addFocusListener((AfterUpdateListener) e -> contaTextField_afterUpdate());
        view.getAddAccount().addActionListener(e -> addAccount_onClick());
    }

    private void addAccount_onClick() {
        try {
            if (!DadoBancarioValidator.validate(model)) {
                model.getDadoBancario().setId(model.getIdOriginal());
                return;
            } 
            new DadoDAO().save(model.getDadoBancario());
            OptionDialog.showSuccessSaveMessage();
            view.getDialog().dispose();
        } catch (FetchFailException | SaveFailException | DeleteFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    private void bankComboBox_afterUpdate() {
        Banco banco = (Banco) view.getBankComboBox().getSelectedItem();
        model.setBanco(banco);
    }

    private void contactComboBox_afterUpdate() {
        Contato contato = (Contato) view.getContactComboBox().getSelectedItem();
        model.setContato(contato);
    }

    private void contaTextField_afterUpdate() { model.setNumConta(view.getContaTextField().getText()); }
    private void agencyTextField_afterUpdate() { model.setAgencia(view.getAgencyTextField().getText()); }

    @Override
    public void showView() { ViewInvoker.showView(view.getDialog()); }

}
