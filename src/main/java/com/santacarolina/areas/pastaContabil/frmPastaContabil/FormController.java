package com.santacarolina.areas.pastaContabil.frmPastaContabil;

import java.awt.EventQueue;

import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jdesktop.swingx.combobox.ListComboBoxModel;

import com.santacarolina.areas.bancario.contaBancaria.frmContaBancaria.ContaForm;
import com.santacarolina.dao.ContaDAO;
import com.santacarolina.dao.PastaDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.model.ContaBancaria;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;

@SuppressWarnings("unchecked")
public class FormController {

    private FormView view;
    private FormModel model;

    public FormController(FormView view, FormModel model) throws FetchFailException {
        this.view = view;
        this.model = model;
        init();
    }

    private void init() throws FetchFailException {
        view.getBankAccountComboBox().setModel(new ListComboBoxModel<>(new ContaDAO().findAll()));
        view.getSelectPathButton().addActionListener (e -> selectPathButton_onClick());
        view.getPathTextField().addActionListener(e -> pathTextField_onClick());       
        view.getFolderTextField().addFocusListener((AfterUpdateListener) e -> folderTextField_afterUpdate());
        view.getAddFolder().addActionListener(e -> addFolder_onClick());
        view.getBankAccountComboBox().addActionListener(e -> contaBancaria_onAction());
        view.getAddAccount().addActionListener(e -> addAccount_onClick());
    }

    private void pathTextField_onClick() { model.setFolderPath(view.getPathTextField().getText()); }

    private void addAccount_onClick() {
        EventQueue.invokeLater(() -> {
            ContaForm.openNew();
            try {
                view.getBankAccountComboBox().setModel(new ListComboBoxModel<>(new ContaDAO().findAll()));
                view.getBankAccountComboBox().setSelectedItem(null);
            } catch (FetchFailException e) {
                CustomErrorThrower.throwError(e);
            }
        });
    }

    private void contaBancaria_onAction() {
        ContaBancaria contaBancaria = (ContaBancaria) view.getBankAccountComboBox().getSelectedItem();
        model.setContaBancaria(contaBancaria);
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
            if (!PastaContabilValidator.validate(model)) return;
            new PastaDAO().save(model.getPastaContabil());
            OptionDialog.showSuccessSaveMessage();
            view.getDialog().dispose();
        } catch (FetchFailException | SaveFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    public void showView() { ViewInvoker.showView(view.getDialog()); }
     
}

