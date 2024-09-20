package com.santacarolina.controller;

import com.santacarolina.dao.PastaDao;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.beans.DocumentoFiscal;
import com.santacarolina.model.beans.PastaContabil;
import com.santacarolina.util.NfeImporter;
import com.santacarolina.view.NfeFolderForm;
import com.santacarolina.view.NfeSelectionForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.FileDialog;
import org.jdesktop.swingx.combobox.ListComboBoxModel;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NfeFolderController {

    private final Logger logger = LogManager.getLogger();

    private NfeFolderForm form;
    private PastaContabil pastaContabil;
    private List<DocumentoFiscal> documentoFiscalList = new ArrayList<>();

    public NfeFolderController(NfeFolderForm form) {
        try {
            this.form = form;
            form.getPastaContabilComboBox().addActionListener(e -> pastaContabil_afterUpdate());
            form.getPastaContabilComboBox().setModel(new ListComboBoxModel<>(new PastaDao().findAll()));
            form.getAddButton().addActionListener(e -> addButton_onClick());
        } catch (FetchFailException e) {
            logger.error(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage(), e.getMessageTitle(), JOptionPane.ERROR_MESSAGE);
            form.getDialog().dispose();
        }
    }

    private void pastaContabil_afterUpdate() { pastaContabil = (PastaContabil) form.getPastaContabilComboBox().getSelectedItem(); }

    private void addButton_onClick() {
        EventQueue.invokeLater(() -> {
            Display display = new Display();
            Shell shell = new Shell(display);
            FileDialog fileChooser = new FileDialog(shell);
            fileChooser.setText("Importar NFe's");
            fileChooser.setFilterPath(pastaContabil.getCaminhoPasta());
            fileChooser.setFilterNames(new String[]{"Arquivo XML"});
            fileChooser.setFilterExtensions(new String[]{"*.xml"});
            fileChooser.openDialog().ifPresent(f -> openFiles(f));
        });
    }

    private void openFiles(String... filePaths) {
        List<String> failedFiles = new ArrayList<>();
        for (String path : filePaths){
            File f = new File(path);
            try {
                DocumentoFiscal d = NfeImporter.openNfe(f);
                d.setPastaContabil(pastaContabil);
                documentoFiscalList.add(d);
            } catch (IOException e) {
                logger.error(e.getLocalizedMessage());
                failedFiles.add(f.getAbsolutePath());
            }
        }
        if (!failedFiles.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            failedFiles.forEach(sb::append);
            JOptionPane.showMessageDialog(null,
                    "Falha ao Importar as NFe's: " + sb.toString(),
                    "Falha na Importação",
                    JOptionPane.ERROR_MESSAGE);
        }
        if (!documentoFiscalList.isEmpty()) openNfeForm();
    }

    private void openNfeForm() {
        EventQueue.invokeLater(() -> {
            form.getDialog().dispose();
            new NfeSelectionController(new NfeSelectionForm(), documentoFiscalList).showView();
        });
    }

    public void showView() {
        form.getDialog().setModal(true);
        form.getDialog().setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        form.getDialog().pack();
        form.getDialog().setVisible(true);
    }

}
