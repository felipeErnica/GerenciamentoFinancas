package com.santacarolina.areas.documentos.frmSelectNFe;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.model.DocumentoFiscal;
import com.santacarolina.ui.AddView;
import com.santacarolina.util.AppIcon;

import net.miginfocom.swing.MigLayout;

public class FormView {

    private AddView view;
    private JDialog dialog;
    private JPanel panel;
    private JButton loadNfeButton;
    private JLabel nfeLabel;
    private JComboBox<DocumentoFiscal> nfeComboBox;

    public FormView() {
        this.view = new AddView();
        this.dialog = view.getDialog();
        this.loadNfeButton = view.getAddButton();
        init();
    }

    private void init() {
        dialog.setTitle("Abrir NFe");
        dialog.setIconImage(AppIcon.paintIcon(new FlatSVGIcon("icon/nfe_icon.svg")).getImage());
        loadNfeButton.setText("Abrir NFe");
        nfeLabel = new JLabel("NFes:");
        nfeComboBox = new JComboBox<>();
        panel = new JPanel(new MigLayout("insets 20", "[][grow, fill]"));
        panel.add(nfeLabel);
        panel.add(nfeComboBox);
        dialog.add(panel, BorderLayout.CENTER);
    }

    public JDialog getDialog() { return dialog; }
    public JPanel getPanel() { return panel; }
    public JButton getLoadNfeButton() { return loadNfeButton; }
    public JComboBox<DocumentoFiscal> getNfeComboBox() { return nfeComboBox; }

}
