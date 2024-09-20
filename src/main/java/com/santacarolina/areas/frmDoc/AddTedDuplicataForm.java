package com.santacarolina.areas.frmDoc;

import com.santacarolina.model.beans.Contato;
import com.santacarolina.model.beans.DadoBancario;
import com.santacarolina.ui.AddView;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.awt.*;

public class AddTedDuplicataForm {

    private JDialog dialog;
    private JButton addButton;

    private JLabel contatoLabel;
    private JComboBox<Contato> contatoComboBox;
    private JButton addNewAccount;

    private JLabel contaLabel;
    private JComboBox<DadoBancario> contaComboBox;
    private JLabel agenciaLabel;
    private JTextField agenciaTextField;
    private JLabel bancoLabel;
    private JTextField bancoTextField;

    public AddTedDuplicataForm() {
        initComponenents();
    }

    private void initComponenents() {

        AddView addView = new AddView();
        dialog = addView.getDialog();
        addButton = addView.getAddButton();

        dialog.setTitle("Selecionar Conta TED");

        contatoComboBox = new JComboBox<>();
        AutoCompleteDecorator.decorate(contatoComboBox);
        contatoLabel = new JLabel("Nome do Contato:");
        contatoLabel.setLabelFor(contatoComboBox);

        addNewAccount = new JButton("Adicionar Nova Conta");

        bancoTextField = new JTextField();
        bancoTextField.setEditable(false);
        bancoTextField.setEnabled(false);
        bancoLabel = new JLabel("Banco:");
        bancoLabel.setLabelFor(bancoTextField);

        agenciaTextField = new JTextField();
        agenciaTextField.setEditable(false);
        agenciaTextField.setEnabled(false);
        agenciaLabel = new JLabel("Agência:");
        agenciaLabel.setLabelFor(agenciaTextField);

        contaComboBox = new JComboBox<>();
        contaComboBox.setEditable(false);
        contaComboBox.setEnabled(false);
        AutoCompleteDecorator.decorate(contaComboBox);
        contaLabel = new JLabel("Número da Conta:");
        contaLabel.setLabelFor(contaComboBox);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new MigLayout(
                "insets 20",
                "[right][][200, fill][][]",
                "[]30[][][][][]"));

        centerPanel.add(contatoLabel);
        centerPanel.add(contatoComboBox,"span 3");
        centerPanel.add(addNewAccount, "wrap");
        centerPanel.add(contaLabel, "skip");
        centerPanel.add(contaComboBox, "wrap");
        centerPanel.add(agenciaLabel, "skip");
        centerPanel.add(agenciaTextField,"wrap");
        centerPanel.add(bancoLabel,"skip");
        centerPanel.add(bancoTextField);

        dialog.add(centerPanel, BorderLayout.CENTER);

        addButton.setText("Adicionar TED");

    }

    public JDialog getDialog() { return dialog; }
    public JButton getAddButton() { return addButton; }
    public JComboBox<Contato> getContatoComboBox() { return contatoComboBox; }
    public JTextField getBancoTextField() { return bancoTextField; }
    public JTextField getAgenciaTextField() { return agenciaTextField; }
    public JComboBox<DadoBancario> getContaComboBox() { return contaComboBox; }

}
