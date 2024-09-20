package com.santacarolina.areas.frmDoc;

import com.santacarolina.model.beans.ChavePix;
import com.santacarolina.model.beans.Contato;
import com.santacarolina.ui.AddView;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.awt.*;

public class AddPixDuplicataForm {

    private JDialog dialog;
    private JButton addButton;

    private JLabel contatoLabel;
    private JComboBox<Contato> contatoComboBox;
    private JButton addNewPix;

    private JLabel chaveLabel;
    private JComboBox<ChavePix> chaveComboBox;
    private JLabel tipoChaveLabel;
    private JTextField tipoPixTextfield;
    private JLabel bancoLabel;
    private JTextField bancoTextField;
    private JLabel agenciaLabel;
    private JTextField agenciaTextField;
    private JLabel contaLabel;
    private JTextField contaTextField;

    public AddPixDuplicataForm() {
        initComponenents();
    }

    private void initComponenents() {

        AddView addView = new AddView();
        dialog = addView.getDialog();
        addButton = addView.getAddButton();

        dialog.setTitle("Selecionar Chave Pix");

        contatoComboBox = new JComboBox<>();
        AutoCompleteDecorator.decorate(contatoComboBox);
        contatoLabel = new JLabel("Nome do Contato:");
        contatoLabel.setLabelFor(contatoComboBox);

        addNewPix = new JButton("Adicionar Nova Conta");

        tipoPixTextfield = new JTextField();
        tipoPixTextfield.setEnabled(false);
        tipoPixTextfield.setEditable(false);
        tipoChaveLabel = new JLabel("Tipo de Chave:");
        tipoChaveLabel.setLabelFor(tipoPixTextfield);

        chaveComboBox = new JComboBox<>();
        chaveComboBox.setEnabled(false);
        AutoCompleteDecorator.decorate(chaveComboBox);
        chaveLabel = new JLabel("Chave PIX:");
        chaveLabel.setLabelFor(chaveComboBox);

        bancoTextField = new JTextField();
        bancoTextField.setEditable(false);
        bancoTextField.setEnabled(false);
        bancoLabel = new JLabel("Banco:");
        bancoLabel.setLabelFor(bancoTextField);

        agenciaTextField = new JTextField();
        agenciaTextField.setEditable(false);
        agenciaTextField.setEnabled(false);
        agenciaLabel = new JLabel("Agência:");
        agenciaLabel.setLabelFor(bancoTextField);

        contaTextField = new JTextField();
        contaTextField.setEditable(false);
        contaTextField.setEnabled(false);
        contaLabel = new JLabel("Número da Conta:");
        contaLabel.setLabelFor(bancoTextField);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new MigLayout(
                "insets 20",
                "[right][][200, fill][][]",
                "[]30[][][][][]"));

        centerPanel.add(contatoLabel);
        centerPanel.add(contatoComboBox,"span 3");
        centerPanel.add(addNewPix, "wrap");
        centerPanel.add(chaveLabel,"skip");
        centerPanel.add(chaveComboBox, "wrap");
        centerPanel.add(tipoChaveLabel,"skip");
        centerPanel.add(tipoPixTextfield,"wrap");
        centerPanel.add(bancoLabel,"skip");
        centerPanel.add(bancoTextField,"wrap");
        centerPanel.add(agenciaLabel, "skip");
        centerPanel.add(agenciaTextField,"wrap");
        centerPanel.add(contaLabel, "skip");
        centerPanel.add(contaTextField);

        dialog.add(centerPanel, BorderLayout.CENTER);

        addButton.setText("Adicionar Chave");

    }

    public JDialog getDialog() { return dialog; }
    public JButton getAddButton() { return addButton; }
    public JComboBox<Contato> getContatoComboBox() { return contatoComboBox; }
    public JTextField getTipoPixTextfield() { return tipoPixTextfield; }
    public JComboBox<ChavePix> getChaveComboBox() { return chaveComboBox; }
    public JTextField getBancoTextField() { return bancoTextField; }
    public JTextField getAgenciaTextField() { return agenciaTextField; }
    public JTextField getContaTextField() { return contaTextField; }
    public JButton getAddNewPix() { return addNewPix; }

}
