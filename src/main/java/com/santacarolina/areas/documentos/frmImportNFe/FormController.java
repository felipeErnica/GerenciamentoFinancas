package com.santacarolina.areas.documentos.frmImportNFe;

import com.santacarolina.areas.documentos.frmSelectNFe.SelectNFeForm;
import com.santacarolina.dao.PastaDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.NFeException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.model.DocumentoFiscal;
import com.santacarolina.model.PastaContabil;
import com.santacarolina.util.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.jdesktop.swingx.combobox.ListComboBoxModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FormController implements Controller {

    private final PastaDAO pastaDAO = new PastaDAO();

    private FormView view;
    private FormModel model;

    public FormController(FormView view, FormModel model) throws FetchFailException {
        this.view = view;
        this.model = model;
        init();
    }

    private void init() throws FetchFailException {
        view.getPastaContabilComboBox().setModel(new ListComboBoxModel<>(pastaDAO.findAll()));
        view.getPastaContabilComboBox().setSelectedItem(null);
        view.getImportNFeButton().addActionListener(e -> importNfeButton_onClick());
        view.getPastaContabilComboBox().addActionListener(e -> pastaContabilComboBox_afterUpdate());
    }

    private void pastaContabilComboBox_afterUpdate() {
        PastaContabil pasta = (PastaContabil) view.getPastaContabilComboBox().getSelectedItem();
        model.setPastaContabil(pasta);
    }

    private void importNfeButton_onClick() {
        if (model.getPastaContabil() == null) {
            ValidatorViolations.violateEmptyFields("Pasta Contábil");
            return;
        }
        Display display = new Display();
        Shell shell = new Shell();
        FileDialog fd = new FileDialog(shell, SWT.MULTI);
        fd.setText("Importar NFe");
        fd.setFilterNames(new String[]{"Arquivo XML"});
        fd.setFilterExtensions(new String[]{"*.xml"});
        fd.setFilterPath(model.getPastaContabil().getCaminhoPasta());
        String test = fd.open();
        List<DocumentoFiscal> nfeList = new ArrayList<>();
        if (test != null) nfeList = importNfe(fd);
        shell.close();
        display.close();
        if (!nfeList.isEmpty()) SelectNFeForm.openListNFe(nfeList);
        view.getDialog().dispose();
    }

    private List<DocumentoFiscal> importNfe(FileDialog fd) {
        List<String> failedFiles = new ArrayList<>();
        List<DocumentoFiscal> nfeList = new ArrayList<>();
        String[] strings = fd.getFileNames();
        for (String string : strings) {
            File nfe = new File(fd.getFilterPath() + "/" + string);
            try {
                DocumentoFiscal nfeDoc = NfeTransformer.transformNFe(nfe);
                nfeDoc.setPastaContabil(model.getPastaContabil());
                nfeList.add(nfeDoc);
            } catch (NFeException | FetchFailException | SaveFailException e) {
                failedFiles.add(nfe.getAbsolutePath());
            }
        }
        if (!failedFiles.isEmpty()) {
            StringBuffer sb = new StringBuffer("A importação falhou para as seguintes NFe's:\n");
            failedFiles.forEach(s -> sb.append(s).append("\n"));
            OptionDialog.showErrorDialog(sb.toString(), "Falha na Importação!");
        }
        return nfeList;
    }

    @Override
    public void showView() { ViewInvoker.showView(view.getDialog()); }

}
