package com.santacarolina.controller;

import com.santacarolina.dao.ContatoDao;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.areas.frmDoc.DocumentoController;
import com.santacarolina.model.beans.Contato;
import com.santacarolina.model.beans.DocumentoFiscal;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.ViewInvoker;
import com.santacarolina.view.NfeSelectionForm;
import com.santacarolina.areas.frmDoc.DocForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdesktop.swingx.combobox.ListComboBoxModel;
import java.awt.*;
import java.util.List;

public class NfeSelectionController implements Controller {

    private final Logger logger = LogManager.getLogger();

    private NfeSelectionForm form;
    private DocumentoFiscal doc;
    private List<DocumentoFiscal> documentoFiscalList;

    public NfeSelectionController(NfeSelectionForm form, List<DocumentoFiscal> documentoFiscalList) {
        this.form = form;
        this.documentoFiscalList = documentoFiscalList;
        initComponents();
    }

    private void initComponents() {
        form.getNfeComboBox().setModel(new ListComboBoxModel<>(documentoFiscalList));
        form.getNfeComboBox().setSelectedItem(null);
        form.getAddButton().addActionListener(e -> addButton_afterUpdate());
        form.getNfeComboBox().addActionListener(e -> nfeComboBox_afterUpdate());
    }

    private void nfeComboBox_afterUpdate() { doc = documentoFiscalList.get(form.getNfeComboBox().getSelectedIndex()); }

    private void addButton_afterUpdate() {
        EventQueue.invokeLater(() -> {
            try {
                documentoFiscalList.remove(doc);
                new ContatoDao().findByDocNumber(doc.getEmissor())
                        .ifPresentOrElse(this::checkContato, this::addNewContato);
                new DocumentoController(doc, new DocForm()).showView();
            } catch (FetchFailException e) {
                CustomErrorThrower.throwError(e);
            }
        });
    }

    private void checkContato (Contato c) {
        Contato emissorNfe = doc.getEmissor();
        boolean isChanged = false;

        if (!emissorNfe.getCpf().equals(c.getCpf())) {
            c.setCpf(emissorNfe.getCpf());
            isChanged = true;
        }
        if (!emissorNfe.getCnpj().equals(c.getCnpj())) {
            c.setCnpj(emissorNfe.getCnpj());
            isChanged = true;
        }
        if (!emissorNfe.getIe().equals(c.getIe())) {
            c.setIe(emissorNfe.getIe());
            isChanged = true;
        }

        try {
            if (isChanged) new ContatoDao().save(c);
        } catch (SaveFailException e) {
            CustomErrorThrower.throwError(e);
        }

        doc.setEmissor(c);
    }


    private void addNewContato() {
    }

    @Override
    public void showView() { ViewInvoker.showView(form.getDialog()); }

}
