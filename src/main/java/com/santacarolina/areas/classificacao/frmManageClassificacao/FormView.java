package com.santacarolina.areas.classificacao.frmManageClassificacao;

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
        init();
    }

    private void init() {
        baseView.getDeleteButton().setText("Excluir Classificação");
        baseView.getAddButton().setText("Adicionar Classificação");
        baseView.getDialog().setTitle("Gerenciar Classificação");
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
    }

    public FilterView getFilterView() { return filterView; }

}
