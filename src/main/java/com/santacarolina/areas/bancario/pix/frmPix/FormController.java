package com.santacarolina.areas.bancario.pix.frmPix;

import java.awt.EventQueue;
import java.util.Optional;

import javax.swing.JOptionPane;

import org.jdesktop.swingx.combobox.EnumComboBoxModel;
import org.jdesktop.swingx.combobox.ListComboBoxModel;

import com.santacarolina.dao.ContatoDAO;
import com.santacarolina.dao.PixDAO;
import com.santacarolina.enums.TipoPix;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.model.ChavePix;
import com.santacarolina.model.Contato;
import com.santacarolina.model.DadoBancario;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
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
        view.getContaCheckBox().addActionListener(e -> contaCheckBox_onClick());
    }

    private void addButton_onClick() {
        EventQueue.invokeLater(() -> {
            try {
                if (model.updatingNotAllowed()) return;
                if (model.getDadoBancario() != null && model.getDadoBancario().getChavePix() != null) {
                    if (!replaceAccountPix()) return;
                }
                Optional<ChavePix> pixRepetido = new PixDAO().getByChave(model.getChavePix().getChave());
                if (pixRepetido.isPresent()) {
                    if (!changeRepeatingPix(pixRepetido.get())) return;
                }
                new PixDAO().save(model.getChavePix());
                view.getDialog().dispose();
            } catch (FetchFailException | SaveFailException e) {
                CustomErrorThrower.throwError(e);
            }
        });
    }

    private boolean replaceAccountPix() {
        int respMsg = OptionDialog.showOptionDialog(
                "Esta conta já possui uma chave pix! Deseja substituí-la?",
                "ATENÇÃO: Chave já existe!"
        );
        return respMsg == JOptionPane.YES_OPTION;
    }

    private boolean changeRepeatingPix(ChavePix c) {
        String msgConta;

        if (c.getDadoBancario() == null) msgConta = "";
        else msgConta = " e à conta " + c.getDadoBancario().getNumeroConta();

        int respMsg = OptionDialog.showOptionDialog(
                "Já existe um pix com esta chave! Pertence ao contato " +
                        c.getContato() + msgConta + ". " +
                "Deseja substituí-la por esta?",
                "ATENÇÃO: Chave já existe!" );
        if (respMsg == JOptionPane.YES_OPTION) {
            model.getChavePix().setId(c.getId());
            return true;
        } return false;
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

