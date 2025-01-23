package com.santacarolina.areas.bancario.dadoBancario.frmDado;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.santacarolina.model.Banco;
import com.santacarolina.model.Contato;
import com.santacarolina.ui.AddView;

import net.miginfocom.swing.MigLayout;

public class FormView implements PropertyChangeListener {

    private JDialog dialog;
    private JLabel contatoLabel;
    private JComboBox<Contato> contactComboBox;
    private JLabel bancoLabel;
    private JComboBox<Banco> bankComboBox;
    private JLabel agenciaLabel;
    private JTextField agencyTextField;
    private JLabel contaLabel;
    private JTextField contaTextField;
    private JButton addAccount;

    public FormView(String dialogTitle, String buttonText) {
        AddView baseView = new AddView();
        addAccount = baseView.getAddButton();
        addAccount.setText(buttonText);
        dialog = baseView.getDialog();
        dialog.setTitle(dialogTitle);
        initComponents(baseView.getCenterPanel());
    }

    private void initComponents(JPanel centerPanel) {
        contatoLabel = new JLabel("Nome do Contato:");
        contactComboBox = new JComboBox<>();
        AutoCompleteDecorator.decorate(contactComboBox);
        contatoLabel.setLabelFor(contactComboBox);

        contaLabel = new JLabel("Número da Conta:");
        contaTextField = new JTextField();

        agenciaLabel = new JLabel("Agência:");
        agencyTextField = new JTextField();
        agenciaLabel.setLabelFor(agencyTextField);

        bancoLabel = new JLabel("Banco:");
        bankComboBox = new JComboBox<>();
        AutoCompleteDecorator.decorate(bankComboBox);
        bancoLabel.setLabelFor(bankComboBox);

        centerPanel.setLayout(new MigLayout("insets 25",
                "[right][fill, grow][grow]",
                "[][]20[][]"
        ));

        centerPanel.add(contatoLabel);
        centerPanel.add(contactComboBox,"span, wrap");
        centerPanel.add(bancoLabel);
        centerPanel.add(bankComboBox, "span, wrap");
        centerPanel.add(agenciaLabel);
        centerPanel.add(agencyTextField, "wrap");
        centerPanel.add(contaLabel);
        centerPanel.add(contaTextField);
    }

    public JDialog getDialog() { return dialog; }
    public JComboBox<Contato> getContactComboBox() { return contactComboBox; }
    public JComboBox<Banco> getBankComboBox() { return bankComboBox; }
    public JTextField getAgencyTextField() { return agencyTextField; }
    public JTextField getContaTextField() { return contaTextField; }
    public JButton getAddAccount() { return addAccount; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case FormModel.AGENCIA_TEXT -> agencyTextField.setText((String) evt.getNewValue());
            case FormModel.NUMCONTA_TEXT -> contaTextField.setText((String) evt.getNewValue());
            case FormModel.CONTATO_COMBOBOX -> contactComboBox.setSelectedItem(evt.getNewValue());
            case FormModel.BANCO_COMBOBOX -> bankComboBox.setSelectedItem(evt.getNewValue());
        }
    }

}
