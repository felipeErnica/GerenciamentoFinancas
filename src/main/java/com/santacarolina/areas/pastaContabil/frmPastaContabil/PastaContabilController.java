package com.santacarolina.areas.pastaContabil.frmPastaContabil;

import java.awt.EventQueue;

import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jdesktop.swingx.combobox.ListComboBoxModel;

import com.santacarolina.dao.ContaDAO;
import com.santacarolina.dao.PastaDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.ViewInvoker;

public class PastaContabilController {

    private static final PastaDAO pastaDAO = new PastaDAO();
    private static final ContaDAO contaDAO = new ContaDAO();

    private PastaContabilView view;
    private PastaContabilModel model;

    public PastaContabilController(PastaContabilView view, PastaContabilModel model) throws FetchFailException {
        this.view = view;
        this.model = model;
        init();
    }

    private void init() throws FetchFailException {
        view.getBankAccountComboBox().setModel(new ListComboBoxModel<>(contaDAO.findAll()));

        view.getSelectPathButton().addActionListener (e -> selectPathButton_onClick());
        view.getFolderTextField().addFocusListener((AfterUpdateListener) e -> folderTextField_afterUpdate());
        view.getAddFolder().addActionListener(e -> addFolder_onClick());
    }

    //Converte String para Upper Case
    private void folderTextField_afterUpdate() { model.setNomePasta(view.getFolderTextField().getText()); }

    //Ação para seleção de caminho da Pasta no sistema.
    private void selectPathButton_onClick() {
        EventQueue.invokeLater(() -> {
                Display display = new Display();
            Shell shell = new Shell(display);
            DirectoryDialog directoryDialog = new DirectoryDialog(shell);
            directoryDialog.setText("Selecionar Pasta");
            directoryDialog.openDialog().ifPresent(p -> model.setFolderPath(p));
            shell.close();
            display.close();
        });
    }

    //Salvar Pasta no banco de dados
    private void addFolder_onClick() {
        try {
            PastaContabilValidator validator = new PastaContabilValidator();
            if (!validator.validate(model)) return;
            pastaDAO.save(model.getPastaContabil());
        } catch (FetchFailException | SaveFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    public void showView() { ViewInvoker.showView(view.getDialog()); }
     
}

