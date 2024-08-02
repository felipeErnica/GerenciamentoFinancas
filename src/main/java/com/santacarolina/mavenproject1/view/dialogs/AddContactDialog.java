package com.santacarolina.mavenproject1.view.dialogs;

import com.santacarolina.mavenproject1.services.ImageIconConfig;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddContactDialog extends JDialog implements ActionListener {

    private JButton addContact;
    private JButton closeDialog;

    private JTextField contactName;
    private JTextField docNumber;

    public AddContactDialog () {
        initComponents();
    }

    private void initComponents() {

        ImageIconConfig icon = new ImageIconConfig("src/main/resources/icon/add_contact_icon.png");

        setTitle("Adicionar Contato");
        setIconImage(icon.getBufferedImage());
        setSize(500,230);
        setResizable(false);

        JPanel fieldPane = new JPanel();
        contactName = new JTextField();
        docNumber = new JTextField();
        JLabel nameLabel = new JLabel("Nome do Contato:");
        JLabel docLabel = new JLabel("Documento do Contato:");

        GroupLayout fieldLayout = new GroupLayout(fieldPane);
        fieldLayout.setAutoCreateGaps(true);
        fieldLayout.setAutoCreateContainerGaps(true);

        fieldLayout.setHorizontalGroup(fieldLayout.createSequentialGroup()
                .addGroup(fieldLayout.createParallelGroup()
                    .addComponent(nameLabel)
                    .addComponent(docLabel)
                )
                .addGroup(fieldLayout.createParallelGroup()
                    .addComponent(contactName)
                    .addComponent(docNumber)
                )
        );

        fieldLayout.setVerticalGroup(fieldLayout.createSequentialGroup()
                .addGroup(fieldLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(contactName)
                )
                .addGroup(fieldLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(docLabel)
                    .addComponent(docNumber)
                )
        );

        fieldPane.setLayout(fieldLayout);
        fieldPane.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        Dimension buttonDimension = new Dimension(150,30);

        JPanel buttonPane = new JPanel();
        addContact = new JButton("Adicionar Contato");
        addContact.setPreferredSize(buttonDimension);
        addContact.addActionListener(this);
        closeDialog = new JButton("Cancelar");
        closeDialog.setPreferredSize(buttonDimension);
        closeDialog.addActionListener(e -> dispose());

        buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        buttonPane.add(addContact);
        buttonPane.add(closeDialog);

        setLayout(new BorderLayout());
        add(fieldPane,BorderLayout.CENTER);
        add(buttonPane,BorderLayout.SOUTH);
        setModal(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
