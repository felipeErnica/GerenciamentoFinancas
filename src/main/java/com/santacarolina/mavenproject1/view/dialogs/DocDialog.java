package com.santacarolina.mavenproject1.view.dialogs;

import com.santacarolina.mavenproject1.model.Contact;
import com.santacarolina.mavenproject1.model.FiscalDocument;
import com.santacarolina.mavenproject1.model.UserFolder;
import com.santacarolina.mavenproject1.model.enums.DocType;
import com.santacarolina.mavenproject1.services.CommonEvents;
import com.santacarolina.mavenproject1.services.ImageIconConfig;
import com.santacarolina.mavenproject1.view.components.MenuButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;
import java.util.Date;
import java.util.Formatter;

public class DocDialog extends JDialog {

    private static final Color MENU_BACKGROUND = new Color(82, 171, 231, 255);

    protected FiscalDocument fiscalDocument;

    private MainPanel mainPanel;
    private SidePanel sidePanel;

    public DocDialog() {
        initComponents();
        setModal(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setVisible(true);
    }
    public DocDialog(FiscalDocument fiscalDocument){
        this.fiscalDocument = fiscalDocument;
        initComponents();
        insertValues();
        setModal(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setVisible(true);
    }


    private void initComponents() {

        ImageIconConfig icon = new ImageIconConfig("src/main/resources/icon/doc_dialog_icon.png");

        setTitle("Informações do Documento");
        setIconImage(icon.getBufferedImage());
        setLayout(new BorderLayout());
        setResizable(false);

        mainPanel = new MainPanel();
        sidePanel = new SidePanel();

        add(sidePanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
        pack();

    }

    private void insertValues() {
        mainPanel.insertValues();
    }

    private class SidePanel extends JPanel {

        private JButton updateButton;
        private MenuButton deleteButton;
        private MenuButton importNFeButton;

        public SidePanel(){
            initComponents();
        }

        private void initComponents(){

            updateButton = new MenuButton("Atualizar Documento", MENU_BACKGROUND);
            deleteButton = new MenuButton("Excluir Documento", MENU_BACKGROUND);
            importNFeButton = new MenuButton("Importar NFe", MENU_BACKGROUND);

            setBackground(MENU_BACKGROUND);
            setLayout(new GridLayout(5,1));
            add(updateButton);
            add(deleteButton);
            add(importNFeButton);

        }
    }

    private class MainPanel extends JTabbedPane {

        private InfoPanel infoPanel;
        private ProductPanel productPanel;
        private PaymentPanel paymentPanel;

        public MainPanel(){
            initComponents();
        }

        private  void insertValues(){
            infoPanel.insertValues();
            productPanel.insertValues();
            paymentPanel.insertValues();
        }

        private void initComponents(){

            infoPanel = new InfoPanel();
            productPanel = new ProductPanel();
            paymentPanel = new PaymentPanel();

            addTab("Informações Gerais",infoPanel);
            addTab("Produtos e Serviços",productPanel);
            addTab("Informações de Pagamento",paymentPanel);
        }

        private class InfoPanel extends JPanel {

            private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

            private JRadioButton expenseButton;
            private JRadioButton incomeButton;

            private JComboBox<Contact> senderComboBox;
            private JComboBox<Contact> receiverComboBox;
            private JComboBox<UserFolder> userFolderComboBox;
            private JComboBox<String> docTypeComboBox;

            private JTextField emissionDate;
            private JTextField docValue;
            private JTextField docPath;
            private JTextField docNumber;

            private JLabel docTypeLabel;
            private JLabel senderLabel;
            private JLabel receiverLabel;
            private JLabel userFolderLabel;
            private JLabel emissionDateLabel;
            private JLabel docValueLabel;
            private JLabel docPathLabel;
            private JLabel docNumberLabel;

            public InfoPanel() {
                initComponents();
            }

            private void initComponents() {

                senderLabel = new JLabel("Emissor:");
                receiverLabel = new JLabel("Destinatário:");

                JPanel northLabelPanel = new JPanel();
                northLabelPanel.setLayout(new GridLayout(2, 1, 20, 20));
                northLabelPanel.add(senderLabel);
                northLabelPanel.add(receiverLabel);

                senderComboBox = new JComboBox<>();
                senderComboBox.addKeyListener(CommonEvents.nextComponentOnEnter(receiverComboBox));
                receiverComboBox = new JComboBox<>();
                receiverComboBox.addKeyListener(CommonEvents.nextComponentOnEnter(userFolderComboBox));

                JPanel northBoxPanel = new JPanel();
                northBoxPanel.setPreferredSize(new Dimension(500, 60));
                northBoxPanel.setLayout(new GridLayout(2, 1, 20, 20));
                northBoxPanel.add(senderComboBox);
                northBoxPanel.add(receiverComboBox);

                JPanel northPanel = new JPanel();
                northPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
                northPanel.add(northLabelPanel);
                northPanel.add(northBoxPanel);

                docNumberLabel = new JLabel("Número do Documento:");
                docTypeLabel = new JLabel("Tipo de Documento:");
                userFolderLabel = new JLabel("Pasta Contábil:");
                emissionDateLabel = new JLabel("Data de Emissão:");
                docValueLabel = new JLabel("Valor do Documento:");

                JPanel otherLabelPane = new JPanel();
                otherLabelPane.setLayout(new GridLayout(5, 1, 20, 20));
                otherLabelPane.add(userFolderLabel);
                otherLabelPane.add(emissionDateLabel);
                otherLabelPane.add(docTypeLabel);
                otherLabelPane.add(docNumberLabel);
                otherLabelPane.add(docValueLabel);

                userFolderComboBox = new JComboBox<>();
                userFolderComboBox.addKeyListener(CommonEvents.nextComponentOnEnter(emissionDate));

                emissionDate = new JTextField();
                emissionDate.addKeyListener(CommonEvents.nextComponentOnEnter(docTypeComboBox));
                emissionDate.addFocusListener(CommonEvents.transformDates());

                docTypeComboBox = new JComboBox(Arrays.stream(DocType.values())
                        .map(DocType::getDocType)
                        .toArray()
                );
                docTypeComboBox.addKeyListener(CommonEvents.nextComponentOnEnter(docNumber));

                docNumber = new JTextField();
                docNumber.addKeyListener(CommonEvents.nextComponentOnEnter(docValue));
                docValue = new JTextField();
                docValue.addKeyListener(CommonEvents.nextComponentOnEnter(expenseButton));

                JPanel otherFieldPane = new JPanel();
                otherFieldPane.setLayout(new GridLayout(5, 1, 20, 20));
                otherFieldPane.add(userFolderComboBox);
                otherFieldPane.add(emissionDate);
                otherFieldPane.add(docTypeComboBox);
                otherFieldPane.add(docNumber);
                otherFieldPane.add(docValue);

                JPanel otherPanel = new JPanel();
                otherPanel.setLayout(new GridLayout(1, 2, 20, 20));
                otherPanel.add(otherLabelPane);
                otherPanel.add(otherFieldPane);

                expenseButton = new JRadioButton("Despesa");
                expenseButton.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                expenseButton.addKeyListener(CommonEvents.nextComponentOnEnter(incomeButton));
                incomeButton = new JRadioButton("Receita");
                incomeButton.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                incomeButton.addKeyListener(CommonEvents.nextComponentOnEnter(docPath));

                ButtonGroup valueTypeGroup = new ButtonGroup();
                valueTypeGroup.add(expenseButton);
                valueTypeGroup.add(incomeButton);

                JPanel valuePanel = new JPanel();
                valuePanel.setBorder(BorderFactory.createTitledBorder("Fluxo de Caixa"));
                valuePanel.setLayout(new GridLayout(2, 1));
                valuePanel.add(expenseButton);
                valuePanel.add(incomeButton);

                JPanel centerPanel = new JPanel();
                centerPanel.setLayout(new GridLayout(1, 2, 20, 20));
                centerPanel.add(otherPanel);
                centerPanel.add(valuePanel);

                docPath = new JTextField();
                docPath.setPreferredSize(new Dimension(500, 20));
                docPathLabel = new JLabel("Caminho do Arquivo:");

                JPanel southPanel = new JPanel();
                southPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
                southPanel.add(docPathLabel);
                southPanel.add(docPath);

                setLayout(new BorderLayout(20, 20));
                setBorder(new EmptyBorder(20, 20, 20, 20));
                add(northPanel, BorderLayout.NORTH);
                add(centerPanel, BorderLayout.CENTER);
                add(southPanel, BorderLayout.SOUTH);

            }

            private void insertValues() {
                senderComboBox.setSelectedItem(fiscalDocument.getSender());
                receiverComboBox.setSelectedItem(fiscalDocument.getReceiver());
                docNumber.setText(fiscalDocument.getDocNumber());
                docValue.setText(String.valueOf(fiscalDocument.getValue()));
                docTypeComboBox.setSelectedItem(fiscalDocument.getDocType().getDocType());
                userFolderComboBox.setSelectedItem(fiscalDocument.getUserFolder());
                expenseButton.setSelected(fiscalDocument.isExpense());
                incomeButton.setSelected(fiscalDocument.isIncome());
                emissionDate.setText(fiscalDocument.getEmissionDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        }

        private class ProductPanel extends JPanel {

            private JTable productsTable;

            public ProductPanel (){
                initComponents();
            }

            private void initComponents(){

                DefaultTableModel tableModel = new DefaultTableModel();
                tableModel.addColumn("Classificação");
                tableModel.addColumn("Descrição");
                tableModel.addColumn("Unidade de Venda");
                tableModel.addColumn("Quantidade");
                tableModel.addColumn("Valor Unitário");
                tableModel.addColumn("Valor Total");

                productsTable = new JTable(tableModel);
                JScrollPane scrollPane = new JScrollPane(productsTable);

                setLayout(new GridLayout());
                add(scrollPane);

            }

            private void insertValues() {
            }
        }

        private class PaymentPanel extends JPanel {

            private JTable paymentTable;

            public PaymentPanel (){
                initComponents();
            }

            private void initComponents(){

                DefaultTableModel tableModel = new DefaultTableModel();
                tableModel.addColumn("Duplicata");
                tableModel.addColumn("Conta Bancária");
                tableModel.addColumn("Data de Vencimento");
                tableModel.addColumn("Método de Pagamento");
                tableModel.addColumn("Valor");

                paymentTable = new JTable(tableModel);
                JScrollPane scrollPane = new JScrollPane(paymentTable);

                setLayout(new GridLayout());
                add(scrollPane);

            }

            private void insertValues(){
            }
        }

    }
}
