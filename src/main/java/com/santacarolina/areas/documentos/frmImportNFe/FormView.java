package com.santacarolina.areas.documentos.frmImportNFe;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.model.PastaContabil;
import com.santacarolina.ui.AddView;
import com.santacarolina.util.AppIcon;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class FormView {

    private AddView main;
    private JDialog dialog;
    private JPanel centerPanel;
    private JLabel pastaLabel;
    private JComboBox<PastaContabil> pastaContabilComboBox;
    private JButton importNFeButton;

    public FormView() {
        this.main = new AddView();
        dialog = main.getDialog();
        importNFeButton = main.getAddButton();
        init();
    }

    private void init() {
        dialog.setTitle("Selecionar Pasta Contábil");
        dialog.setIconImage(AppIcon.paintIcon(new FlatSVGIcon("icon/pasta_icon.svg")).getImage());
        importNFeButton.setText("Importar NFe's");
        pastaLabel = new JLabel("Selecionar Pasta:");
        pastaContabilComboBox = new JComboBox<>();
        centerPanel = new JPanel(new MigLayout("insets 20","[][grow, fill]"));
        centerPanel.add(pastaLabel);
        centerPanel.add(pastaContabilComboBox);
        dialog.add(centerPanel, BorderLayout.CENTER);
    }

    public JDialog getDialog() { return dialog; }
    public JComboBox<PastaContabil> getPastaContabilComboBox() { return pastaContabilComboBox; }
    public JButton getImportNFeButton() { return importNFeButton; }

}
