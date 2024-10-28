package com.santacarolina.areas.bancario.contaBancaria.frmManageContaBancaria;

import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import com.santacarolina.interfaces.ManageView;
import com.santacarolina.ui.ActionSVGButton;
import com.santacarolina.ui.ManageViewImpl;

public class FormView implements ManageView {

    private ManageViewImpl baseView;

    public FormView() {
        baseView = new ManageViewImpl();
        baseView.getDialog().setTitle("Gerenciar Contas Bancárias");
        baseView.getAddButton().setText("Adicionar Conta");
        baseView.getDeleteButton().setText("Excluir Conta");
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
        columnModel.getColumn(2).setPreferredWidth(width*40);
        columnModel.getColumn(3).setPreferredWidth(width*10);
        columnModel.getColumn(4).setPreferredWidth(width*10);
    }

}

