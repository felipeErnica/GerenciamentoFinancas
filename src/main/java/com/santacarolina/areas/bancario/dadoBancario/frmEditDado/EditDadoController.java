package com.santacarolina.areas.bancario.dadoBancario.frmEditDado;

import com.formdev.flatlaf.FlatClientProperties;
import com.santacarolina.dao.BancoDAO;
import com.santacarolina.dao.ContatoDao;
import com.santacarolina.dao.DadoDao;
import com.santacarolina.dao.PixDao;
import com.santacarolina.enums.TipoPix;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.interfaces.FormListener;
import com.santacarolina.model.beans.Banco;
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

public class EditDadoController implements Controller, FormListener {

    private final Logger logger = LogManager.getLogger();

    private EditDadoView view;
    private EditDadoModel model;

    public EditDadoController(EditDadoView view, EditDadoModel model) throws FetchFailException {
        this.view = view;
        this.model = model;
        initComponents();
    }

    private void initComponents() throws FetchFailException {
        view.getContactComboBox().setModel(new ListComboBoxModel<>(new ContatoDao().findAll()));
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
        EventQueue.invokeLater(() -> {
            try {
                if (model.updatingNotAllowed()) return;
                Optional<DadoBancario> optional = new DadoDao().getEqual(model.getDadoBancario());
                ChavePix pixEncontrado = model.getChavePix() != null ? new PixDao().getByChave(model.getChavePix().getChave()).orElse(null) : null;
                if (optional.isPresent() && optional.get().getId() != model.getDadoBancario().getId()) {
                    if (!replaceDado(optional.get())) return;
                }
                if (pixEncontrado != null && pixEncontrado.getId() != model.getDadoBancario().getPixId()) {
                    if (!replacePix(pixEncontrado)) return;
                }
                if (model.deleteOriginalPix()) new PixDao().deleteById(model.getPixIdOrginal());
                new DadoDao().save(model.getDadoBancario());
                OptionDialog.showSuccessSaveMessage();
                view.getDialog().dispose();
            } catch (DeleteFailException | FetchFailException | SaveFailException e) {
                CustomErrorThrower.throwError(e);
            }
        });
    }

    private boolean replaceDado(DadoBancario encontrado)  {
        try {
            int result = OptionDialog.showOptionDialog(
                    "Esta conta já existe e pertence a " + encontrado.getContato() + "! Deseja substituí-la por esta?",
                    "ATENÇÃO: Conta já existe!");
            if (result != JOptionPane.YES_OPTION) return false;
            model.getDadoBancario().setId(encontrado.getId());
            if (encontrado.getPixId() != null) new PixDao().deleteById(encontrado.getPixId());
            return true;
        } catch (DeleteFailException e) {
            return false;
        }
    }

    private boolean replacePix(ChavePix pixEncontrado) {
        int result = OptionDialog.showOptionDialog(
                "Esta chave pix já existe e pertence a " + pixEncontrado.getContato() + "! Deseja substituí-lo por este?",
                "ATENÇÃO: Pix já existe!");
        if (result != JOptionPane.YES_OPTION) return false;
        model.getDadoBancario().getChavePix().setId(pixEncontrado.getId());
        return true;
    }

    private void pixTypeComboBox_afterUpdate() {
        EventQueue.invokeLater(() -> {
            TipoPix t = (TipoPix) view.getPixTypeComboBox().getSelectedItem();
            if (t != null) model.setTipoPix(t);
        });
    }

    private void bankComboBox_afterUpdate() {
        EventQueue.invokeLater(() -> {
            Banco banco = (Banco) view.getBankComboBox().getSelectedItem();
            if (banco != null) model.setBanco(banco);
        });
    }

    private void contactComboBox_afterUpdate() {
        EventQueue.invokeLater(() -> {
            Contato contato = (Contato) view.getContactComboBox().getSelectedItem();
            model.setContato(contato);
        });
    }

    private void contaTextField_afterUpdate() { model.setNumConta(view.getContaTextField().getText()); }
    private void agencyTextField_afterUpdate() { model.setAgencia(view.getAgencyTextField().getText()); }
    private void pixKey_afterUpdate() {EventQueue.invokeLater(() -> model.setChave(view.getPixKey().getText())); }
    private void pixCheckBox_onClick() {EventQueue.invokeLater(() -> model.setPixEnabled(view.getPixCheckBox().isSelected())); }

    @Override
    public void showView() { ViewInvoker.showView(view.getDialog()); }

    @Override
    public void update(String property) {
        System.out.println(property);
        switch (property) {
            case EditDadoModel.PIX_CHECKBOX -> view.getPixCheckBox().setSelected(model.isPixEnabled());
            case EditDadoModel.TIPO_PIX -> {
                view.getPixKey().setEnabled(model.isPixEnabled());
                view.getPixTypeComboBox().setSelectedItem(model.getTipoPix());
            }
            case EditDadoModel.CHAVE_PIX -> {
                view.getPixTypeComboBox().setEnabled(model.isPixEnabled());
                view.getPixKey().setText(model.getChave());
                if (model.isPixInvalidFormat()) view.getPixKey().putClientProperty(FlatClientProperties.OUTLINE, "error");
                else view.getPixKey().putClientProperty(FlatClientProperties.OUTLINE, null);
            }
            case EditDadoModel.CONTATO-> view.getContactComboBox().setSelectedItem(model.getContato());
            case EditDadoModel.BANCO -> view.getBankComboBox().setSelectedItem(model.getBanco());
            case EditDadoModel.AGENCIA -> view.getAgencyTextField().setText(model.getAgencia());
            case EditDadoModel.NUM_CONTA -> view.getContaTextField().setText(model.getNumConta());
        }
    }

}
