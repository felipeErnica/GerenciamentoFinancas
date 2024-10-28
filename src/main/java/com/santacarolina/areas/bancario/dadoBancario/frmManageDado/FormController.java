package com.santacarolina.areas.bancario.dadoBancario.frmManageDado;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.RowSorter;

import com.santacarolina.areas.bancario.dadoBancario.frmDado.DadoForm;
import com.santacarolina.dao.DadoDAO;
import com.santacarolina.dto.DadoDTO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ManageController;
import com.santacarolina.model.DadoBancario;
import com.santacarolina.ui.ManageControllerImpl;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;

@SuppressWarnings("unchecked")
public class FormController implements ManageController {

    private TableModel model;
    private FormView view;
    private RowSorter<DadoDTO> sorter;

    public FormController(TableModel model, FormView view) {
        this.model = model;
        this.view = view;
        ManageControllerImpl<DadoDTO> baseController = new ManageControllerImpl<>(model, view, this);
        sorter = baseController.getSorter();
    }

    @Override
    public void table_onDoubleClick(MouseEvent e) {
        EventQueue.invokeLater(() -> {
            try {
                int viewRow = view.getTable().rowAtPoint(e.getPoint());
                int modelRow = sorter.convertRowIndexToModel(viewRow);
                DadoBancario dadoBancario = model.getObject(modelRow).fromDTO();
                DadoForm.open(dadoBancario);
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
                DadoForm.openNew();
                model.requeryTable();
            } catch (FetchFailException e) {
                CustomErrorThrower.throwError(e);
            }
        });
    }

    @Override
    public void deleteButton_onClick() {
        EventQueue.invokeLater(() -> {
            try {
                int[] rows = view.getTable().getSelectedRows();
                if (OptionDialog.showDeleteDialog(rows.length) != JOptionPane.YES_OPTION) return;
                for (int i = rows.length - 1; i >= 0; i--) {
                    int viewRow = rows[i];
                    int modelRow = sorter.convertRowIndexToModel(viewRow);
                    DadoBancario dadoBancario = model.getObject(modelRow).fromDTO();
                    new DadoDAO().deleteById(dadoBancario.getId());
                    model.removeRow(modelRow);
                }
            } catch (DeleteFailException e) {
                CustomErrorThrower.throwError(e);
            }
        });
    }

    @Override
    public void showView() { ViewInvoker.showMaximizedView(view.getDialog()); }

}
