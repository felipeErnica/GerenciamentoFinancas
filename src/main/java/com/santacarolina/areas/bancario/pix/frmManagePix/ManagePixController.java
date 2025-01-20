package com.santacarolina.areas.bancario.pix.frmManagePix;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.RowSorter;

import com.santacarolina.areas.bancario.pix.frmPix.PixForm;
import com.santacarolina.dao.PixDAO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ManageController;
import com.santacarolina.model.ChavePix;
import com.santacarolina.ui.ManageControllerImpl;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.ViewInvoker;

@SuppressWarnings("unchecked")
public class ManagePixController implements ManageController<ChavePix> {

    private PixTableModel model;
    private ManagePixView view;
    private RowSorter<PixTableModel> sorter;

    public ManagePixController(PixTableModel model, ManagePixView view) {
        ManageControllerImpl<ChavePix> baseController = new ManageControllerImpl<>(model, view, this);
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
                ChavePix chavePix = model.getObject(modelRow);
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
    public void showView() { ViewInvoker.showMaximizedView(view.getDialog()); }

    @Override
    public void callDeleteDAO(List<ChavePix> list) {
        try {
            new PixDAO().deleteAll(list);
        } catch (DeleteFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
