package com.santacarolina.areas.pastaContabil.frmPastaContabil;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.icons.FlatFileChooserNewFolderIcon;
import com.santacarolina.model.ContaBancaria;
import com.santacarolina.ui.ActionSVGButton;
import com.santacarolina.ui.AddView;
import com.santacarolina.util.AppIcon;

import net.miginfocom.swing.MigLayout;

public class FormView implements PropertyChangeListener {

    private JDialog dialog;
    private JTextField folderTextField;
    private JTextField pathTextField;
    private JButton selectPathButton;
    private JComboBox<ContaBancaria> bankAccountComboBox;
    private JButton addAccount;
    private JButton addFolder;

    public FormView(String buttonText, String dialogText) {
        AddView view = new AddView();
        dialog = view.getDialog();
        dialog.setTitle(dialogText);
        addFolder = view.getAddButton();
        addFolder.setText(buttonText);
        initComponents();
    }

    private void initComponents() {

        FlatSVGIcon icon = AppIcon.paintIcon(new FlatSVGIcon("icon/user_folder_icon.svg"));

        dialog.setIconImage(icon.getImage());

        JPanel fieldPane = new JPanel();

        folderTextField = new JTextField();
        JLabel folderLabel = new JLabel("Nome da Pasta:");
        folderTextField.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        folderLabel.setLabelFor(folderTextField);

        selectPathButton = new JButton(new FlatFileChooserNewFolderIcon());
        pathTextField = new JTextField();
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
                "[][][]"));

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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case FormModel.CAMINHO -> pathTextField.setText((String) evt.getNewValue());
            case FormModel.NOME_PASTA -> folderTextField.setText((String) evt.getNewValue());
            case FormModel.CONTA -> bankAccountComboBox.setSelectedItem(evt.getNewValue());
        }
    }

}
