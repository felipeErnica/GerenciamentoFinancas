package com.santacarolina.areas.documentos.frmDoc.frmAddPix;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.model.ChavePix;
import com.santacarolina.model.Contato;
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

    public FormView() {
        initComponenents();
    }

    private void initComponenents() {

        AddView addView = new AddView();
        dialog = addView.getDialog();
        addButton = addView.getAddButton();

        dialog.setTitle("Selecionar Chave Pix");
        dialog.setIconImage(AppIcon.paintIcon(new FlatSVGIcon("icon/pix_icon.svg")).getImage());

        contatoComboBox = new JComboBox<>();
        AutoCompleteDecorator.decorate(contatoComboBox);
        contatoLabel = new JLabel("Nome do Contato:");
        contatoLabel.setLabelFor(contatoComboBox);

        addNewPix = new JButton("Adicionar Nova Chave", AppIcon.paintIcon(new FlatSVGIcon("icon/add_icon.svg")));

        tipoPixTextfield = new JTextField();
        tipoPixTextfield.setEditable(false);
        tipoChaveLabel = new JLabel("Tipo de Chave:");
        tipoChaveLabel.setLabelFor(tipoPixTextfield);

        chaveComboBox = new JComboBox<>();
        AutoCompleteDecorator.decorate(chaveComboBox);
        chaveLabel = new JLabel("Chave PIX:");
        chaveLabel.setLabelFor(chaveComboBox);

        bancoTextField = new JTextField();
        bancoTextField.setEditable(false);
        bancoLabel = new JLabel("Banco:");
        bancoLabel.setLabelFor(bancoTextField);

        agenciaTextField = new JTextField();
        agenciaTextField.setEditable(false);
        agenciaLabel = new JLabel("Agência:");
        agenciaLabel.setLabelFor(bancoTextField);

        contaTextField = new JTextField();
        contaTextField.setEditable(false);
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

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case FormModel.CONTATO -> contatoComboBox.setSelectedItem(evt.getNewValue());
            case FormModel.CHAVE_ENABLE -> {
                boolean fieldsEnabled = (Boolean) evt.getNewValue();
                chaveComboBox.setEnabled(fieldsEnabled);
                agenciaTextField.setEnabled(fieldsEnabled);
                bancoTextField.setEnabled(fieldsEnabled);
                contaTextField.setEnabled(fieldsEnabled);
                tipoPixTextfield.setEnabled(fieldsEnabled);
            }
            case FormModel.CHAVE_LIST -> {
                List<ChavePix> list = (List) evt.getNewValue();
                chaveComboBox.removeAllItems();
                if (list != null) list.forEach(c -> chaveComboBox.addItem(c));
            }
            case FormModel.CHAVE -> chaveComboBox.setSelectedItem(evt.getNewValue());
            case FormModel.TIPO_PIX -> tipoPixTextfield.setText((String) evt.getNewValue());
            case FormModel.BANCO -> bancoTextField.setText((String) evt.getNewValue());
            case FormModel.AGENCIA -> agenciaTextField.setText((String) evt.getNewValue());
            case FormModel.CONTA -> contaTextField.setText((String) evt.getNewValue());
        }
    }

}
