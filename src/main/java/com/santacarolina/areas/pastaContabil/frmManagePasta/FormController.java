package com.santacarolina.areas.pastaContabil.frmManagePasta;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.RowSorter;

import com.santacarolina.areas.pastaContabil.frmPastaContabil.PastaContabilForm;
import com.santacarolina.dao.PastaDAO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ManageController;
import com.santacarolina.model.PastaContabil;
import com.santacarolina.ui.ManageControllerImpl;
import com.santacarolina.util.CustomErrorThrower;

@SuppressWarnings("unchecked")
public class FormController implements ManageController<PastaContabil> {

    private ManageControllerImpl<PastaContabil> baseController;
    private RowSorter<PastaTableModel> sorter;
    private FormView view;
    private PastaTableModel model;

    public FormController(FormView view, PastaTableModel model) {
        this.view = view;
        this.model = model;
        this.baseController = new ManageControllerImpl<>(model, view, this);
        sorter = baseController.getSorter();
    }

    @Override
    public void showView() { baseController.showView(); }

    @Override
    public void table_onDoubleClick(MouseEvent e) {
        EventQueue.invokeLater(() -> {
            try {
                int row = view.getTable().rowAtPoint(e.getPoint());
                int modelRow = sorter.convertRowIndexToModel(row);
                PastaContabil pasta = model.getObject(modelRow);
                PastaContabilForm.open(pasta);
                model.requeryTable();
            } catch (FetchFailException ex) {
                CustomErrorThrower.throwError(ex);
            }
        });
    }

    @Override
    public void addButton_onClick() { EventQueue.invokeLater(PastaContabilForm::openNew); }

    @Override
    public void callDeleteDAO(List<PastaContabil> list) {
        try {
            new PastaDAO().deleteAll(list);
        } catch (DeleteFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
