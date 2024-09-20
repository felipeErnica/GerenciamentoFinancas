package com.santacarolina.areas.contato.frmEditContato;

import com.santacarolina.areas.contato.common.FormContatoView;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EditContatoView implements PropertyChangeListener {

    private FormContatoView view;
    private JDialog dialog;
    private JButton addButton;

    public EditContatoView() {
        this.view = new FormContatoView();
        addButton = view.getAddContact();
        dialog = view.getDialog();
        initComponents();
    }

    private void initComponents() {
        dialog.setTitle("Editar Contato");
        addButton.setText("Salvar Contato");
    }

    public FormContatoView getBaseView() { return view; }
    public JButton getAddButton() { return addButton; }
    public JDialog getDialog() { return dialog; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) { view.propertyChange(evt); }

}
