package com.santacarolina.areas.bancario.contaBancaria.frmContaBancaria;

import com.santacarolina.model.Banco;
import com.santacarolina.ui.AddView;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class FormView implements PropertyChangeListener {

    private JDialog dialog;

    private JComboBox<Banco> bancoComboBox;
    private JTextField agenciaTextField;
    private JTextField numeroContaTextField;
    private JTextField apelidoContaTextField;
    private JTextField abreviacaoText;
    private JButton addConta;

    public FormView(String dialogTitle, String buttonText) {
        AddView view = new AddView();
        dialog = view.getDialog();
        addConta = view.getAddButton();
        dialog.setTitle(dialogTitle);
        addConta.setText(buttonText);
        initComponents();
    }

    private void initComponents() {

        bancoComboBox = new JComboBox<>();
        AutoCompleteDecorator.decorate(bancoComboBox);
        JLabel bancoLabel = new JLabel("Banco:");
        bancoLabel.setLabelFor(bancoComboBox);

        agenciaTextField = new JTextField();
        JLabel agenciaLabel = new JLabel("Agência:");
        agenciaLabel.setLabelFor(agenciaTextField);

        numeroContaTextField = new JTextField();
        JLabel numeroLabel = new JLabel("Número da Conta:");
        numeroLabel.setLabelFor(numeroContaTextField);

        apelidoContaTextField = new JTextField();
        JLabel apelidoLabel = new JLabel("Apelido da Conta:");
        apelidoLabel.setLabelFor(apelidoContaTextField);

        JLabel abreviacaoLabel = new JLabel("Abreviação da Conta:");
        abreviacaoText = new JTextField();
        abreviacaoLabel.setLabelFor(abreviacaoText);

        JPanel centerPane = new JPanel(new MigLayout("insets 20",
                "[][][grow, fill]",
                "[]20[][][]"
        ));

        centerPane.add(bancoLabel);
        centerPane.add(bancoComboBox, "span, wrap, wmax 400");
        centerPane.add(agenciaLabel, "skip");
        centerPane.add(agenciaTextField, "wrap");
        centerPane.add(numeroLabel,"skip");
        centerPane.add(numeroContaTextField, "wrap");
        centerPane.add(apelidoLabel, "skip");
        centerPane.add(apelidoContaTextField, "wrap");
        centerPane.add(abreviacaoLabel, "skip");
        centerPane.add(abreviacaoText);

        dialog.add(centerPane, BorderLayout.CENTER);

    }

    public JDialog getDialog() { return dialog; }
    public JComboBox<Banco> getBancoComboBox() { return bancoComboBox; }
    public JTextField getAgenciaTextField() { return agenciaTextField; }
    public JTextField getNumeroContaTextField() { return numeroContaTextField; }
    public JTextField getApelidoContaTextField() { return apelidoContaTextField; }
    public JTextField getAbreviacaoText() { return abreviacaoText; }
    public JButton getAddConta() { return addConta; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case FormModel.ABREVIACAO -> abreviacaoText.setText((String) evt.getNewValue());
            case FormModel.APELIDO -> apelidoContaTextField.setText((String) evt.getNewValue());
            case FormModel.BANCO -> {
                Banco banco = (Banco) evt.getNewValue();
                bancoComboBox.setSelectedItem(banco);
            }
            case FormModel.AGENCIA -> agenciaTextField.setText((String) evt.getNewValue());
            case FormModel.NUMERO -> numeroContaTextField.setText((String) evt.getNewValue());
        }
    }

}