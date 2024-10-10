package com.santacarolina.areas.bancario.dadoBancario.frmManageDado;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.ui.ActionSVGButton;
import com.santacarolina.ui.ManageViewImpl;
import com.santacarolina.util.AppIcon;

import javax.swing.*;
import javax.swing.table.TableColumn;

public class FormView {

    private JDialog dialog;
    private ActionSVGButton deleteButton;
    private ActionSVGButton addButton;
    private JTable table;

    public FormView() {
        ManageViewImpl view = new ManageViewImpl();
        this.dialog = view.getDialog();
        this.deleteButton = view.getDeleteButton();
        this.addButton = view.getAddButton();
        this.table = view.getTable();
        initComponents();
    }

    private void initComponents() {
        dialog.setTitle("Gerenciar Contas Bancárias");
        dialog.setIconImage(AppIcon.paintIcon(new FlatSVGIcon("icon/dado_bancario_icon.svg")).getImage());
        deleteButton.setText("Excluir Conta");
        addButton.setText("Adicionar Conta");
    }

    public void formatColumns () {
        for (int i = 0; i < table.getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            switch (i) {
                case 0 -> column.setPreferredWidth(400);
                case 1 -> column.setPreferredWidth(300);
                case 2 -> column.setPreferredWidth(80);
                case 3 -> column.setPreferredWidth(120);
                case 4 -> column.setPreferredWidth(100);
                case 5 -> column.setPreferredWidth(150);
            }
        }
    }

    public JDialog getDialog() { return dialog; }
    public ActionSVGButton getDeleteButton() { return deleteButton; }
    public JTable getTable() { return table; }
    public ActionSVGButton getAddButton() { return addButton; }

}
