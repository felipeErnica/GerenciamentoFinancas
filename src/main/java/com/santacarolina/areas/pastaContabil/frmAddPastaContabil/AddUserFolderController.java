package com.santacarolina.areas.pastaContabil.frmAddPastaContabil;

import com.santacarolina.areas.pastaContabil.frmAddContaBancaria.AddContaForm;
import com.santacarolina.dao.ContaDAO;
import com.santacarolina.dao.PastaDao;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.interfaces.FormListener;
import com.santacarolina.model.beans.ContaBancaria;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.ViewInvoker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jdesktop.swingx.combobox.ListComboBoxModel;

import javax.swing.*;
import java.awt.*;

public class AddUserFolderController implements Controller, FormListener {

    private final Logger logger = LogManager.getLogger();

    private AddUserFolderView view;
    private AddUserFolderModel model;

    public AddUserFolderController(AddUserFolderView view, AddUserFolderModel model) throws FetchFailException {
        this.view = view;
        this.model = model;
        initComponents();
    }

    private void initComponents() throws FetchFailException {
        view.getBankAccountComboBox().setModel(new ListComboBoxModel<>(new ContaDAO().findAll()));
        view.getFolderTextField().addFocusListener((AfterUpdateListener) e -> folderTextField_afterUpdate());
        view.getSelectPathButton().addActionListener(e -> selectPathButton_onClick());
        view.getBankAccountComboBox().addActionListener(e -> bankAccountComboBox_afterUpdate());
        view.getAddFolder().addActionListener(e -> addFolder_onClick());
        view.getAddAccount().addActionListener(e -> addAccount_onClick());
    }


    private void addFolder_onClick() {
        EventQueue.invokeLater(() -> {
            try {
                if (model.updatingNotAllowed()) return;
                else if (model.isNomeRepetido()) return;
                new PastaDao().save(model.getPastaContabil());
                JOptionPane.showMessageDialog(null,
                        "Pasta salva com sucesso",
                        "Informação Salva!",
                        JOptionPane.INFORMATION_MESSAGE);
                view.getDialog().dispose();
            } catch (FetchFailException | SaveFailException e) {
                CustomErrorThrower.throwError(e);
            }
        });
    }

    private void folderTextField_afterUpdate() {
        EventQueue.invokeLater(() -> model.setNomePasta(view.getFolderTextField().getText()));
    }

    private void addAccount_onClick() { EventQueue.invokeLater(() -> new AddContaForm(new ContaBancaria())); }

    private void selectPathButton_onClick() {
        EventQueue.invokeLater(() -> {
            Display display = new Display();
            Shell shell = new Shell(display);
            DirectoryDialog dialog = new DirectoryDialog(shell);
            dialog.setText("Selecional Localização da Pasta Contábil");
            dialog.openDialog().ifPresent(s -> model.setFolderPath(s));
            display.dispose();
        });
    }

    private void bankAccountComboBox_afterUpdate() {
        EventQueue.invokeLater(() -> {
            ContaBancaria conta = (ContaBancaria) view.getBankAccountComboBox().getSelectedItem();
            model.setContaBancaria(conta);
        });
    }

    @Override
    public void update(String property) {
        switch (property) {
            case AddUserFolderModel.CAMINHO -> view.getFolderTextField().setText(model.getNomePasta());
            case AddUserFolderModel.NOME_PASTA -> view.getPathTextField().setText(model.getFolderPath());
        }
    }

    @Override
    public void showView() { ViewInvoker.showView(view.getDialog()); }
}
