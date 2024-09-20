package com.santacarolina.areas.frmDoc;

import com.formdev.flatlaf.FlatClientProperties;
import com.santacarolina.areas.contato.frmAddContato.AddContatoForm;
import com.santacarolina.dao.ContatoDao;
import com.santacarolina.dao.PastaDao;
import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.enums.TipoDoc;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.DoubleClickListener;
import com.santacarolina.model.beans.Contato;
import com.santacarolina.model.beans.DocumentoFiscal;
import com.santacarolina.model.beans.PastaContabil;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.FileManager;
import com.santacarolina.util.StringConversor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdesktop.swingx.combobox.ListComboBoxModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DocumentoController {

    private final Logger logger = LogManager.getLogger();

    private DocumentoFiscal doc;
    private DocForm docForm;
    private InfoPanel panel;

    public DocumentoController(DocumentoFiscal doc, DocForm docForm) {
        this.doc = doc;
        this.docForm = docForm;
        this.panel = docForm.getInfoPanel();

        new DuplicatasDocController(doc, docForm.getDuplicatasPanel());
        new ProdutosDocController(doc, docForm.getProdutosPanel());

        panel.getDocPath().addMouseListener((DoubleClickListener) this::docPath_doubleClick);
        panel.getSenderComboBox().addActionListener(this::senderComboBox_afterUpdate);
        panel.getExpenseButton().addActionListener(this::expenseButton_click);
        panel.getIncomeButton().addActionListener(this::incomeButton_click);
        panel.getAddNewContactButton().addActionListener(this::addNewContactButton_click);
        panel.getUserFolderComboBox().addActionListener(this::userFolderComboBox_afterUpdate);
        panel.getDocValue().addFocusListener((AfterUpdateListener) this::docValue_afterUpdate);
        panel.getEmissionDate().addFocusListener((AfterUpdateListener) this::emissionDate_afterUpdate);
        panel.getDocTypeComboBox().addActionListener(this::docTypeComboBox_afterUpdate);
        docForm.getUpdateDocButton().addActionListener(this::updateDocButton_Click);

        try {
            panel.getSenderComboBox().setModel(new ListComboBoxModel<>(new ContatoDao().findAll()));
            panel.getUserFolderComboBox().setModel(new ListComboBoxModel<>(new PastaDao().findAll()));
            insertValues();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e, logger, docForm.getDialog());
        }

    }

    private void insertValues() {
        try {

            if (doc.getTipoDoc() == TipoDoc.NOTA_FISCAL || doc.getTipoDoc() == TipoDoc.NFE) {
                panel.getDocNumber().setText(doc.getNumDoc());
                panel.getDocNumber().setEnabled(true);
            } else panel.getDocNumber().setEnabled(false);

            if (doc.isExpense()) panel.getExpenseButton().setSelected(true);
            else panel.getIncomeButton().setSelected(true);

            panel.getSenderComboBox().setSelectedItem(doc.getEmissor());
            panel.getUserFolderComboBox().setSelectedItem(doc.getPastaContabil());
            panel.getEmissionDate().setText(doc.getDataEmissao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            panel.getDocValue().setText(StringConversor.getCurrency(doc.getValor()));
            panel.getDocTypeComboBox().setSelectedItem(doc.getTipoDoc());
            panel.getDocPath().setText(doc.getCaminho());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Falha ao abrir o Documento!",
                    "ERRO!",
                    JOptionPane.ERROR_MESSAGE);
            docForm.getDialog().dispose();
            logger.error(e.getLocalizedMessage());
        }
    }

    public void showView(){
        docForm.getDialog().setModal(true);
        docForm.getDialog().setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        docForm.getDialog().setVisible(true);
    }

    private void senderComboBox_afterUpdate(ActionEvent actionEvent) {
        Contato contato = (Contato) panel.getSenderComboBox().getSelectedItem();
        doc.setEmissor(contato);
    }

    private void docPath_doubleClick(MouseEvent e) {
        if (panel.getDocPath().getText().isEmpty()) {
            String path = FileManager.getFile().orElse("");
            doc.setCaminho(path);
            panel.getDocPath().setText(path);
        } else FileManager.openFile(panel.getDocPath().getText());
    }

    private void expenseButton_click(ActionEvent e) {
        doc.setFluxoCaixa(FluxoCaixa.DESPESA);
        panel.getDocValue().setText(StringConversor.getCurrency(doc.getValor()));
    }

    private void incomeButton_click(ActionEvent e) {
        doc.setFluxoCaixa(FluxoCaixa.RECEITA);
        panel.getDocValue().setText(StringConversor.getCurrency(doc.getValor()));
    }

    private void addNewContactButton_click(ActionEvent e) {
        EventQueue.invokeLater(AddContatoForm::new);
    }

    private void docValue_afterUpdate(FocusEvent e) {
        try {
            double value = StringConversor.getDoubleFromLocalFormat(panel.getDocValue().getText());
            doc.setValor(value);
            panel.getDocValue().setText(StringConversor.getCurrency(doc.getValor()));
            panel.getDocValue().putClientProperty(FlatClientProperties.OUTLINE,null);
        } catch (ParseException ex) {
            panel.getDocValue().putClientProperty(FlatClientProperties.OUTLINE,FlatClientProperties.OUTLINE_ERROR);
        }

    }

    private void docTypeComboBox_afterUpdate(ActionEvent e) {
        TipoDoc tipoDoc = panel.getDocTypeComboBox().getItemAt(panel.getDocTypeComboBox().getSelectedIndex());
        doc.setTipoDoc(tipoDoc);
        if (tipoDoc == TipoDoc.NOTA_FISCAL || tipoDoc == TipoDoc.NFE) panel.getDocNumber().setEnabled(true);
        else {
            panel.getDocNumber().setEnabled(false);
            panel.getDocNumber().setText("");
            doc.setNumDoc("");
        }
    }

    private void userFolderComboBox_afterUpdate(ActionEvent e) {
        PastaContabil pastaContabil = (PastaContabil) panel.getUserFolderComboBox().getSelectedItem();
        doc.setPastaContabil(pastaContabil);
    }

    private void emissionDate_afterUpdate(FocusEvent e) {
        try {
            LocalDate date = StringConversor.transformDate(panel.getEmissionDate().getText());
            doc.setDataEmissao(date);
            panel.getEmissionDate().putClientProperty(FlatClientProperties.OUTLINE,null);
            panel.getEmissionDate().setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } catch (DateTimeException ex) {
            panel.getEmissionDate().putClientProperty(FlatClientProperties.OUTLINE,FlatClientProperties.OUTLINE_ERROR);
        }
    }

    private void updateDocButton_Click(ActionEvent e) {
        System.out.println(doc);
    }

}
