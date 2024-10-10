package com.santacarolina.areas.bancario.banco.frmManageBanco;

import com.santacarolina.areas.bancario.banco.frmAddBanco.AddBancoForm;
import com.santacarolina.areas.bancario.banco.frmEditBanco.EditBanco;
import com.santacarolina.dao.BancoDAO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ManageController;
import com.santacarolina.model.Banco;
import com.santacarolina.ui.ManageControllerImpl;
import com.santacarolina.util.CustomErrorThrower;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class FormController implements ManageController {

    private ManageControllerImpl<Banco> baseController;
    private FormView view;
    private TableModel model;
    private RowSorter sorter;

    public FormController(FormView view, TableModel model) {
        this.baseController = new ManageControllerImpl<>(model, view, this);
        sorter = baseController.getSorter();
    }

    @Override
    public void table_onDoubleClick(MouseEvent e) {
        EventQueue.invokeLater(() -> {
            try {
                int row = view.getTable().rowAtPoint(e.getPoint());
                int modelRow = sorter.convertRowIndexToModel(row);
                Banco banco = model.getObject(modelRow);
                EditBanco.open(banco);
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
                AddBancoForm.open();
                model.requeryTable();
            } catch (FetchFailException e) {
                CustomErrorThrower.throwError(e);
            }
        });
    }

    @Override
    public void deleteButton_onClick() {
        try {
            BancoDAO bancoDAO = new BancoDAO();
            int[] selectRows = view.getTable().getSelectedRows();
            for (int i = selectRows.length - 1; i >= 0; i--) {
                int modelRow = sorter.convertRowIndexToModel(selectRows[i]);
                Banco banco = model.getObject(modelRow);
                bancoDAO.deleteById(banco.getId());
                model.removeRow(modelRow);
            }
        } catch (DeleteFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    @Override
    public void showView() { baseController.showView(); }

}
