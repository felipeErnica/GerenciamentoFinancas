package com.santacarolina.areas.bancario.conciliacao.frmManageConciliados;

import javax.swing.JDialog;
import javax.swing.JTable;

import com.santacarolina.interfaces.ManageView;
import com.santacarolina.ui.ActionSVGButton;
import com.santacarolina.ui.ManageViewImpl;

/**
 * FormView
 */
public class FormView implements ManageView {

    private ManageViewImpl baseView;
    private FilterView filterView;

    public FormView() {
        baseView = new ManageViewImpl();
        filterView = new FilterView(baseView.getFilterPane());
        baseView.getDialog().setTitle("Gerenciar Arquivos Conciliados");
        baseView.getAddButton().setText("Conciliar Arquivos");
        baseView.getDeleteButton().setText("Excluir Conciliações");
    }

    @Override
    public JTable getTable() { return baseView.getTable(); }

    @Override
    public JDialog getDialog() { return baseView.getDialog(); }

    @Override
    public ActionSVGButton getAddButton() { return baseView.getAddButton(); }

    @Override
    public ActionSVGButton getDeleteButton() { return baseView.getDeleteButton(); }

    public FilterView getFilterView() { return filterView; }

    @Override
    public void formatColumns() {
    }
    
}
