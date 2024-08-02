package com.santacarolina.mavenproject1.view.dialogs;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.icons.FlatFileChooserNewFolderIcon;
import com.santacarolina.mavenproject1.model.UserBankAccount;
import com.santacarolina.mavenproject1.services.CommonEvents;
import com.santacarolina.mavenproject1.services.ImageIconConfig;
import javax.swing.*;
import java.awt.*;

public class AddUserFolderDialog extends JDialog {

    private JTextField folderTextField;
    private JTextField pathTextField;
    private JComboBox<UserBankAccount> bankAccountComboBox;

    private JButton addFolder;
    private JButton closeDialog;

    public AddUserFolderDialog() {
        initComponents();
    }

    private void initComponents() {

        ImageIconConfig icon = new ImageIconConfig("src/main/resources/icon/user_folder_icon.png");

        setTitle("Adicionar Pasta Contábil");
        setIconImage(icon.getBufferedImage());
        setSize(500, 230);
        setResizable(false);

        JPanel fieldPane = new JPanel();

        folderTextField = new JTextField();
        folderTextField.addFocusListener(CommonEvents.afterUpdateToUpperCase());

        JFileChooser fileChoser = new JFileChooser();

        JButton selectPathButton = new JButton(new FlatFileChooserNewFolderIcon());

        selectPathButton.addActionListener(e -> {
            fileChoser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChoser.showOpenDialog(this);
            pathTextField.setText(fileChoser.getSelectedFile().getAbsolutePath());
        });

        pathTextField = new JTextField();
        pathTextField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_COMPONENT, selectPathButton);

        bankAccountComboBox = new JComboBox<>();
        bankAccountComboBox.addKeyListener(CommonEvents.nextComponentOnEnter(addFolder));

        JLabel folderLabel = new JLabel("Nome da Pasta:");
        JLabel pathLabel = new JLabel("Caminho da Pasta:");
        JLabel bankAccountLabel = new JLabel("Conta Relacionada:");

        GroupLayout fieldLayout = new GroupLayout(fieldPane);
        fieldLayout.setAutoCreateGaps(true);
        fieldLayout.setAutoCreateContainerGaps(true);

        fieldLayout.setHorizontalGroup(fieldLayout.createSequentialGroup()
                .addGroup(fieldLayout.createParallelGroup()
                        .addComponent(folderLabel)
                        .addComponent(pathLabel)
                        .addComponent(bankAccountLabel)
                )
                .addGroup(fieldLayout.createParallelGroup()
                        .addComponent(folderTextField)
                        .addComponent(pathTextField)
                        .addComponent(bankAccountComboBox)
                )
        );

        fieldLayout.setVerticalGroup(fieldLayout.createSequentialGroup()
                .addGroup(fieldLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(folderLabel)
                        .addComponent(folderTextField)
                )
                .addGroup(fieldLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(pathLabel)
                        .addComponent(pathTextField)
                )
                .addGroup(fieldLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(bankAccountLabel)
                        .addComponent(bankAccountComboBox)
                )
        );

        fieldPane.setLayout(fieldLayout);
        fieldPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Dimension buttonDimension = new Dimension(150, 30);

        JPanel buttonPane = new JPanel();
        addFolder = new JButton("Adicionar Pasta");
        addFolder.setPreferredSize(buttonDimension);
        closeDialog = new JButton("Cancelar");
        closeDialog.setPreferredSize(buttonDimension);
        closeDialog.addActionListener(e -> dispose());

        buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPane.add(addFolder);
        buttonPane.add(closeDialog);

        setLayout(new BorderLayout());
        add(fieldPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.SOUTH);
        setModal(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setVisible(true);
    }
}
