package com.santacarolina.mavenproject1.view.overview;

import com.santacarolina.mavenproject1.view.dialogs.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OverviewMenuBar extends JMenuBar {

    private DocMenu docsBar;
    private ContactMenu contactBar;
    private AccountabilityMenu accountabilityBar;
    private SettingsMenu settingsBar;
    private UserFoldMenu userFoldMenu;

    public OverviewMenuBar() {
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
            addFolder.addActionListener(e -> new AddUserFolderDialog());

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
            addContact.addActionListener(e -> new AddContactDialog());

            seeContact = new JMenuItem("Gerenciar Contatos");
            seeContact.addActionListener(e -> new ManageContactsDialog());

            contacts = new JMenu("Contatos");
            contacts.add(addContact);
            contacts.add(seeContact);

            addBankingData = new JMenuItem("Adicionar Dado Bancário");
            addBankingData.addActionListener(e -> new AddContactBankAccountDialog());

            seeBankingData = new JMenuItem("Gerenciar Dados Bancários");
            seeBankingData.addActionListener(e -> new ManageContactAccountDialog());

            bankingData = new JMenu("Dados Bancários");
            bankingData.add(addBankingData);
            bankingData.add(seeBankingData);

            add(contacts);
            add(bankingData);
        }
    }

    private static class DocMenu extends JMenu implements ActionListener {

        private JMenuItem addNewDoc;
        private JMenuItem importNfe;
        private JMenuItem reportGenerator;

        public DocMenu() {
            initComponents();
        }

        private void initComponents(){

            addNewDoc = new JMenuItem("Novo Documento Fiscal");
            addNewDoc.addActionListener(this);

            importNfe = new JMenuItem("Importar NFE's");
            reportGenerator = new JMenuItem("Gerar Relatório");

            setText("Documentos Fiscais");
            add(addNewDoc);
            add(importNfe);
            add(reportGenerator);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addNewDoc){
                DocDialog docDialog = new DocDialog();
            }
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
