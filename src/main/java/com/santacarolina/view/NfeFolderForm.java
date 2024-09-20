package com.santacarolina.view;

import com.santacarolina.model.beans.PastaContabil;
import com.santacarolina.ui.AddView;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.awt.*;

public class NfeFolderForm {

    private AddView form;
    private JDialog dialog;
    private JButton addButton;
    private JLabel pastaLabel;
    private JComboBox<PastaContabil> pastaContabilComboBox;

    public NfeFolderForm() {
        form = new AddView();
        initComponents();
    }

    private void initComponents() {

        dialog = form.getDialog();
        addButton = form.getAddButton();

        form.getDialog().setTitle("Escolher Pasta Contábil");
        form.getAddButton().setText("Selecionar");

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new MigLayout(
                "insets 20",
                "[right][grow,fill]"
        ));

        pastaContabilComboBox = new JComboBox<>();
        AutoCompleteDecorator.decorate(pastaContabilComboBox);

        pastaLabel = new JLabel("Pasta Contábil:");
        pastaLabel.setLabelFor(pastaContabilComboBox);

        centerPanel.add(pastaLabel);
        centerPanel.add(pastaContabilComboBox);

        dialog.add(centerPanel, BorderLayout.CENTER);

    }

    public JDialog getDialog() { return dialog; }
    public JButton getAddButton() { return addButton; }
    public JComboBox<PastaContabil> getPastaContabilComboBox() { return pastaContabilComboBox; }

}
