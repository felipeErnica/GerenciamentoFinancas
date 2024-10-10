package com.santacarolina.areas.bancario.pix.frmAddPix;

import com.formdev.flatlaf.FlatClientProperties;
import com.santacarolina.areas.bancario.pix.formModel.PixFormModel;
import com.santacarolina.dao.ContatoDAO;
import com.santacarolina.dao.PixDAO;
import com.santacarolina.enums.TipoPix;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.interfaces.FormListener;
import com.santacarolina.model.ChavePix;
import com.santacarolina.model.Contato;
import com.santacarolina.model.DadoBancario;
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

public class AddPixController implements Controller, FormListener {

    private final Logger logger = LogManager.getLogger();

    private AddPixView view;
    private PixFormModel model;

    public AddPixController(AddPixView view, PixFormModel model) throws FetchFailException {
        this.view = view;
        this.model = model;
        initComponents();
    }

    private void initComponents() throws FetchFailException {
        view.getContatoComboBox().setModel(new ListComboBoxModel<>(new ContatoDAO().findAll()));
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

    private void chaveField_afterUpdate() {
        EventQueue.invokeLater(() -> model.setChave(view.getChaveTextField().getText()));
    }
    private void contaCheckBox_onClick() {
        EventQueue.invokeLater(() -> model.setContaSelected(view.getContaCheckBox().isSelected()));
    }

    private void tipoPix_afterUpdate() {
        EventQueue.invokeLater(() -> {
            TipoPix t = (TipoPix) view.getTipoPixComboBox().getSelectedItem();
            model.setTipoPix(t);
        });
    }

    private void contaComboBox_afterUpdate() {
        EventQueue.invokeLater(() -> {
            DadoBancario d = (DadoBancario) view.getContaComboBox().getSelectedItem();
            model.setDadoBancario(d);
        });
    }

    private void contatoComboBox_afterUpdate() {
        EventQueue.invokeLater(() -> {
            try {
                Contato c = (Contato) view.getContatoComboBox().getSelectedItem();
                if (c != null) model.setContato(c);
            } catch (FetchFailException e) {
                CustomErrorThrower.throwError(e);
            }
        });
    }

    @Override
    public void showView() {
        try {
            initComponents();
            ViewInvoker.showView(view.getDialog());
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    @Override
    public void update(String property) {
        switch (property) {
            case PixFormModel.CONTA -> {
                view.getContaComboBox().setEnabled(model.isContaEnabled());
                view.getContaComboBox().removeAllItems();
                if (model.isContaEnabled()) {
                    model.getContaList().forEach(d -> view.getContaComboBox().addItem(d));
                    view.getContaComboBox().setSelectedItem(model.getDadoBancario());
                }
            }
            case PixFormModel.TIPO_PIX -> {
                view.getTipoPixComboBox().setEnabled(model.isDadosEnabled());
                view.getTipoPixComboBox().setSelectedItem(model.getTipoPix());
            }
            case PixFormModel.BANCO -> view.getBancoTextField().setText(model.getBanco());
            case PixFormModel.CHAVE -> {
                view.getChaveTextField().setText(model.getChave());
                view.getChaveTextField().setEnabled(model.isDadosEnabled());
                if (model.getChave() != null && !model.getChave().isEmpty()) {
                    if (model.isInvalidFormat()) view.getChaveTextField().putClientProperty(FlatClientProperties.OUTLINE, "error");
                } else view.getChaveTextField().putClientProperty(FlatClientProperties.OUTLINE, null);
            }
        }
    }

}

