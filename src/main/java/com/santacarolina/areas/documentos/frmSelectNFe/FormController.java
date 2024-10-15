package com.santacarolina.areas.documentos.frmSelectNFe;

import javax.swing.JOptionPane;

import org.jdesktop.swingx.combobox.ListComboBoxModel;

import com.santacarolina.areas.contato.frmAddContato.AddContatoForm;
import com.santacarolina.areas.documentos.frmDoc.DocForm;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.model.Contato;
import com.santacarolina.model.DocumentoFiscal;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ValidatorViolations;
import com.santacarolina.util.ViewInvoker;

public class FormController implements Controller {

    private FormView view;
    private FormModel model;

    public FormController(FormView view, FormModel model) {
        this.view = view;
        this.model = model;
        init();
    }

    private void init() {
        view.getNfeComboBox().setModel(new ListComboBoxModel<>(model.getNfeList()));
        view.getNfeComboBox().setSelectedItem(null);
        view.getNfeComboBox().addActionListener(e -> nfeComboBox_onClick());
        view.getLoadNfeButton().addActionListener(e -> loadNfeButton_onClick());
    }

    private void loadNfeButton_onClick() {
        if (model.getNfe() == null) {
            ValidatorViolations.violateEmptyFields("NFe");
            return;
        }
        DocumentoFiscal nfe = model.getNfe();
        if (nfe.getEmissorId() == 0) addContato();
        model.deleteNfe(nfe);
        view.getNfeComboBox().setSelectedItem(null);
        if (model.getNfeList().isEmpty()) view.getDialog().dispose();
        DocForm.open(nfe);
    }

    private void addContato() {
        int result = OptionDialog.showOptionDialog("O Emissor desta NFe não está registrado no sistema! " +
                "Deseja adicioná-lo?","Novo Contato");
        if (result == JOptionPane.YES_OPTION) {
            Contato emissor = AddContatoForm.addContato(model.getNfe().getEmissor());
            model.getNfe().setEmissor(emissor);
        } else {
            model.getNfe().setEmissor(null);
        }
    }

    private void nfeComboBox_onClick() {
        DocumentoFiscal doc = (DocumentoFiscal) view.getNfeComboBox().getSelectedItem();
        model.setNfe(doc);
    }

    @Override
    public void showView() { ViewInvoker.showView(view.getDialog()); }

}
