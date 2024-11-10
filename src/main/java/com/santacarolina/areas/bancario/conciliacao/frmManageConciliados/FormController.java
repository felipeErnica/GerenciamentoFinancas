package com.santacarolina.areas.bancario.conciliacao.frmManageConciliados;

import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.table.TableColumnModel;

import com.santacarolina.areas.bancario.conciliacao.frmConciliacao.ConciliacaoForm;
import com.santacarolina.dao.ConciliacaoDAO;
import com.santacarolina.dto.ConciliacaoDTO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ManageController;
import com.santacarolina.ui.ManageControllerImpl;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.ViewInvoker;

/**
 * FormController
 */
public class FormController implements ManageController<ConciliacaoDTO> {

    private FormView view;
    private ConciliadosTableModel tableModel;

    public FormController(FormView view, ConciliadosTableModel tableModel) {
        this.view = view;
        this.tableModel = tableModel;
        new ManageControllerImpl<>(tableModel, view, this);
        new FilterController(view.getFilterView(), tableModel.getFilterModel());
        tableModel.getFilterModel().addPropertyChangeListener(view.getFilterView());
        init();
    }

    private void init() {
        ConciliacaoRenderer cellRenderer = new ConciliacaoRenderer();
        TableColumnModel columnModel = view.getTable().getColumnModel();
        columnModel.getColumn(0).setCellRenderer(cellRenderer);
        columnModel.getColumn(1).setCellRenderer(cellRenderer);
        columnModel.getColumn(2).setCellRenderer(cellRenderer);
        columnModel.getColumn(3).setCellRenderer(cellRenderer);
        columnModel.getColumn(4).setCellRenderer(cellRenderer);
        columnModel.getColumn(5).setCellRenderer(cellRenderer);
        columnModel.getColumn(6).setCellRenderer(cellRenderer);
        columnModel.getColumn(7).setCellRenderer(cellRenderer);
        columnModel.getColumn(8).setCellRenderer(cellRenderer);
        columnModel.getColumn(9).setCellRenderer(cellRenderer);
    }

    @Override
    public void showView() { ViewInvoker.showMaximizedView(view.getDialog()); }

    @Override
    public void table_onDoubleClick(MouseEvent e) {}

    @Override
    public void addButton_onClick() {
        try {
            new ConciliacaoForm();
            tableModel.requeryTable();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    @Override
    public void callDeleteDAO(List<ConciliacaoDTO> list) {
        try {
            new ConciliacaoDAO().deleteAll(list);
        } catch (DeleteFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
