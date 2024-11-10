package com.santacarolina.areas.bancario.dadoBancario.frmManageDado;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.util.List;

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
import com.santacarolina.util.ViewInvoker;

@SuppressWarnings("unchecked")
public class FormController implements ManageController<DadoDTO> {

    private TableModel model;
    private FormView view;
    private RowSorter<DadoDTO> sorter;

    public FormController(TableModel model, FormView view) throws FetchFailException {
        this.model = model;
        this.view = view;
        new FilterController(view.getFilterView(), model.getFilterModel());
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
    public void showView() { ViewInvoker.showMaximizedView(view.getDialog()); }

    @Override
    public void callDeleteDAO(List<DadoDTO> list) {
        try {
            new DadoDAO().deleteAll(list);
        } catch (DeleteFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
