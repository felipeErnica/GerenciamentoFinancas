package com.santacarolina.areas.bancario.banco.frmManageBanco;

import com.santacarolina.interfaces.ManageView;
import com.santacarolina.ui.ActionSVGButton;
import com.santacarolina.ui.ManageViewImpl;

import javax.swing.*;

public class FormView implements ManageView {

    private ManageViewImpl baseView;

    public FormView() {
        this.baseView = new ManageViewImpl();
        init();
    }

    private void init() {
        baseView.getDialog().setTitle("Gerenciar Bancos");
        baseView.getAddButton().setText("Adicionar Banco");
        baseView.getDeleteButton().setText("Excluir Banco");
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

}