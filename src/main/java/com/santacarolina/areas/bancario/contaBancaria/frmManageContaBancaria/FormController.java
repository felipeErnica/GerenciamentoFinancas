package com.santacarolina.areas.bancario.contaBancaria.frmManageContaBancaria;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;

import javax.swing.RowSorter;

import com.santacarolina.areas.bancario.contaBancaria.frmContaBancaria.ContaForm;
import com.santacarolina.dao.ContaDAO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ManageController;
import com.santacarolina.model.ContaBancaria;
import com.santacarolina.ui.ManageControllerImpl;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.ViewInvoker;

@SuppressWarnings("rawtypes")
public class FormController implements ManageController {

    private FormView view;
    private ContaTableModel tableModel;
    private RowSorter sorter;

    public FormController(FormView view, ContaTableModel tableModel) {
        ManageControllerImpl<ContaBancaria> baseController = new ManageControllerImpl<>(tableModel, view, this);
        this.view = view;
        this.tableModel = tableModel;
        sorter = baseController.getSorter();
    }

    @Override
    public void showView() { ViewInvoker.showMaximizedView(view.getDialog()); }

    @Override
    public void table_onDoubleClick(MouseEvent e) {
        EventQueue.invokeLater(() -> {
            int row = view.getTable().rowAtPoint(e.getPoint());
            int modelRow = sorter.convertRowIndexToModel(row);
            ContaBancaria conta = tableModel.getObject(modelRow);
            ContaForm.open(conta);
            try {
                tableModel.requeryTable();
            } catch (FetchFailException ex) {
                CustomErrorThrower.throwError(ex);
            }
        });
    }

    @Override
    public void addButton_onClick() { 
        EventQueue.invokeLater(() -> {
            ContaForm.openNew();
            try {
                tableModel.requeryTable();
            } catch (FetchFailException e) {
                CustomErrorThrower.throwError(e);
            }
        }); 
    }

    @Override
    public void deleteButton_onClick() {
        try {
            int[] rows = view.getTable().getSelectedRows();
            for (int i = rows.length - 1; i >= 0; i--) {
                int modelRow = sorter.convertRowIndexToModel(rows[i]);
                ContaBancaria contaBancaria = tableModel.getObject(modelRow);
                new ContaDAO().deleteById(contaBancaria.getId());
                tableModel.requeryTable();
            }
        } catch (DeleteFailException | FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}

