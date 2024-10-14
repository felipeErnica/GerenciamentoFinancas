package com.santacarolina.areas.pastaContabil.frmManagePasta;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.interfaces.ManageView;
import com.santacarolina.ui.ActionSVGButton;
import com.santacarolina.ui.ManageViewImpl;
import com.santacarolina.util.AppIcon;

import javax.swing.*;
import javax.swing.table.TableColumnModel;

public class FormView implements ManageView {

    private ManageViewImpl baseView;
    private JTable table;

    public FormView() {
        this.baseView = new ManageViewImpl();
        table = baseView.getTable();
        baseView.getAddButton().setText("Adicionar Pasta");
        baseView.getDeleteButton().setText("Excluir Pasta");
        baseView.getDialog().setTitle("Gerenciar Pastas Contábeis");
        baseView.getDialog().setIconImage(AppIcon.paintIcon(new FlatSVGIcon("icon/pasta_icon.svg")).getImage());
    }

    @Override
    public JTable getTable() { return table; }

    @Override
    public JDialog getDialog() { return baseView.getDialog(); }

    @Override
    public ActionSVGButton getAddButton() { return baseView.getAddButton(); }

    @Override
    public ActionSVGButton getDeleteButton() { return baseView.getDeleteButton(); }

    @Override
    public void formatColumns() {
        int width = table.getWidth()/100;
        TableColumnModel columnModel = table.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(width*20);
        columnModel.getColumn(1).setPreferredWidth(width*60);
        columnModel.getColumn(2).setPreferredWidth(width*20);
    }
}
