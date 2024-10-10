package com.santacarolina.areas.bancario.dadoBancario.common;

import com.formdev.flatlaf.FlatClientProperties;
import com.santacarolina.enums.TipoPix;
import com.santacarolina.model.Banco;
import com.santacarolina.model.Contato;
import com.santacarolina.ui.AddView;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DadoBancarioFormView implements PropertyChangeListener {

    private JDialog dialog;
    private JLabel contatoLabel;
    private JComboBox<Contato> contactComboBox;
    private JLabel bancoLabel;
    private JComboBox<Banco> bankComboBox;
    private JLabel agenciaLabel;
    private JTextField agencyTextField;
    private JLabel contaLabel;
    private JTextField contaTextField;
    private JLabel pixCheckBoxLabel;
    private JCheckBox pixCheckBox;
    private JLabel pixTypeLabel;
    private JComboBox<TipoPix> pixTypeComboBox;
    private JLabel chaveLabel;
    private JTextField pixKey;
    private JButton addAccount;

    public DadoBancarioFormView() {
        AddView baseView = new AddView();
        addAccount = baseView.getAddButton();
        dialog = baseView.getDialog();
        initComponents();
    }

    private void initComponents() {
        JPanel centerPane = new JPanel();
        JPanel infoPane = new JPanel();
        JPanel pixPane = new JPanel();

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

        pixCheckBox = new JCheckBox("Adicionar Chave Pix");

        pixTypeLabel = new JLabel("Tipo de Chave:");
        pixTypeComboBox = new JComboBox<>();
        AutoCompleteDecorator.decorate(pixTypeComboBox);

        chaveLabel = new JLabel("Chave Pix:");
        pixKey = new JTextField();
        pixKey.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");

        pixPane.setBorder(BorderFactory.createTitledBorder("Informações de Pix"));
        pixPane.setLayout(new MigLayout("",
                "[right][fill, grow]",
                "[][]"
        ));

        pixPane.add(pixTypeLabel);
        pixPane.add(pixTypeComboBox, "wrap");
        pixPane.add(chaveLabel);
        pixPane.add(pixKey);

        infoPane.setLayout(new MigLayout("insets 25",
                "[right][fill, grow][grow]",
                "[][]20[][]20[][]"
        ));

        infoPane.add(contatoLabel);
        infoPane.add(contactComboBox,"span, wrap");
        infoPane.add(bancoLabel);
        infoPane.add(bankComboBox, "span, wrap");
        infoPane.add(contaLabel);
        infoPane.add(contaTextField, "wrap");
        infoPane.add(agenciaLabel);
        infoPane.add(agencyTextField, "wrap");
        infoPane.add(pixCheckBox, "wrap, span 2, align left");
        infoPane.add(pixPane, "span 2, align left, grow");

        dialog.add(infoPane, BorderLayout.CENTER);
    }

    public JDialog getDialog() { return dialog; }
    public JComboBox<Contato> getContactComboBox() { return contactComboBox; }
    public JComboBox<Banco> getBankComboBox() { return bankComboBox; }
    public JTextField getAgencyTextField() { return agencyTextField; }
    public JTextField getContaTextField() { return contaTextField; }
    public JCheckBox getPixCheckBox() { return pixCheckBox; }
    public JComboBox<TipoPix> getPixTypeComboBox() { return pixTypeComboBox; }
    public JTextField getPixKey() { return pixKey; }
    public JButton getAddAccount() { return addAccount; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case DadoBancarioFormModel.AGENCIA_TEXT -> agencyTextField.setText((String) evt.getNewValue());
            case DadoBancarioFormModel.NUMCONTA_TEXT -> contaTextField.setText((String) evt.getNewValue());
            case DadoBancarioFormModel.PIX_CHECKBOX -> pixCheckBox.setSelected((Boolean) evt.getNewValue());
            case DadoBancarioFormModel.CONTATO_COMBOBOX -> contactComboBox.setSelectedItem(evt.getNewValue());
            case DadoBancarioFormModel.BANCO_COMBOBOX -> bankComboBox.setSelectedItem(evt.getNewValue());
            case DadoBancarioFormModel.CHAVE_TEXT -> pixKey.setText((String) evt.getNewValue());
            case DadoBancarioFormModel.CHAVE_INVALID -> {
                if ((boolean) evt.getNewValue()) pixKey.putClientProperty(FlatClientProperties.OUTLINE, "error");
                else pixKey.putClientProperty(FlatClientProperties.OUTLINE, null);
            }
            case DadoBancarioFormModel.CHAVE_ENABLED -> pixKey.setEnabled((Boolean) evt.getNewValue());
            case DadoBancarioFormModel.TIPOPIX_ENABLED -> pixTypeComboBox.setEnabled((Boolean) evt.getNewValue());
            case DadoBancarioFormModel.TIPOPIX_COMBOBOX -> pixTypeComboBox.setSelectedItem(evt.getNewValue());
        }
    }

}
