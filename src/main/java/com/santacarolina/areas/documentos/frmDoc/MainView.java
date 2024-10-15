package com.santacarolina.areas.documentos.frmDoc;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.areas.documentos.frmDoc.docPanel.DocView;
import com.santacarolina.areas.documentos.frmDoc.dupPanel.DupView;
import com.santacarolina.areas.documentos.frmDoc.prodPanel.ProdView;
import com.santacarolina.util.AppIcon;
import com.santacarolina.util.MenuDecorator;

public class MainView {

    private JDialog dialog;
    private DocView docView;
    private DupView duplicatasPanel;
    private ProdView produtosPanel;
    private JButton updateDocButton;
    private JButton importNFeButton;

    public MainView() { initComponents(); }

    private void initComponents() {
        dialog = new JDialog();
        FlatSVGIcon icon = AppIcon.paintIcon(new FlatSVGIcon("icon/documento_icon.svg"));

        dialog.setTitle("Informações do Documento");
        dialog.setIconImage(icon.getImage());

        JTabbedPane mainPanel = new JTabbedPane();

        docView = new DocView();
        produtosPanel = new ProdView();
        duplicatasPanel = new DupView();

        mainPanel.addTab("Informações Gerais", docView.getMainPanel());
        mainPanel.addTab("Produtos e Serviços", produtosPanel.getMainPanel());
        mainPanel.addTab("Informações de Pagamento", duplicatasPanel.getMainPanel());

        updateDocButton = new JButton("Atualisar Documento", AppIcon.paintIcon(new FlatSVGIcon("icon/update_doc_icon.svg")));
        MenuDecorator.paintButton(updateDocButton);
        JPanel sidePanel = new JPanel(new BorderLayout());

        sidePanel.add(updateDocButton, BorderLayout.CENTER);
        MenuDecorator.paintPanel(sidePanel);

        dialog.add(sidePanel, BorderLayout.WEST);
        dialog.add(mainPanel, BorderLayout.CENTER);
    }

    public DocView getInfoPanel() { return docView; }
    public DupView getDuplicatasPanel() { return duplicatasPanel; }
    public ProdView getProdutosPanel() { return produtosPanel; }
    public JButton getUpdateDocButton() { return updateDocButton; }
    public JButton getImportNFeButton() { return importNFeButton; }
    public JDialog getDialog() { return dialog; }
}
