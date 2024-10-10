package com.santacarolina.areas.contato.common;

import com.formdev.flatlaf.FlatClientProperties;
import com.santacarolina.ui.AddView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class FormContatoView implements PropertyChangeListener {

    private AddView addView;
    private JDialog dialog;
    private JButton addContact;

    private JLabel nameLabel;
    private JTextField nameTextField;

    private JCheckBox docCheckBox;

    private JLabel cpfLabel;
    private JTextField cpfTextField;
    private JLabel cnpjLabel;
    private JTextField cnpjTextField;
    private JLabel ieLabel;
    private JTextField ieTextField;

    public FormContatoView() {
        addView = new AddView();
        dialog = addView.getDialog();
        addContact = addView.getAddButton();
        initComponents();
    }

    private void initComponents() {

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new MigLayout("wrap, insets 20",
                "[grow, fill]",
                "[][]"));

        JPanel namePane = new JPanel();
        namePane.setLayout(new MigLayout("",
                "[][grow, fill]",
                "[]30[]"));

        nameTextField = new JTextField();
        nameLabel = new JLabel("Nome do Contato:");
        nameLabel.setLabelFor(nameTextField);

        docCheckBox = new JCheckBox("Adicionar Documentos");

        namePane.add(nameLabel);
        namePane.add(nameTextField, "wrap");
        namePane.add(docCheckBox, "span");

        JPanel docPanel = new JPanel();
        docPanel.setLayout(new MigLayout("wrap",
                "[][grow, fill]",
                "[][][]"));

        docPanel.setBorder(BorderFactory.createTitledBorder("Documentos"));

        cpfTextField = new JTextField();
        cpfTextField.setEnabled(false);
        cpfLabel = new JLabel("CPF:");
        cpfLabel.setLabelFor(cpfTextField);

        cnpjTextField = new JTextField();
        cnpjTextField.setEnabled(false);
        cnpjLabel = new JLabel("CNPJ:");
        cnpjLabel.setLabelFor(cnpjTextField);

        ieTextField = new JTextField();
        ieTextField.setEnabled(false);
        ieLabel = new JLabel("IE:");
        ieLabel.setLabelFor(ieLabel);

        docPanel.add(cpfLabel);
        docPanel.add(cpfTextField);
        docPanel.add(cnpjLabel);
        docPanel.add(cnpjTextField);
        docPanel.add(ieLabel);
        docPanel.add(ieTextField);

        centerPanel.add(namePane);
        centerPanel.add(docPanel);

        dialog.add(centerPanel, BorderLayout.CENTER);

    }

    public JDialog getDialog() {
        return dialog;
    }

    public JButton getAddContact() {
        return addContact;
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public JCheckBox getDocCheckBox() {
        return docCheckBox;
    }

    public JTextField getCpfTextField() {
        return cpfTextField;
    }

    public JTextField getCnpjTextField() {
        return cnpjTextField;
    }

    public JTextField getIeTextField() {
        return ieTextField;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case FormContatoModel.NOME -> nameTextField.setText((String) evt.getNewValue());
            case FormContatoModel.DOC_CHECKBOX -> docCheckBox.setSelected((Boolean) evt.getNewValue());
            case FormContatoModel.DOC_ENABLED -> {
                Boolean docEnabled = (Boolean) evt.getNewValue();
                cpfTextField.setEnabled(docEnabled);
                cnpjTextField.setEnabled(docEnabled);
                ieTextField.setEnabled(docEnabled);
            }
            case FormContatoModel.CNPJ -> cnpjTextField.setText((String) evt.getNewValue());
            case FormContatoModel.CPF -> cpfTextField.setText((String) evt.getNewValue());
            case FormContatoModel.IE -> ieTextField.setText((String) evt.getNewValue());
            case FormContatoModel.CNPJ_INVALID -> {
                if ((Boolean) evt.getNewValue())
                    cnpjTextField.putClientProperty(FlatClientProperties.OUTLINE, "error");
                else
                    cnpjTextField.putClientProperty(FlatClientProperties.OUTLINE, null);
            }
            case FormContatoModel.CPF_INVALID -> {
                if ((Boolean) evt.getNewValue())
                    cpfTextField.putClientProperty(FlatClientProperties.OUTLINE, "error");
                else
                    cpfTextField.putClientProperty(FlatClientProperties.OUTLINE, null);
            }
            case FormContatoModel.IE_INVALID -> {
                if ((Boolean) evt.getNewValue())
                    ieTextField.putClientProperty(FlatClientProperties.OUTLINE, "error");
                else
                    ieTextField.putClientProperty(FlatClientProperties.OUTLINE, null);
            }
        }
    }

}
