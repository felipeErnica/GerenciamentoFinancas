package com.santacarolina.areas.frmDoc;

import com.santacarolina.ui.SideMenu;
import com.santacarolina.util.ImageIconConfig;

import javax.swing.*;
import java.awt.*;

public class DocForm {

    private JDialog dialog;
    private InfoPanel infoPanel;
    private DuplicatasDocPanel duplicatasPanel;
    private ProdutosDocPanel produtosPanel;
    private JButton updateDocButton;
    private JButton deleteDocButton;
    private JButton importNFeButton;

    public DocForm() {
        initComponents();
    }

    private void initComponents() {

        dialog = new JDialog();
        ImageIconConfig icon = new ImageIconConfig("src/main/resources/icon/doc_dialog_icon.png");

        dialog.setTitle("Informações do Documento");
        dialog.setIconImage(icon.getBufferedImage());
        dialog.setLayout(new BorderLayout());
        dialog.setSize(1200, 400);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);

        JTabbedPane mainPanel = new JTabbedPane();
        infoPanel = new InfoPanel();

        produtosPanel = new ProdutosDocPanel();
        duplicatasPanel = new DuplicatasDocPanel();
        mainPanel.addTab("Informações Gerais", infoPanel.getMainPanel());
        mainPanel.addTab("Produtos e Serviços", produtosPanel.getMainPanel());
        mainPanel.addTab("Informações de Pagamento", duplicatasPanel.getMainPanel());

        updateDocButton = new JButton("Atualisar Documento");
        deleteDocButton = new JButton("Excluir Documento");
        importNFeButton = new JButton("Importar NFe");

        SideMenu sidePanel = new SideMenu(new Dimension(200, 400))
                .put(updateDocButton, "icon/update_doc_icon.svg")
                .put(importNFeButton, "icon/import_nfe_icon.svg")
                .put(deleteDocButton, "icon/delete_doc_icon.svg")
                .build();

        dialog.add(sidePanel, BorderLayout.WEST);
        dialog.add(mainPanel, BorderLayout.CENTER);
    }

    public InfoPanel getInfoPanel() { return infoPanel; }
    public DuplicatasDocPanel getDuplicatasPanel() { return duplicatasPanel; }
    public ProdutosDocPanel getProdutosPanel() { return produtosPanel; }
    public JButton getUpdateDocButton() { return updateDocButton; }
    public JButton getDeleteDocButton() { return deleteDocButton; }
    public JButton getImportNFeButton() { return importNFeButton; }
    public JDialog getDialog() { return dialog; }
}