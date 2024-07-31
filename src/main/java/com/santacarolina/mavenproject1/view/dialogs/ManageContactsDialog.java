package com.santacarolina.mavenproject1.view.dialogs;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.icons.FlatSearchIcon;
import com.santacarolina.mavenproject1.services.ImageIconConfig;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManageContactsDialog extends JDialog {

    private JTextField searchField;
    private JTable contactsTable;

    public ManageContactsDialog (){
        initComponents();
    }

    private void initComponents() {

        ImageIconConfig iconConfig = new ImageIconConfig("src/main/resources/icon/add_contact_icon.png");

        setTitle("Tabela de Contatos");
        setIconImage(iconConfig.getBufferedImage());
        setSize(1024,700);
        setResizable(false);

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Nome do Contato");
        tableModel.addColumn("Número do Documento");

        contactsTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(contactsTable);

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(400,20));
        textField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT,"Pesquisar Contato...");
        textField.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON,new FlatSearchIcon());

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        northPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        northPanel.add(textField);

        setLayout(new BorderLayout(20,20));
        add(northPanel,BorderLayout.NORTH);
        add(scrollPane,BorderLayout.CENTER);

        setModal(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setVisible(true);

    }
}
