package com.santacarolina.view;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.icons.FlatSearchIcon;
import com.santacarolina.util.ImageIconConfig;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManageContactAccountDialog extends JDialog {

    private JTextField searchField;
    private JTable accountsTable;

    public ManageContactAccountDialog (){
        initComponents();
    }

    private void initComponents() {

        ImageIconConfig iconConfig = new ImageIconConfig("src/main/resources/icon/contact_bank_account_icon.png");

        setTitle("Tabela de Dados Bancários");
        setIconImage(iconConfig.getBufferedImage());
        setSize(1024,700);
        setResizable(false);

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Nome do Contato");
        tableModel.addColumn("Banco");
        tableModel.addColumn("Agência");
        tableModel.addColumn("Número da Conta");
        tableModel.addColumn("Tipo de Chave");
        tableModel.addColumn("Chave Pix");

        accountsTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(accountsTable);

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(400,20));
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT,"Pesquisar Contato...");
        searchField.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON,new FlatSearchIcon());

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        northPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        northPanel.add(searchField);

        setLayout(new BorderLayout(20,20));
        add(northPanel,BorderLayout.NORTH);
        add(scrollPane,BorderLayout.CENTER);

        setModal(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setVisible(true);

    }
}
