package com.santacarolina.areas.frmDoc;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.enums.TipoDoc;
import com.santacarolina.model.beans.Contato;
import com.santacarolina.model.beans.PastaContabil;
import com.santacarolina.ui.ActionSVGButton;
import com.santacarolina.util.CommonEvents;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.combobox.EnumComboBoxModel;

import javax.swing.*;
import java.awt.*;

public class InfoPanel {

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

    public InfoPanel() {
        initComponents();
    }

    private void initComponents() {

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20,20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout(FlowLayout.LEADING,20,20));

        JLabel senderLabel = new JLabel("Emissor:");
        senderLabel.setLabelFor(senderComboBox);
        northPanel.add(senderLabel);

        senderComboBox = new JComboBox<>();
        senderComboBox.addKeyListener(CommonEvents.nextComponentOnEnter(userFolderComboBox));
        senderComboBox.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY,FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY_ALWAYS);
        AutoCompleteDecorator.decorate(senderComboBox);
        northPanel.add(senderComboBox);

        addNewContactButton = new ActionSVGButton("Adicionar Contato",new FlatSVGIcon("icon/add_contact_icon.svg"));
        northPanel.add(addNewContactButton);

        mainPanel.add(northPanel,BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1,2,20,20));

        JPanel centerLeftPanel = new JPanel();

        docNumber = new JTextField();
        docNumber.addKeyListener(CommonEvents.nextComponentOnEnter(docValue));
        docNumber.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY,FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY_ALWAYS);

        JLabel docNumberLabel = new JLabel("Número do Documento:");
        docNumberLabel.setLabelFor(docNumber);

        docTypeComboBox = new JComboBox<>(new EnumComboBoxModel<TipoDoc>(TipoDoc.class));
        docTypeComboBox.addKeyListener(CommonEvents.nextComponentOnEnter(docNumber));
        docTypeComboBox.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY,FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY_ALWAYS);
        AutoCompleteDecorator.decorate(docTypeComboBox);

        JLabel docTypeLabel = new JLabel("Tipo de Documento:");
        docTypeLabel.setLabelFor(docTypeComboBox);

        userFolderComboBox = new JComboBox<>();
        userFolderComboBox.addKeyListener(CommonEvents.nextComponentOnEnter(emissionDate));
        userFolderComboBox.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY,FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY_ALWAYS);
        AutoCompleteDecorator.decorate(userFolderComboBox);

        JLabel userFolderLabel = new JLabel("Pasta Contábil:");
        userFolderLabel.setLabelFor(userFolderComboBox);

        emissionDate = new JTextField();
        emissionDate.addKeyListener(CommonEvents.nextComponentOnEnter(docTypeComboBox));
        emissionDate.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY,FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY_ALWAYS);

        JLabel emissionDateLabel = new JLabel("Data de Emissão:");
        emissionDateLabel.setLabelFor(emissionDate);

        docValue = new JTextField();
        docValue.addKeyListener(CommonEvents.nextComponentOnEnter(expenseButton));
        docValue.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY,FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY_ALWAYS);

        JLabel docValueLabel = new JLabel("Valor do Documento:");
        docValueLabel.setLabelFor(docValue);

        GroupLayout centerLeftGroup = new GroupLayout(centerLeftPanel);
        centerLeftPanel.setLayout(centerLeftGroup);
        centerLeftGroup.setAutoCreateGaps(true);
        centerLeftGroup.setAutoCreateContainerGaps(true);

        centerLeftGroup.setHorizontalGroup(centerLeftGroup.createSequentialGroup()
                .addGroup(centerLeftGroup.createParallelGroup()
                        .addComponent(userFolderLabel)
                        .addComponent(emissionDateLabel)
                        .addComponent(docTypeLabel)
                        .addComponent(docNumberLabel)
                        .addComponent(docValueLabel)
                )
                .addGroup(centerLeftGroup.createParallelGroup()
                        .addComponent(userFolderComboBox)
                        .addComponent(emissionDate)
                        .addComponent(docTypeComboBox)
                        .addComponent(docNumber)
                        .addComponent(docValue)
                )
        );
        centerLeftGroup.setVerticalGroup(centerLeftGroup.createSequentialGroup()
                .addGroup(centerLeftGroup.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(userFolderLabel)
                        .addComponent(userFolderComboBox)
                )
                .addGroup(centerLeftGroup.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(emissionDateLabel)
                        .addComponent(emissionDate)
                )
                .addGroup(centerLeftGroup.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(docTypeLabel)
                        .addComponent(docTypeComboBox)
                )
                .addGroup(centerLeftGroup.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(docNumberLabel)
                        .addComponent(docNumber)
                )
                .addGroup(centerLeftGroup.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(docValueLabel)
                        .addComponent(docValue)
                )
        );

        centerPanel.add(centerLeftPanel);

        JPanel centerRightPanel = new JPanel();
        centerRightPanel.setLayout(new GridLayout(2,1,30,30));
        centerRightPanel.setBorder(BorderFactory.createTitledBorder("Fluxo de Caixa"));

        ButtonGroup valueTypeGroup = new ButtonGroup();

        expenseButton = new JRadioButton("Despesa");
        expenseButton.addKeyListener(CommonEvents.nextComponentOnEnter(incomeButton));
        expenseButton.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        expenseButton.setSelected(true);
        valueTypeGroup.add(expenseButton);
        centerRightPanel.add(expenseButton);

        incomeButton = new JRadioButton("Receita");
        incomeButton.addKeyListener(CommonEvents.nextComponentOnEnter(docPath));
        incomeButton.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        valueTypeGroup.add(incomeButton);
        centerRightPanel.add(incomeButton);

        centerPanel.add(centerRightPanel);

        mainPanel.add(centerPanel,BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.LEADING));

        JLabel docPathLabel = new JLabel("Caminho do Arquivo:");
        docPathLabel.setLabelFor(docPath);
        southPanel.add(docPathLabel);

        docPath = new JTextField();
        docPath.setPreferredSize(new Dimension(500, 20));
        docPath.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY,FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY_ALWAYS);
        southPanel.add(docPath);

        mainPanel.add(southPanel,BorderLayout.SOUTH);

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

}

