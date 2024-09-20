package com.santacarolina.view;

import com.santacarolina.areas.bancario.dadoBancario.frmAddDado.AddDadoBancarioView;
import com.santacarolina.areas.pastaContabil.frmAddPastaContabil.AddUserFolderView;
import com.santacarolina.areas.contato.frmAddContato.AddContatoForm;
import com.santacarolina.areas.frmDoc.DocumentoController;
import com.santacarolina.controller.NfeFolderController;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.beans.DocumentoFiscal;
import com.santacarolina.areas.frmDoc.DocForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OverviewMenuBar extends JMenuBar {

    private DocMenu docsBar;
    private ContactMenu contactBar;
    private AccountabilityMenu accountabilityBar;
    private SettingsMenu settingsBar;
    private UserFoldMenu userFoldMenu;

    private JFrame mainFrame;

    public OverviewMenuBar(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
    }

    private void initComponents(){

        docsBar = new DocMenu();
        contactBar = new ContactMenu();
        userFoldMenu = new UserFoldMenu();
        accountabilityBar = new AccountabilityMenu();
        settingsBar = new SettingsMenu();

        add(docsBar);
        add(contactBar);
        add(accountabilityBar);
        add(settingsBar);
    }

    private void throwError(Exception e){
        JOptionPane.showMessageDialog(this.getParent(),
                e.getMessage(),
                "Falha de Comunicação com o Servidor!",
                JOptionPane.ERROR_MESSAGE);
    }


    private static class AccountabilityMenu extends JMenu implements ActionListener {

        private JMenuItem addCategory;
        private JMenuItem seeCategory;

        public AccountabilityMenu() {
            initComponents();
        }

        private void initComponents(){
            setText("Contabilidade");
            addCategory = new JMenuItem("Adicionar Nova Categoria");
            seeCategory = new JMenuItem("Ver Categorias");
            add(addCategory);
            add(seeCategory);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private static class UserFoldMenu extends JMenu {

        private JMenuItem addFolder;
        private JMenuItem seeFolder;

        public UserFoldMenu () {
            initComponents();
        }

        private void initComponents(){

            setText("Pasta Contábil");

            addFolder = new JMenuItem("Adicionar Nova Pasta");
            addFolder.addActionListener(e -> new AddUserFolderView());

            seeFolder = new JMenuItem("Gerenciar Pasta Contábil");
            seeFolder.addActionListener(e -> new ManageUserFolderDialog());

            add(addFolder);
            add(seeFolder);

        }
    }

    private  class ContactMenu extends JMenu {

        private JMenu contacts;
        private JMenuItem addContact;
        private JMenuItem seeContact;
        private JMenu bankingData;
        private JMenuItem addBankingData;
        private JMenuItem seeBankingData;

        public ContactMenu() {
            initComponents();
        }

        private void initComponents(){

            setText("Contatos");

            addContact = new JMenuItem("Adicionar Novo Contato");
            addContact.addActionListener(e -> EventQueue.invokeLater(AddContatoForm::new));

            seeContact = new JMenuItem("Gerenciar Contatos");
            seeContact.addActionListener(this::seeContacts_ButtonClicked);

            contacts = new JMenu("Contatos");
            contacts.add(addContact);
            contacts.add(seeContact);

            addBankingData = new JMenuItem("Adicionar Dado Bancário");
            addBankingData.addActionListener(e -> new AddDadoBancarioView());

            seeBankingData = new JMenuItem("Gerenciar Dados Bancários");
            seeBankingData.addActionListener(e -> new ManageContactAccountDialog());

            bankingData = new JMenu("Dados Bancários");
            bankingData.add(addBankingData);
            bankingData.add(seeBankingData);

            add(contacts);
            add(bankingData);
        }

        private void seeContacts_ButtonClicked(ActionEvent actionEvent) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        new ManageContactsDialog().setVisible(true);
                    } catch (FetchFailException ex) {
                        throwError(ex);
                    }
                }
            });
        }
    }

    private static class DocMenu extends JMenu {

        private JMenuItem addNewDoc;
        private JMenuItem importNfe;
        private JMenuItem reportGenerator;

        public DocMenu() {
            initComponents();
        }

        private void initComponents(){

            addNewDoc = new JMenuItem("Novo Documento Fiscal");
            addNewDoc.addActionListener(this::addNewDoc_Click);

            importNfe = new JMenuItem("Importar NFE's");
            importNfe.addActionListener(this::importNfe_Click);
            reportGenerator = new JMenuItem("Gerar Relatório");

            setText("Documentos Fiscais");
            add(addNewDoc);
            add(importNfe);
            add(reportGenerator);

        }

        private void importNfe_Click(ActionEvent e) {
            EventQueue.invokeLater(() -> new NfeFolderController(new NfeFolderForm()).showView());
        }

        private void addNewDoc_Click(ActionEvent e) {
            EventQueue.invokeLater(() -> new DocumentoController(new DocumentoFiscal(), new DocForm()).showView());
        }

    }

    private static class SettingsMenu extends JMenu implements ActionListener {

        private JMenuItem openConfigurationMenu;

        public SettingsMenu() {
            initComponents();
        }

        private void initComponents(){
            setText("Configurações");
            openConfigurationMenu = new JMenuItem("Abrir Configurações...");
            add(openConfigurationMenu);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
