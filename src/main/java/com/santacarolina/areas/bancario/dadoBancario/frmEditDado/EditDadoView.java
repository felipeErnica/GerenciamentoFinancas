package com.santacarolina.areas.bancario.dadoBancario.frmEditDado;

import com.santacarolina.enums.TipoPix;
import com.santacarolina.model.beans.Banco;
import com.santacarolina.model.beans.Contato;
import com.santacarolina.ui.AddView;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.awt.*;

public class EditDadoView {

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

    public EditDadoView() {
        AddView baseView = new AddView();
        addAccount = baseView.getAddButton();
        dialog = baseView.getDialog();
        initComponents();
    }

    private void initComponents() {

        dialog.setTitle("Editar Dado Bancários");
        addAccount.setText("Salvar Conta");

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
        pixTypeComboBox.setEnabled(false);

        chaveLabel = new JLabel("Chave Pix:");
        pixKey = new JTextField();
        pixKey.setEnabled(false);

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

}
