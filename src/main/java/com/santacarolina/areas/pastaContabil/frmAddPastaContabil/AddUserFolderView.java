package com.santacarolina.areas.pastaContabil.frmAddPastaContabil;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.icons.FlatFileChooserNewFolderIcon;
import com.santacarolina.model.ContaBancaria;
import com.santacarolina.ui.ActionSVGButton;
import com.santacarolina.ui.AddView;
import com.santacarolina.util.AppIcon;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.awt.*;

public class AddUserFolderView {

    private JDialog dialog;
    private JTextField folderTextField;
    private JTextField pathTextField;
    private JButton selectPathButton;
    private JComboBox<ContaBancaria> bankAccountComboBox;
    private JButton addAccount;
    private JButton addFolder;

    public AddUserFolderView() {
        AddView view = new AddView();
        dialog = view.getDialog();
        addFolder = view.getAddButton();
        initComponents();
    }

    private void initComponents() {

        FlatSVGIcon icon = AppIcon.paintIcon(new FlatSVGIcon("icon/user_folder_icon.svg"));

        dialog.setTitle("Adicionar Pasta Contábil");
        dialog.setIconImage(icon.getImage());
        addFolder.setText("Adicionar Pasta");

        JPanel fieldPane = new JPanel();

        folderTextField = new JTextField();
        JLabel folderLabel = new JLabel("Nome da Pasta:");
        folderLabel.setLabelFor(folderTextField);

        selectPathButton = new JButton(new FlatFileChooserNewFolderIcon());
        pathTextField = new JTextField();
        pathTextField.setEditable(false);
        pathTextField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_COMPONENT, selectPathButton);
        JLabel pathLabel = new JLabel("Caminho da Pasta:");
        pathLabel.setLabelFor(pathTextField);

        bankAccountComboBox = new JComboBox<>();
        AutoCompleteDecorator.decorate(bankAccountComboBox);
        JLabel bankAccountLabel = new JLabel("Conta Relacionada:");
        bankAccountLabel.setLabelFor(bankAccountComboBox);

        addAccount = new ActionSVGButton("Adicionar Conta", new FlatSVGIcon("icon/add_icon.svg"));

        fieldPane.setLayout(new MigLayout("insets 25",
                "[right][grow, fill][fill]",
                "[][][]"
        ));

        fieldPane.add(folderLabel);
        fieldPane.add(folderTextField, "wrap, span");
        fieldPane.add(pathLabel);
        fieldPane.add(pathTextField, "wrap, span");
        fieldPane.add(bankAccountLabel);
        fieldPane.add(bankAccountComboBox);
        fieldPane.add(addAccount);

        dialog.add(fieldPane, BorderLayout.CENTER);
    }

    public JDialog getDialog() { return dialog; }
    public JTextField getFolderTextField() { return folderTextField; }
    public JTextField getPathTextField() { return pathTextField; }
    public JButton getSelectPathButton() { return selectPathButton; }
    public JComboBox<ContaBancaria> getBankAccountComboBox() { return bankAccountComboBox; }
    public JButton getAddAccount() { return addAccount; }
    public JButton getAddFolder() { return addFolder; }

}
