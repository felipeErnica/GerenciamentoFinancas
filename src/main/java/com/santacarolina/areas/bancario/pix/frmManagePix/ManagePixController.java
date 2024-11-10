package com.santacarolina.areas.bancario.pix.frmManagePix;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.RowSorter;

import com.santacarolina.areas.bancario.pix.frmPix.PixForm;
import com.santacarolina.dao.PixDAO;
import com.santacarolina.dto.PixDTO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ManageController;
import com.santacarolina.model.ChavePix;
import com.santacarolina.ui.ManageControllerImpl;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;

@SuppressWarnings("unchecked")
public class ManagePixController implements ManageController {

    private PixTableModel model;
    private ManagePixView view;
    private RowSorter<PixTableModel> sorter;

    public ManagePixController(PixTableModel model, ManagePixView view) {
        ManageControllerImpl<PixDTO> baseController = new ManageControllerImpl<>(model, view, this);
        this.model = model;
        this.view = view;
        new FilterController(view.getFilterView(), model.getFilterModel());
        sorter = baseController.getSorter();
    }

    @Override
    public void table_onDoubleClick(MouseEvent e) {
        EventQueue.invokeLater(() -> {
            try {
                int viewRow = view.getTable().rowAtPoint(e.getPoint());
                int modelRow = sorter.convertRowIndexToModel(viewRow);
                ChavePix chavePix = model.getObject(modelRow).fromDTO();
                System.out.println(chavePix);
                PixForm.open(chavePix);
                model.requeryTable();
            } catch (FetchFailException ex) {
                CustomErrorThrower.throwError(ex);
                view.getDialog().dispose();
            }
        });
    }

    @Override
    public void addButton_onClick() {
        EventQueue.invokeLater(() -> {
            try {
                PixForm.openNew();
                model.requeryTable();
            } catch (FetchFailException e) {
                CustomErrorThrower.throwError(e);
            }
        });
    }

    @Override
    public void callDeleteDAO() {
        EventQueue.invokeLater(() -> {
            try {
                int[] rows = view.getTable().getSelectedRows();
                if (OptionDialog.showDeleteDialog(rows.length) != JOptionPane.YES_OPTION) return;
                for (int i = rows.length - 1; i >= 0; i--) {
                    int modelRow = sorter.convertRowIndexToModel(rows[i]);
                    PixDTO pix = model.getObject(modelRow);
                    new PixDAO().deleteById(pix.getId());
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
