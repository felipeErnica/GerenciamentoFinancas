package com.santacarolina.mavenproject1.view.overview;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OverviewMenuBar extends JMenuBar {

    private DocMenu docsBar;
    private ContactMenu contactBar;
    private AccountabilityMenu accountabilityBar;
    private SettingsMenu settingsBar;

    public OverviewMenuBar() {
        initComponents();
    }

    private void initComponents(){
        docsBar = new DocMenu();
        contactBar = new ContactMenu();
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


    private  class ContactMenu extends JMenu implements ActionListener {

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

            contacts = new JMenu("Contatos");
            addContact = new JMenuItem("Adicionar Novo Contato");
            seeContact = new JMenuItem("Gerenciar Contatos");
            contacts.add(addContact);
            contacts.add(seeContact);

            bankingData = new JMenu("Dados Bancários");
            addBankingData = new JMenuItem("Adicionar Dado Bancário");
            seeBankingData = new JMenuItem("Gerenciar Dados Bancários");
            bankingData.add(addBankingData);
            bankingData.add(seeBankingData);

            add(contacts);
            add(bankingData);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

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
            setText("Documentos Fiscais");
            addNewDoc = new JMenuItem("Novo Documento Fiscal");
            importNfe = new JMenuItem("Importar NFE's");
            reportGenerator = new JMenuItem("Gerar Relatório");
            add(addNewDoc);
            add(importNfe);
            add(reportGenerator);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

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
