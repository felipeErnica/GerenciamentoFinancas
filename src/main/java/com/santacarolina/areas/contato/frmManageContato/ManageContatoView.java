package com.santacarolina.areas.contato.frmManageContato;

import com.santacarolina.interfaces.ManageView;
import com.santacarolina.ui.ActionSVGButton;
import com.santacarolina.ui.ManageViewImpl;

import javax.swing.*;

public class ManageContatoView implements ManageView {

    private ManageViewImpl view;
    private JDialog dialog;
    private JTable table;
    private ActionSVGButton addButton;
    private ActionSVGButton deleteButton;

    public ManageContatoView() {
        this.view = new ManageViewImpl();
        table = view.getTable();

        addButton = view.getAddButton();
        deleteButton = view.getDeleteButton();
        dialog = view.getDialog();
        initComponents();
    }

    private void initComponents() {
        dialog.setTitle("Gerenciar Contatos");
        addButton.setText("Adicionar Contato");
        deleteButton.setText("Excluir Contatos");
    }

    public JTable getTable() { return table; }
    public JDialog getDialog() { return dialog; }
    public ActionSVGButton getAddButton() { return addButton; }
    public ActionSVGButton getDeleteButton() { return deleteButton; }

    @Override
    public void formatColumns() {
        table.getColumnModel().getColumn(0).setPreferredWidth(table.getWidth()/2);
    }

}
