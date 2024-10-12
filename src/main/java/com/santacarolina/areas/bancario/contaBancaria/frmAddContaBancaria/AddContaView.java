package com.santacarolina.areas.pastaContabil.frmAddContaBancaria;

import com.santacarolina.model.Banco;
import com.santacarolina.ui.AddView;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.awt.*;

public class AddContaView {

    private JDialog dialog;

    private JComboBox<Banco> bancoComboBox;
    private JTextField agenciaTextField;
    private JTextField numeroContaTextField;
    private JTextField apelidoContaTextField;
    private JButton addConta;

    public AddContaView() {
        AddView view = new AddView();
        dialog = view.getDialog();
        addConta = view.getAddButton();
        initComponents();
    }

    private void initComponents() {
        dialog.setTitle("Adicionar Conta Bancária");
        addConta.setText("Adicionar Conta");

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

        JPanel centerPane = new JPanel(new MigLayout("insets 25",
                "[right][right][grow, fill]",
                "[]20[][][]"
        ));

        centerPane.add(bancoLabel);
        centerPane.add(bancoComboBox, "span, wrap, wmax 400");
        centerPane.add(agenciaLabel, "skip");
        centerPane.add(agenciaTextField, "wrap");
        centerPane.add(numeroLabel,"skip");
        centerPane.add(numeroContaTextField, "wrap");
        centerPane.add(apelidoLabel, "skip");
        centerPane.add(apelidoContaTextField);

        dialog.add(centerPane, BorderLayout.CENTER);

    }

    public JDialog getDialog() { return dialog; }
    public JComboBox<Banco> getBancoComboBox() { return bancoComboBox; }
    public JTextField getAgenciaTextField() { return agenciaTextField; }
    public JTextField getNumeroContaTextField() { return numeroContaTextField; }
    public JTextField getApelidoContaTextField() { return apelidoContaTextField; }
    public JButton getAddConta() { return addConta; }

}
