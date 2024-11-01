package com.santacarolina.areas.categoria.frmManageCategoria;

import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import com.santacarolina.interfaces.ManageView;
import com.santacarolina.ui.ActionSVGButton;
import com.santacarolina.ui.ManageViewImpl;

public class FormView implements ManageView {

    private ManageViewImpl baseView;
    private FilterView filterView;

    public FormView() {
        baseView = new ManageViewImpl();
        filterView = new FilterView(baseView.getFilterPane());
        init();
    }

    private void init() {
        baseView.getDialog().setTitle("Gerenciar Categorias Contábeis");
        baseView.getDeleteButton().setText("Excluir Categoria");
        baseView.getAddButton().setText("Adicionar Categoria");
    }

    @Override
    public JTable getTable() { return baseView.getTable(); }

    @Override
    public JDialog getDialog() { return baseView.getDialog(); }

    @Override
    public ActionSVGButton getAddButton() { return baseView.getAddButton(); }

    @Override
    public ActionSVGButton getDeleteButton() { return baseView.getDeleteButton(); }

    @Override
    public void formatColumns() {
        int width = baseView.getTable().getWidth()/100;
        TableColumnModel columnModel = baseView.getTable().getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(width*20);
        columnModel.getColumn(1).setPreferredWidth(width*20);
        columnModel.getColumn(2).setPreferredWidth(width*60);
    }

    public FilterView getFilterView() { return filterView; }

}

