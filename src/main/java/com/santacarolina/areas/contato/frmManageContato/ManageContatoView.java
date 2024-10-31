package com.santacarolina.areas.contato.frmManageContato;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.interfaces.ManageView;
import com.santacarolina.ui.ActionSVGButton;
import com.santacarolina.ui.ManageViewImpl;
import com.santacarolina.util.AppIcon;

import javax.swing.*;

public class ManageContatoView implements ManageView {

    private ManageViewImpl view;
    private JDialog dialog;
    private JTable table;
    private ActionSVGButton addButton;
    private ActionSVGButton deleteButton;
    private FilterView filterView;

    public ManageContatoView() {
        this.view = new ManageViewImpl();
        table = view.getTable();
        filterView = new FilterView(view.getFilterPane());

        addButton = view.getAddButton();
        deleteButton = view.getDeleteButton();
        dialog = view.getDialog();
        initComponents();
    }

    private void initComponents() {
        dialog.setTitle("Gerenciar Contatos");
        dialog.setIconImage(AppIcon.paintIcon(new FlatSVGIcon("icon/contato_menu_icon.svg")).getImage());
        addButton.setText("Adicionar Contato");
        addButton.setIcon(AppIcon.paintIcon(new FlatSVGIcon("icon/contato_add_icon.svg")));
        deleteButton.setText("Excluir Contatos");
        deleteButton.setIcon(AppIcon.paintIcon(new FlatSVGIcon("icon/delete_icon.svg")));
    }

    public JTable getTable() { return table; }
    public JDialog getDialog() { return dialog; }
    public ActionSVGButton getAddButton() { return addButton; }
    public ActionSVGButton getDeleteButton() { return deleteButton; }
    public FilterView getFilterView() { return filterView; }

    @Override
    public void formatColumns() {
        table.getColumnModel().getColumn(0).setPreferredWidth(table.getWidth()/2);
    }

}
