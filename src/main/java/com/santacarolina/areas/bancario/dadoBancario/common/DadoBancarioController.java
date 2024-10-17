package com.santacarolina.areas.bancario.dadoBancario.common;

import org.jdesktop.swingx.combobox.EnumComboBoxModel;
import org.jdesktop.swingx.combobox.ListComboBoxModel;

import com.santacarolina.dao.BancoDAO;
import com.santacarolina.dao.ContatoDAO;
import com.santacarolina.dao.DadoDAO;
import com.santacarolina.dao.PixDAO;
import com.santacarolina.enums.TipoPix;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.model.Banco;
import com.santacarolina.model.Contato;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;

public class DadoBancarioController {

    private DadoBancarioValidator validator;
    private DadoBancarioFormView view;
    private DadoBancarioFormModel model;

    public DadoBancarioController (DadoBancarioFormView view, DadoBancarioFormModel model) throws FetchFailException {
        this.view = view;
        this.model = model;
        validator = new DadoBancarioValidator(model);
        initComponents();
    }

    private void initComponents() throws FetchFailException {
        view.getContactComboBox().setModel(new ListComboBoxModel<>(new ContatoDAO().findAll()));
        view.getBankComboBox().setModel(new ListComboBoxModel<>(new BancoDAO().findAll()));
        view.getPixTypeComboBox().setModel(new EnumComboBoxModel<>(TipoPix.class));

        view.getContactComboBox().addActionListener(e -> contactComboBox_afterUpdate());
        view.getBankComboBox().addActionListener(e -> bankComboBox_afterUpdate());
        view.getAgencyTextField().addFocusListener((AfterUpdateListener) e -> agencyTextField_afterUpdate());
        view.getContaTextField().addFocusListener((AfterUpdateListener) e -> contaTextField_afterUpdate());
        view.getPixCheckBox().addActionListener(e -> pixCheckBox_onClick());
        view.getPixTypeComboBox().addActionListener(e -> pixTypeComboBox_afterUpdate());
        view.getPixKey().addFocusListener((AfterUpdateListener) e -> pixKey_afterUpdate());
        view.getAddAccount().addActionListener(e -> addAccount_onClick());
    }

    private void addAccount_onClick() {
        try {
            if (!validator.validate()) return;
            if (validator.dadoExists()) return;
            if (validator.pixExists()) return;
            if (model.getChavePix() == null) {
                if (model.getDadoBancario().getPixId() != null) new PixDAO().deleteById(model.getDadoBancario().getPixId());
            } else {
                model.getDadoBancario().addChavePix(model.getChavePix());
            }
            model.setDadoSaved(new DadoDAO().save(model.getDadoBancario()));
            OptionDialog.showSuccessSaveMessage();
            view.getDialog().dispose();
        } catch (FetchFailException | SaveFailException | DeleteFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    private void pixTypeComboBox_afterUpdate() {
        TipoPix t = (TipoPix) view.getPixTypeComboBox().getSelectedItem();
        model.setTipoPix(t);
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
    private void pixKey_afterUpdate() { model.setChave(view.getPixKey().getText()); }
    private void pixCheckBox_onClick() { model.setPixEnabled(view.getPixCheckBox().isSelected()); }

}
