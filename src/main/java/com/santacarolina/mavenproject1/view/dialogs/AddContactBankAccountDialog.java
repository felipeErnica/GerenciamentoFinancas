package com.santacarolina.mavenproject1.view.dialogs;

import com.santacarolina.mavenproject1.model.Bank;
import com.santacarolina.mavenproject1.model.Contact;
import com.santacarolina.mavenproject1.model.enums.PixType;
import com.santacarolina.mavenproject1.services.ImageIconConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class AddContactBankAccountDialog extends JDialog implements ActionListener {

    private JButton addAccount;
    private JButton closeDialog;

    private JCheckBox pixCheckBox;

    private JTextField accountNumber;
    private JTextField agency;
    private JTextField pixKey;
    private JComboBox<String> pixTypeComboBox;

    private JComboBox<Bank> bankComboBox;
    private JComboBox<Contact> contactComboBox;

    public AddContactBankAccountDialog () {
        initComponents();
    }

    private void initComponents() {

        ImageIconConfig icon = new ImageIconConfig("src/main/resources/icon/contact_bank_account_icon.png");

        setTitle("Adicionar Dado Bancário");
        setIconImage(icon.getBufferedImage());
        setSize(600,400);
        setResizable(false);

        JPanel northPanel =new JPanel();

        contactComboBox = new JComboBox<>();
        contactComboBox.setPreferredSize(new Dimension(400,20));
        JLabel contactLabel = new JLabel("Contato:");

        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        northPanel.add(contactLabel);
        northPanel.add(contactComboBox);

        JPanel accountInfoPane = new JPanel();

        bankComboBox = new JComboBox<>();
        agency = new JTextField();
        accountNumber = new JTextField();
        pixCheckBox = new JCheckBox("Adicionar Chave Pix");

        pixCheckBox.addActionListener(e ->{
            if (pixCheckBox.isSelected()){
                pixKey.setEnabled(true);
                pixTypeComboBox.setEnabled(true);
            } else {
                pixKey.setEnabled(false);
                pixTypeComboBox.setEnabled(false);
            }
        });

        JLabel bankLabel = new JLabel("Banco:");
        JLabel agencyLabel = new JLabel("Agência:");
        JLabel accountLabel = new JLabel("Número da Conta:");

        GroupLayout accountInfoLayout = new GroupLayout(accountInfoPane);
        accountInfoLayout.setAutoCreateGaps(true);
        accountInfoLayout.setAutoCreateContainerGaps(true);

        accountInfoLayout.setHorizontalGroup(accountInfoLayout.createSequentialGroup()
            .addGroup(accountInfoLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(bankLabel)
                    .addComponent(agencyLabel)
                    .addComponent(accountLabel)
                    .addComponent(pixCheckBox)
            )
            .addGroup(accountInfoLayout.createParallelGroup()
                    .addComponent(bankComboBox)
                    .addComponent(agency)
                    .addComponent(accountNumber)
            )
        );

        accountInfoLayout.setVerticalGroup(accountInfoLayout.createSequentialGroup()
                .addGroup(accountInfoLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(bankLabel)
                        .addComponent(bankComboBox)
                )
                .addGroup(accountInfoLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(agencyLabel)
                        .addComponent(agency)
                )
                .addGroup(accountInfoLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(accountLabel)
                        .addComponent(accountNumber)
                )
                .addComponent(pixCheckBox)
        );

        accountInfoPane.setLayout(accountInfoLayout);
        accountInfoPane.setBorder(BorderFactory.createTitledBorder("Informações da Conta"));

        JPanel pixPane = new JPanel();

        JLabel typeLabel = new JLabel("Tipo de Chave");
        JLabel keyLabel = new JLabel("Chave Pix:");

        pixTypeComboBox = new JComboBox(Arrays.stream(PixType.values())
                .map(PixType::getPixType)
                .toArray()
        );
        pixTypeComboBox.setEnabled(false);

        pixKey = new JTextField();
        pixKey.setEnabled(false);

        GroupLayout pixLayout = new GroupLayout(pixPane);
        pixLayout.setAutoCreateContainerGaps(true);
        pixLayout.setAutoCreateGaps(true);

        pixLayout.setHorizontalGroup(pixLayout.createSequentialGroup()
            .addGroup(pixLayout.createParallelGroup()
                .addComponent(typeLabel)
                .addComponent(keyLabel)
            )
            .addGroup(pixLayout.createParallelGroup()
                .addComponent(pixTypeComboBox)
                .addComponent(pixKey)
            )
        );

        pixLayout.setVerticalGroup(pixLayout.createSequentialGroup()
                .addGroup(pixLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(typeLabel)
                        .addComponent(pixTypeComboBox)
                )
                .addGroup(pixLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(keyLabel)
                        .addComponent(pixKey)
                )
        );

        pixPane.setLayout(pixLayout);
        pixPane.setBorder(BorderFactory.createTitledBorder("Informações de Pix"));

        JPanel centerPanel = new JPanel();

        GroupLayout centerLayout = new GroupLayout(centerPanel);
        centerLayout.setAutoCreateGaps(true);
        centerLayout.setAutoCreateContainerGaps(true);

        centerLayout.setHorizontalGroup(centerLayout.createSequentialGroup()
                .addGroup(centerLayout.createParallelGroup()
                        .addComponent(accountInfoPane)
                        .addComponent(pixPane)
                )
        );

        centerLayout.setVerticalGroup(centerLayout.createSequentialGroup()
                .addComponent(accountInfoPane)
                .addComponent(pixPane)
        );

        centerPanel.setLayout(centerLayout);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0,50,0,50));

        Dimension buttonDimension = new Dimension(200,30);

        JPanel buttonPane = new JPanel();
        addAccount = new JButton("Adicionar Dado Bancário");
        addAccount.setPreferredSize(buttonDimension);
        addAccount.addActionListener(this);
        closeDialog = new JButton("Cancelar");
        closeDialog.setPreferredSize(buttonDimension);
        closeDialog.addActionListener(e -> dispose());

        buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
        buttonPane.add(addAccount);
        buttonPane.add(closeDialog);

        setLayout(new BorderLayout());
        add(northPanel,BorderLayout.NORTH);
        add(centerPanel,BorderLayout.CENTER);
        add(buttonPane,BorderLayout.SOUTH);
        setModal(true);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
