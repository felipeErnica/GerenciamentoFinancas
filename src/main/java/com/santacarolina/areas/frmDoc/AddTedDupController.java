package com.santacarolina.areas.frmDoc;

import com.santacarolina.dao.ContatoDao;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.OnSelectListener;
import com.santacarolina.model.beans.Contato;
import com.santacarolina.model.beans.DadoBancario;
import com.santacarolina.model.beans.DocumentoFiscal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdesktop.swingx.combobox.ListComboBoxModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AddTedDupController {

    private final Logger logger = LogManager.getLogger();

    private AddTedDuplicataForm form;
    private DadoBancario dadoBancario;
    private DocumentoFiscal doc;

    public AddTedDupController(DocumentoFiscal doc, AddTedDuplicataForm form) {
        this.form = form;
        this.doc = doc;
        try {
            form.getContatoComboBox().setModel(new ListComboBoxModel<>(new ContatoDao().findAll()));
            if (doc.getEmissor() != null) {
                form.getContatoComboBox().setSelectedItem(doc.getEmissor());
                contatoComboBox_afterUpdate();
                contaComboBox_afterUpdate();
            }
            form.getContatoComboBox().addActionListener(e -> contatoComboBox_afterUpdate());
            form.getContaComboBox().addActionListener(e -> contaComboBox_afterUpdate());
            form.getAgenciaTextField().addFocusListener((OnSelectListener) e -> agenciaTextField_onFocus());
            form.getBancoTextField().addFocusListener((OnSelectListener) e -> bancoTextField_onFocus());
        } catch (FetchFailException e) {
            throwError(e);
        }
    }

    private void bancoTextField_onFocus() { form.getBancoTextField().selectAll(); }
    private void agenciaTextField_onFocus() { form.getAgenciaTextField().selectAll(); }

    private void contaComboBox_afterUpdate() {
        if (form.getContaComboBox().getSelectedItem() == null) return;
        DadoBancario d = (DadoBancario) form.getContaComboBox().getSelectedItem();
        form.getBancoTextField().setText(d.getBanco().getNomeBanco());
        form.getAgenciaTextField().setText(d.getAgencia());
    }

    private void contatoComboBox_afterUpdate() {
        try {
            Contato contato = (Contato) form.getContatoComboBox().getSelectedItem();
            form.getContaComboBox().removeAllItems();
            List<DadoBancario> contasList = new ContatoDao().findContas(contato);
            if (!contasList.isEmpty()) {
                form.getContaComboBox().setEnabled(true);
                form.getBancoTextField().setEnabled(true);
                form.getAgenciaTextField().setEnabled(true);
                contasList.forEach(c -> form.getContaComboBox().addItem(c));
            } else {
                form.getContaComboBox().setEnabled(false);
                form.getBancoTextField().setText("");
                form.getBancoTextField().setEnabled(false);
                form.getAgenciaTextField().setText("");
                form.getAgenciaTextField().setEnabled(false);
            }
        } catch (FetchFailException ex) {
            throwError(ex);
        }
    }

    public void showView() {
        form.getDialog().setModal(true);
        form.getDialog().setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        form.getDialog().pack();
        form.getDialog().setVisible(true);
    }

    private void throwError(FetchFailException e) {
        logger.error(e.getMessage());
        JOptionPane.showMessageDialog(null, e.getMessage(), e.getMessageTitle(), JOptionPane.ERROR_MESSAGE);
        form.getDialog().dispose();
    }

}