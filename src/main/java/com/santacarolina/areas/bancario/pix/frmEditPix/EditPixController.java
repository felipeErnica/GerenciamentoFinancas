package com.santacarolina.areas.bancario.pix.frmEditPix;

import com.santacarolina.areas.bancario.pix.formModel.PixFormModel;
import com.santacarolina.dao.ContatoDao;
import com.santacarolina.dao.PixDao;
import com.santacarolina.enums.TipoPix;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.model.beans.ChavePix;
import com.santacarolina.model.beans.Contato;
import com.santacarolina.model.beans.DadoBancario;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdesktop.swingx.combobox.EnumComboBoxModel;
import org.jdesktop.swingx.combobox.ListComboBoxModel;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class EditPixController implements Controller {

    private final Logger logger = LogManager.getLogger();

    private EditPixView view;
    private PixFormModel model;

    public EditPixController(EditPixView view, PixFormModel model) throws FetchFailException {
        this.view = view;
        this.model = model;
        initComponents();
    }

    private void initComponents() throws FetchFailException {
        view.getContatoComboBox().setModel(new ListComboBoxModel<>(new ContatoDao().findAll()));
        view.getContatoComboBox().setSelectedItem(null);
        view.getTipoPixComboBox().setModel(new EnumComboBoxModel<>(TipoPix.class));
        view.getTipoPixComboBox().setSelectedItem(null);
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

                if (model.getDadoBancario() != null) {
                    if (model.getDadoBancario().getPixId() != model.getChavePix().getId()) {
                        if (!replaceAccountPix()) return;
                    }
                }

                Optional<ChavePix> pixRepetido = new PixDao().getByChave(model.getChavePix().getChave());
                if (pixRepetido.isPresent()) {
                    if (model.getChavePix().getId() != pixRepetido.get().getId()) {
                        if (!changeRepeatingPix(pixRepetido.get())) return;
                    }
                }

                new PixDao().save(model.getChavePix());
                OptionDialog.showSuccessSaveMessage();
                model.setUpdated(true);
                view.getDialog().dispose();
            } catch (FetchFailException | SaveFailException e) {
                CustomErrorThrower.throwError(e);
            }
        });
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

    private boolean replaceAccountPix() {
        int respMsg = OptionDialog.showOptionDialog(
                "Esta conta já possui uma chave pix! Deseja substituí-la?",
                "ATENÇÃO: Chave já existe!"
        );
        return respMsg == JOptionPane.YES_OPTION;
    }

    private void chaveField_afterUpdate() { model.setChave(view.getChaveTextField().getText()); }
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
            if (c != null) model.setContato(c);
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    @Override
    public void showView() { ViewInvoker.showView(view.getDialog()); }

}

