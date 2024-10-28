package com.santacarolina.areas.bancario.banco.frmManageBanco;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.RowSorter;

import com.santacarolina.areas.bancario.banco.frmBanco.BancoForm;
import com.santacarolina.dao.BancoDAO;
import com.santacarolina.dao.DadoDAO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ManageController;
import com.santacarolina.model.Banco;
import com.santacarolina.model.ContaBancaria;
import com.santacarolina.model.DadoBancario;
import com.santacarolina.ui.ManageControllerImpl;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;

@SuppressWarnings("rawtypes")
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
    public void deleteButton_onClick() {
        try {
            BancoDAO bancoDAO = new BancoDAO();
            int[] selectRows = view.getTable().getSelectedRows();
                int result = OptionDialog.showDeleteCascadeDialog(selectRows.length);
            if (result != JOptionPane.YES_OPTION) return;
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
