package com.santacarolina.areas.bancario.banco.frmManageBanco;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.RowSorter;

import com.santacarolina.areas.bancario.banco.frmBanco.BancoForm;
import com.santacarolina.dao.BancoDAO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.DocumentChangeListener;
import com.santacarolina.interfaces.ManageController;
import com.santacarolina.model.Banco;
import com.santacarolina.ui.ManageControllerImpl;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.ViewInvoker;

@SuppressWarnings("rawtypes")
public class FormController implements ManageController<Banco> {

    private FormView view;
    private TableModel model;
    private RowSorter sorter;

    public FormController(FormView view, TableModel model) {
        ManageControllerImpl baseController = new ManageControllerImpl<>(model, view, this);
        sorter = baseController.getSorter();
        this.view = view;
        this.model = model;
        view.getContatoSearchField().getDocument().addDocumentListener((DocumentChangeListener) e -> contatoSearchField_afterUpdate());; 
    }

    private void contatoSearchField_afterUpdate() { model.setFilterSearch(view.getContatoSearchField().getText()); }

    @Override
    public void table_onDoubleClick(MouseEvent e) {
        EventQueue.invokeLater(() -> {
            try {
                int row = view.getTable().rowAtPoint(e.getPoint());
                int modelRow = sorter.convertRowIndexToModel(row);
                Banco banco = model.getObject(modelRow);
                BancoForm.open(banco);
                model.requeryTable();
            } catch (FetchFailException ex) {
                CustomErrorThrower.throwError(ex);
            }
        });
    }

    @Override
    public void addButton_onClick() {
        EventQueue.invokeLater(() -> {
            try {
                BancoForm.openNew();
                model.requeryTable();
                } catch (FetchFailException e) {
                CustomErrorThrower.throwError(e);
            }
        });
    }

    @Override
    public void callDeleteDAO(List<Banco> list) {
        try {
            new BancoDAO().deleteAll(list);
        } catch (DeleteFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    @Override
    public void showView() { ViewInvoker.showMaximizedView(view.getDialog()); }

}
