package com.santacarolina.areas.contato.frmAddContato;

import com.santacarolina.areas.contato.common.FormContatoView;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AddContatoView implements PropertyChangeListener {

    private FormContatoView baseView;
    private JDialog dialog;
    private JButton addContact;

    public AddContatoView() {
        baseView = new FormContatoView();
        dialog = baseView.getDialog();
        addContact = baseView.getAddContact();
        initComponents();
    }

    private void initComponents() {
        dialog.setTitle("Adicionar Contato");
        addContact.setText("Adicionar Contato");
    }

    public JDialog getDialog() { return dialog; }
    public FormContatoView getBaseView() { return baseView; }
    public JButton getAddContact() { return addContact; }
    public void propertyChange(PropertyChangeEvent evt) { baseView.propertyChange(evt); }

}
