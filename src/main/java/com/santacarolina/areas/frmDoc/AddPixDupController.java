package com.santacarolina.areas.frmDoc;

import com.santacarolina.dao.ContatoDao;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.interfaces.OnSelectListener;
import com.santacarolina.model.beans.ChavePix;
import com.santacarolina.model.beans.Contato;
import com.santacarolina.model.beans.DadoBancario;
import com.santacarolina.model.beans.DocumentoFiscal;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.ViewInvoker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdesktop.swingx.combobox.ListComboBoxModel;

import java.util.List;

public class AddPixDupController implements Controller {

    private final Logger logger = LogManager.getLogger();

    private AddPixDuplicataForm form;
    private DadoBancario dadoBancario;
    private DocumentoFiscal doc;

    public AddPixDupController(DocumentoFiscal doc, AddPixDuplicataForm form) {
        this.form = form;
        this.doc = doc;
        initComponents();
    }

    public void initComponents() {
        try {
            form.getContatoComboBox().setModel(new ListComboBoxModel<>(new ContatoDao().findAll()));
            if (doc.getEmissor() != null) {
                form.getContatoComboBox().setSelectedItem(doc.getEmissor());
                contatoComboBox_afterUpdate();
                chaveComboBox_afterUpdate();
            }
            form.getContatoComboBox().addActionListener(e -> contatoComboBox_afterUpdate());
            form.getChaveComboBox().addActionListener(e -> chaveComboBox_afterUpdate());
            form.getAgenciaTextField().addFocusListener((OnSelectListener) e -> agenciaTextField_onFocus());
            form.getBancoTextField().addFocusListener((OnSelectListener) e -> bancoTextField_onFocus());
            form.getContaTextField().addFocusListener((OnSelectListener) e -> contaTextField_onFocus());
            form.getAddNewPix().addActionListener(e -> addButton_onClick());
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e, logger, form.getDialog());
        }
    }

    private void addButton_onClick() {  }
    private void bancoTextField_onFocus() { form.getBancoTextField().selectAll(); }
    private void contaTextField_onFocus() { form.getContaTextField().selectAll(); }
    private void agenciaTextField_onFocus() { form.getAgenciaTextField().selectAll(); }

    private void chaveComboBox_afterUpdate() {
        if (form.getChaveComboBox().getSelectedItem() == null) return;
        ChavePix chavePix = (ChavePix) form.getChaveComboBox().getSelectedItem();
        form.getTipoPixTextfield().setText(chavePix.getTipoPix().toString());
        if (chavePix.getDadoBancario() != null) {
            DadoBancario d = chavePix.getDadoBancario();
            form.getBancoTextField().setText(chavePix.getDadoBancario().getBanco().getNomeBanco());
            form.getAgenciaTextField().setText(d.getAgencia());
            form.getContaTextField().setText(d.getNumeroConta());
        }
    }

    private void contatoComboBox_afterUpdate() {
        try {
            Contato contato = (Contato) form.getContatoComboBox().getSelectedItem();
            form.getChaveComboBox().removeAllItems();
            List<ChavePix> pixList = new ContatoDao().getPix(contato);
            if (!pixList.isEmpty()) {
                form.getChaveComboBox().setEnabled(true);
                form.getBancoTextField().setEnabled(true);
                form.getAgenciaTextField().setEnabled(true);
                form.getContaTextField().setEnabled(true);
                pixList.forEach(c -> form.getChaveComboBox().addItem(c));
            } else {
                form.getChaveComboBox().setEnabled(false);
                form.getTipoPixTextfield().setText("");
                form.getBancoTextField().setText("");
                form.getBancoTextField().setEnabled(false);
                form.getAgenciaTextField().setText("");
                form.getAgenciaTextField().setEnabled(false);
                form.getContaTextField().setText("");
                form.getContaTextField().setEnabled(false);
            }
        } catch (FetchFailException ex) {
            CustomErrorThrower.throwError(ex, logger, form.getDialog());
        }
    }

    public void showView() { ViewInvoker.showView(form.getDialog()); }

}