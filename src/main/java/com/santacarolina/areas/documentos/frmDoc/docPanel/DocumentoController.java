package com.santacarolina.areas.documentos.frmDoc.docPanel;

import com.santacarolina.areas.contato.frmAddContato.AddContatoForm;
import com.santacarolina.dao.ContatoDAO;
import com.santacarolina.dao.PastaDAO;
import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.enums.TipoDoc;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.DoubleClickListener;
import com.santacarolina.model.Contato;
import com.santacarolina.model.PastaContabil;
import com.santacarolina.util.FileManager;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.StringConversor;
import org.apache.commons.lang3.StringUtils;
import org.jdesktop.swingx.combobox.ListComboBoxModel;

import java.awt.*;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;

public class DocumentoController {

    private ContatoDAO contatoDAO;
    private PastaDAO pastaDAO;
    private DocModel model;
    private DocView view;

    public DocumentoController(DocModel model, DocView view) throws FetchFailException {
        this.model = model;
        this.view = view;
        contatoDAO = new ContatoDAO();
        pastaDAO = new PastaDAO();
        init();
        model.addPropertyChangeListener(view);
    }

    private void init() throws FetchFailException {
        view.getSenderComboBox().setModel(new ListComboBoxModel<>(contatoDAO.findAll()));
        view.getUserFolderComboBox().setModel(new ListComboBoxModel<>(pastaDAO.findAll()));

        view.getDocPath().addMouseListener((DoubleClickListener) e -> docPath_doubleClick());
        view.getSenderComboBox().addActionListener(e -> senderComboBox_afterUpdate());
        view.getExpenseButton().addActionListener(e -> expenseButton_onClick());
        view.getIncomeButton().addActionListener(e -> incomeButton_onClick());
        view.getAddNewContactButton().addActionListener(e -> addNewContactButton_onClick());
        view.getUserFolderComboBox().addActionListener(e -> userFolderComboBox_afterUpdate());
        view.getDocValue().addFocusListener((AfterUpdateListener) e -> docValue_afterUpdate());
        view.getEmissionDate().addFocusListener((AfterUpdateListener) e -> emissionDate_afterUpdate());
        view.getDocTypeComboBox().addActionListener(e -> docTypeComboBox_afterUpdate());
        view.getDocNumber().addFocusListener((AfterUpdateListener) e -> docNumber_afterUpdate());
    }

    private void senderComboBox_afterUpdate() {
        Contato contato = (Contato) view.getSenderComboBox().getSelectedItem();
        model.setEmissor(contato);
    }

    private void expenseButton_onClick() { model.setFluxoCaixa(FluxoCaixa.DESPESA); }
    private void incomeButton_onClick() { model.setFluxoCaixa(FluxoCaixa.RECEITA); }
    private void addNewContactButton_onClick() { EventQueue.invokeLater(AddContatoForm::new); }

    private void docNumber_afterUpdate() {
        try {
            model.setDocNumber(Long.parseLong(view.getDocNumber().getText()));
        } catch (NumberFormatException e) {
            model.setDocNumber(null);
            if (!StringUtils.isBlank(view.getDocNumber().getText())) model.setInvalidDocNumber(true);
        }
    }

    private void docValue_afterUpdate() {
        try {
            double value = StringConversor.getDoubleFromLocalFormat(view.getDocValue().getText());
            model.setDocValue(value);
            model.setInvalidValue(false);
        } catch (ParseException ex) {
            if (StringUtils.isBlank(view.getEmissionDate().getText())) model.setInvalidValue(true);
        }
    }

    private void docTypeComboBox_afterUpdate() {
        TipoDoc tipoDoc = (TipoDoc) view.getDocTypeComboBox().getSelectedItem();
        model.setTipoDoc(tipoDoc);
    }

    private void userFolderComboBox_afterUpdate() {
        PastaContabil pastaContabil = (PastaContabil) view.getUserFolderComboBox().getSelectedItem();
        model.setPastaContabil(pastaContabil);
    }

    private void emissionDate_afterUpdate() {
        try {
            LocalDate date = StringConversor.transformDate(view.getEmissionDate().getText());
            model.setEmissionDate(date);
        } catch (DateTimeException ex) {
            model.setInvalidDate(true);
        }
    }

    private void docPath_doubleClick() {
        if (view.getDocPath().getText().isEmpty()) {
            String path = FileManager.getFile().orElse("");
            model.setDocPath(path);
        } else FileManager.openFile(view.getDocPath().getText());
    }

}
