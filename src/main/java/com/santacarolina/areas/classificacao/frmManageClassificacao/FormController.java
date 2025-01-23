package com.santacarolina.areas.classificacao.frmManageClassificacao;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.santacarolina.areas.classificacao.frmClassificacao.ClassificacacaoForm;
import com.santacarolina.dao.ClassificacaoDAO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ManageController;
import com.santacarolina.model.ClassificacaoContabil;
import com.santacarolina.ui.ManageControllerImpl;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.ViewInvoker;

/**
 * FormController
 */
@SuppressWarnings("rawtypes")
public class FormController implements ManageController<ClassificacaoContabil> {

    private FormView view;
    private ClassificacaoTableModel tableModel;
    private RowSorter sorter;

    public FormController(FormView view, ClassificacaoTableModel tableModel) {
        this.view = view;
        this.tableModel = tableModel;
        ManageControllerImpl<ClassificacaoContabil> baseController = new ManageControllerImpl<>(tableModel, view, this);
        sorter = baseController.getSorter();
        new FilterController(view.getFilterView(), tableModel.getFilterModel());
        init();
    }

    private void init() {
        view.getTable().setModel(tableModel.getBaseModel());
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        view.getTable().getColumnModel().getColumn(2).setCellRenderer(renderer);
    }

    @Override
    public void showView() {
        ViewInvoker.showMaximizedView(view.getDialog());
    }

    @Override
    public void table_onDoubleClick(MouseEvent e) {
        EventQueue.invokeLater(() -> {
            try {
                int rowIndex = view.getTable().rowAtPoint(e.getPoint());
                int modelRow = sorter.convertRowIndexToModel(rowIndex);
                ClassificacaoContabil classificacao = tableModel.getObject(modelRow);
                ClassificacacaoForm.open(classificacao);
                tableModel.requeryTable();
            } catch (FetchFailException ex) {
                CustomErrorThrower.throwError(ex);
            }
        });
    }

    @Override
    public void addButton_onClick() {
        EventQueue.invokeLater(() -> {
            try {
                ClassificacacaoForm.openNew();
                tableModel.requeryTable();
            } catch (FetchFailException e) {
                CustomErrorThrower.throwError(e);
            }
        });
    }

    @Override
    public void callDeleteDAO(List<ClassificacaoContabil> list) {
        try {
            new ClassificacaoDAO().deleteAll(list);
        } catch (DeleteFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
