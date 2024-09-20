package com.santacarolina.view;

import com.santacarolina.model.beans.DocumentoFiscal;
import com.santacarolina.ui.AddView;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import javax.swing.*;
import java.awt.*;

public class NfeSelectionForm {

    private JDialog dialog;
    private AddView form;
    private JButton addButton;
    private JLabel nfeLabel;
    private JComboBox<DocumentoFiscal> nfeComboBox;

    public NfeSelectionForm() {
        form = new AddView();
        initComponents();
    }

    private void initComponents() {

        dialog = form.getDialog();
        addButton = form.getAddButton();

        form.getDialog().setTitle("Escolher NFe");
        form.getAddButton().setText("Selecionar");

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new MigLayout(
                "insets 20",
                "[right][grow,fill]"
        ));

        nfeComboBox = new JComboBox<>();
        AutoCompleteDecorator.decorate(nfeComboBox);

        nfeLabel = new JLabel("Notas:");
        nfeLabel.setLabelFor(nfeComboBox);

        centerPanel.add(nfeLabel);
        centerPanel.add(nfeComboBox);

        dialog.add(centerPanel, BorderLayout.CENTER);

    }

    public JDialog getDialog() { return dialog; }
    public JButton getAddButton() { return addButton; }
    public JComboBox<DocumentoFiscal> getNfeComboBox() { return nfeComboBox; }

}

