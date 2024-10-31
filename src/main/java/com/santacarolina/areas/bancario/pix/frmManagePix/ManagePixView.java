package com.santacarolina.areas.bancario.pix.frmManagePix;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.interfaces.AbstractFilterView;
import com.santacarolina.interfaces.FilterViewContainer;
import com.santacarolina.interfaces.ManageView;
import com.santacarolina.ui.ActionSVGButton;
import com.santacarolina.ui.ManageViewImpl;
import com.santacarolina.util.AppIcon;

import javax.swing.*;
import javax.swing.table.TableColumnModel;

public class ManagePixView implements ManageView, FilterViewContainer {

    private JDialog dialog;
    private ActionSVGButton deleteButton;
    private ActionSVGButton addButton;
    private JTable table;
    private FilterView filterView;

    public ManagePixView() {
        ManageViewImpl view = new ManageViewImpl();
        filterView = new FilterView(view.getFilterPane());
        this.dialog = view.getDialog();
        this.deleteButton = view.getDeleteButton();
        this.addButton = view.getAddButton();
        this.table = view.getTable();
        initComponents();
    }

    private void initComponents() {
        dialog.setTitle("Gerenciar Chaves Pix");
        dialog.setIconImage(AppIcon.paintIcon(new FlatSVGIcon("icon/pix_menu_icon.svg")).getImage());
        deleteButton.setText("Excluir Chave");
        addButton.setText("Adicionar Chave");
    }

    public void formatColumns () {
        int columnSize = table.getSize().width/17;
        TableColumnModel model = table.getColumnModel();
        model.getColumn(0).setPreferredWidth(6*columnSize);
        model.getColumn(1).setPreferredWidth(2*columnSize);
        model.getColumn(2).setPreferredWidth(2*columnSize);
        model.getColumn(3).setPreferredWidth(4*columnSize);
        model.getColumn(4).setPreferredWidth(columnSize);
        model.getColumn(5).setPreferredWidth(2*columnSize);
    }

    public JDialog getDialog() { return dialog; }
    public ActionSVGButton getDeleteButton() { return deleteButton; }
    public JTable getTable() { return table; }
    public ActionSVGButton getAddButton() { return addButton; }

    @Override
    public FilterView getFilterView() { return filterView; }

}
