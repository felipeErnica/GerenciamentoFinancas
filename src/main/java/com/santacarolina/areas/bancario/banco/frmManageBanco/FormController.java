package com.santacarolina.areas.bancario.banco.frmManageBanco;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.RowSorter;

import com.santacarolina.areas.bancario.banco.frmBanco.BancoForm;
import com.santacarolina.dao.BancoDAO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ManageController;
import com.santacarolina.interfaces.UpdateListener;
import com.santacarolina.model.Banco;
import com.santacarolina.ui.ManageControllerImpl;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;

@SuppressWarnings("rawtypes")
public class FormController implements ManageController {

    private FormView view;
    private TableModel model;
    private RowSorter sorter;

    public FormController(FormView view, TableModel model) {
        ManageControllerImpl baseController = new ManageControllerImpl<>(model, view, this);
        sorter = baseController.getSorter();
        this.view = view;
        this.model = model;
        view.getContatoSearchField().getDocument().addDocumentListener((UpdateListener) e -> contatoSearchField_afterUpdate());; 
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
    public void showView() { ViewInvoker.showMaximizedView(view.getDialog()); }

}
