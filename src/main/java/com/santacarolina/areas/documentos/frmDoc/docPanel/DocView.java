package com.santacarolina.areas.documentos.frmDoc.docPanel;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.enums.TipoDoc;
import com.santacarolina.model.Contato;
import com.santacarolina.model.PastaContabil;
import com.santacarolina.ui.ActionSVGButton;
import com.santacarolina.util.CommonEvents;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.combobox.EnumComboBoxModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DocView implements PropertyChangeListener {

    private JPanel mainPanel;
    private JButton addNewContactButton;

    private JRadioButton expenseButton;
    private JRadioButton incomeButton;

    private JComboBox<Contato> senderComboBox;
    private JComboBox<PastaContabil> userFolderComboBox;
    private JComboBox<TipoDoc> docTypeComboBox;

    private JTextField emissionDate;
    private JTextField docValue;
    private JTextField docPath;
    private JTextField docNumber;

    public DocView() { initComponents(); }

    private void initComponents() {

        JLabel senderLabel = new JLabel("Emissor:");
        senderLabel.setLabelFor(senderComboBox);

        senderComboBox = new JComboBox<>();
        senderComboBox.addKeyListener(CommonEvents.nextComponentOnEnter(userFolderComboBox));
        senderComboBox.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY,FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY_ALWAYS);
        AutoCompleteDecorator.decorate(senderComboBox);

        addNewContactButton = new ActionSVGButton("Adicionar Contato",new FlatSVGIcon("icon/contato_add_icon.svg"));

        JLabel docNumberLabel = new JLabel("Número do Documento:");
        docNumber = new JTextField();
        docNumber.addKeyListener(CommonEvents.nextComponentOnEnter(docValue));
        docNumber.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY,FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY_ALWAYS);
        docNumberLabel.setLabelFor(docNumber);

        JLabel docTypeLabel = new JLabel("Tipo de Documento:");
        docTypeComboBox = new JComboBox<>(new EnumComboBoxModel<TipoDoc>(TipoDoc.class));
        docTypeComboBox.addKeyListener(CommonEvents.nextComponentOnEnter(docNumber));
        docTypeComboBox.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY,FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY_ALWAYS);
        AutoCompleteDecorator.decorate(docTypeComboBox);
        docTypeLabel.setLabelFor(docTypeComboBox);

        JLabel userFolderLabel = new JLabel("Pasta Contábil:");
        userFolderComboBox = new JComboBox<>();
        userFolderComboBox.addKeyListener(CommonEvents.nextComponentOnEnter(emissionDate));
        userFolderComboBox.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY,FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY_ALWAYS);
        AutoCompleteDecorator.decorate(userFolderComboBox);
        userFolderLabel.setLabelFor(userFolderComboBox);

        JLabel emissionDateLabel = new JLabel("Data de Emissão:");
        emissionDate = new JTextField();
        emissionDate.addKeyListener(CommonEvents.nextComponentOnEnter(docTypeComboBox));
        emissionDate.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY,FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY_ALWAYS);
        emissionDateLabel.setLabelFor(emissionDate);

        JLabel docValueLabel = new JLabel("Valor do Documento:");
        docValue = new JTextField();
        docValue.addKeyListener(CommonEvents.nextComponentOnEnter(expenseButton));
        docValue.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY,FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY_ALWAYS);
        docValueLabel.setLabelFor(docValue);

        expenseButton = new JRadioButton("Despesa");
        expenseButton.addKeyListener(CommonEvents.nextComponentOnEnter(incomeButton));

        incomeButton = new JRadioButton("Receita");
        incomeButton.addKeyListener(CommonEvents.nextComponentOnEnter(docPath));

        ButtonGroup valueTypeGroup = new ButtonGroup();
        valueTypeGroup.add(expenseButton);
        valueTypeGroup.add(incomeButton);

        JPanel radioPanel = new JPanel(new MigLayout("insets 10, flowy","fill","fill, grow"));
        radioPanel.setBorder(BorderFactory.createTitledBorder("Fluxo de Caixa"));

        radioPanel.add(expenseButton);
        radioPanel.add(incomeButton, "ay bottom");

        JLabel docPathLabel = new JLabel("Caminho do Arquivo:");
        docPath = new JTextField();
        docPath.setPreferredSize(new Dimension(500, 20));
        docPath.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY,FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY_ALWAYS);
        docPathLabel.setLabelFor(docPath);

        mainPanel = new JPanel(new MigLayout("insets 20",
                "[right][fill, grow]15[fill, grow][]",
                "[]push[][][][][]push[]"
        ));

        mainPanel.add(senderLabel);
        mainPanel.add(senderComboBox, "span 2");
        mainPanel.add(addNewContactButton, "wrap");

        mainPanel.add(docTypeLabel);
        mainPanel.add(docTypeComboBox);
        mainPanel.add(radioPanel, "wrap, spany 5, spanx 2, grow");
        mainPanel.add(docNumberLabel);
        mainPanel.add(docNumber, "wrap");
        mainPanel.add(userFolderLabel);
        mainPanel.add(userFolderComboBox, "wrap");
        mainPanel.add(emissionDateLabel);
        mainPanel.add(emissionDate, "wrap");
        mainPanel.add(docValueLabel);
        mainPanel.add(docValue, "wrap");
        mainPanel.add(docPathLabel);
        mainPanel.add(docPath, "span 3");

    }

    public JButton getAddNewContactButton() { return addNewContactButton; }
    public JRadioButton getExpenseButton() { return expenseButton; }
    public JRadioButton getIncomeButton() { return incomeButton; }
    public JComboBox<Contato> getSenderComboBox() { return senderComboBox; }
    public JComboBox<PastaContabil> getUserFolderComboBox() { return userFolderComboBox; }
    public JComboBox<TipoDoc> getDocTypeComboBox() { return docTypeComboBox; }
    public JTextField getEmissionDate() { return emissionDate; }
    public JTextField getDocValue() { return docValue; }
    public JTextField getDocPath() { return docPath; }
    public JTextField getDocNumber() { return docNumber; }
    public JPanel getMainPanel() { return mainPanel; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String textValue = evt.getNewValue() != null ? String.valueOf(evt.getNewValue()) : null;
        switch (evt.getPropertyName()) {
            case DocModel.FLUXO_CAIXA -> {
                FluxoCaixa fluxoCaixa = (FluxoCaixa) evt.getNewValue();
                expenseButton.setSelected(fluxoCaixa == FluxoCaixa.DESPESA);
                incomeButton.setSelected(fluxoCaixa == FluxoCaixa.RECEITA);
            }
            case DocModel.EMISSOR -> senderComboBox.setSelectedItem(evt.getNewValue());
            case DocModel.PASTA -> userFolderComboBox.setSelectedItem(evt.getNewValue());
            case DocModel.TIPO_DOC -> docTypeComboBox.setSelectedItem(evt.getNewValue());
            case DocModel.DATA_EMISSAO -> emissionDate.setText(textValue);
            case DocModel.INVALID_DATE -> {
                boolean invalidDate = (Boolean) evt.getNewValue();
                if (invalidDate) emissionDate.putClientProperty(FlatClientProperties.OUTLINE, "error");
                else emissionDate.putClientProperty(FlatClientProperties.OUTLINE, null);
            }
            case DocModel.VALOR -> docValue.setText(textValue);
            case DocModel.VALOR_INVALID -> {
                boolean invalidValue = (Boolean) evt.getNewValue();
                if (invalidValue) docValue.putClientProperty(FlatClientProperties.OUTLINE, "error");
                else docValue.putClientProperty(FlatClientProperties.OUTLINE, null);
            }
            case DocModel.CAMINHO -> docPath.setText(textValue);
            case DocModel.NUM_DOC -> docNumber.setText(textValue);
            case DocModel.NUM_DOC_ENABLE -> docNumber.setEnabled((Boolean) evt.getNewValue());
            case DocModel.NUM_DOC_INVALID -> {
                boolean numDocInvalid = (boolean) evt.getNewValue();
                if (numDocInvalid) docNumber.putClientProperty(FlatClientProperties.OUTLINE, "error");
                else docNumber.putClientProperty(FlatClientProperties.OUTLINE, null);
            }
        }
    }

}

