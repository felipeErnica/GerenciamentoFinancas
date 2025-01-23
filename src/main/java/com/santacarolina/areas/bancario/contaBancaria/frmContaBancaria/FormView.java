package com.santacarolina.areas.bancario.contaBancaria.frmContaBancaria;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.model.Banco;
import com.santacarolina.ui.ActionSVGButton;
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
    private JButton addBanco;
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
        agenciaTextField.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        agenciaLabel.setLabelFor(agenciaTextField);

        numeroContaTextField = new JTextField();
        JLabel numeroLabel = new JLabel("Número da Conta:");
        numeroContaTextField.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        numeroLabel.setLabelFor(numeroContaTextField);

        apelidoContaTextField = new JTextField();
        JLabel apelidoLabel = new JLabel("Apelido da Conta:");
        apelidoContaTextField.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        apelidoLabel.setLabelFor(apelidoContaTextField);

        JLabel abreviacaoLabel = new JLabel("Abreviação da Conta:");
        abreviacaoText = new JTextField();
        abreviacaoText.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        abreviacaoLabel.setLabelFor(abreviacaoText);

        addBanco = new ActionSVGButton("Adicionar Novo Banco", new FlatSVGIcon("icon/add_icon.svg"));

        JPanel centerPane = new JPanel(new MigLayout("insets 20",
                "[][][grow, fill][]",
                "[]20[][][]"
        ));

        centerPane.add(bancoLabel);
        centerPane.add(bancoComboBox, "span 2, wmax 400");
        centerPane.add(addBanco, "wrap");
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
    public JButton getAddBanco() { return addBanco; }

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
