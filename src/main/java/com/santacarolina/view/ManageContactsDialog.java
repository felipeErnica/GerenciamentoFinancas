package com.santacarolina.view;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.icons.FlatSearchIcon;
import com.santacarolina.dao.ContatoDao;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.beans.Contato;
import com.santacarolina.util.ImageIconConfig;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageContactsDialog extends JDialog {

    private JTextField searchField;
    private JTable contactsTable;
    private ContatoDao contatoDao = new ContatoDao();

    public ManageContactsDialog () throws FetchFailException {
        initComponents();
        insertValues();
    }

    private void initComponents() {

        ImageIconConfig iconConfig = new ImageIconConfig("src/main/resources/icon/add_contact_icon.png");

        setTitle("Tabela de Contatos");
        setIconImage(iconConfig.getBufferedImage());
        setSize(1024,700);
        setResizable(false);

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Nome do Contato");
        tableModel.addColumn("CNPJ");
        tableModel.addColumn("CPF");
        tableModel.addColumn("IE");

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

    }

    private void insertValues() throws FetchFailException {
            DefaultTableModel tableModel = (DefaultTableModel) contactsTable.getModel();
            List<Contato> contatoList = contatoDao.findAll();
            for (Contato c : contatoList) {
                Object[] objects = new Object[4];
                objects[0] = c.getNome();
                objects[1] = c.getCnpj();
                objects[2] = c.getCpf();
                objects[3] = c.getIe();
                tableModel.addRow(objects);
            }
    }
}
