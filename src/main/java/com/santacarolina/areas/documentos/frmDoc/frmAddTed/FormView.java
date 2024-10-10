package com.santacarolina.areas.documentos.frmDoc.frmAddTed;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.model.Contato;
import com.santacarolina.model.DadoBancario;
import com.santacarolina.ui.AddView;
import com.santacarolina.util.AppIcon;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class FormView implements PropertyChangeListener {

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

    public FormView() { initComponenents(); }

    private void initComponenents() {

        AddView addView = new AddView();
        dialog = addView.getDialog();
        addButton = addView.getAddButton();

        dialog.setTitle("Selecionar Conta TED");
        dialog.setIconImage(AppIcon.paintIcon(new FlatSVGIcon("icon/bank_account_icon.svg")).getImage());

        contatoComboBox = new JComboBox<>();
        AutoCompleteDecorator.decorate(contatoComboBox);
        contatoLabel = new JLabel("Nome do Contato:");
        contatoLabel.setLabelFor(contatoComboBox);

        addNewAccount = new JButton("Adicionar Nova Conta", AppIcon.paintIcon(new FlatSVGIcon("icon/add_icon.svg")));

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
    public JButton getAddNewAccount() { return addNewAccount; }
    public JComboBox<Contato> getContatoComboBox() { return contatoComboBox; }
    public JTextField getBancoTextField() { return bancoTextField; }
    public JTextField getAgenciaTextField() { return agenciaTextField; }
    public JComboBox<DadoBancario> getContaComboBox() { return contaComboBox; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case FormModel.CONTATO -> contatoComboBox.setSelectedItem(evt.getNewValue());
            case FormModel.DADO -> contaComboBox.setSelectedItem(evt.getNewValue());
            case FormModel.DADO_LIST -> {
                List<DadoBancario> list = (List) evt.getNewValue();
                contaComboBox.removeAllItems();
                if (list != null) list.forEach(d -> contaComboBox.addItem(d));
            }
            case FormModel.DADO_ENABLE -> {
                boolean dadoEnabled = (Boolean) evt.getNewValue();
                contaComboBox.setEnabled(dadoEnabled);
                agenciaTextField.setEnabled(dadoEnabled);
                bancoTextField.setEnabled(dadoEnabled);
            }
            case FormModel.AGENCIA -> agenciaTextField.setText((String) evt.getNewValue());
            case FormModel.BANCO -> bancoTextField.setText((String) evt.getNewValue());
        }
    }

}
