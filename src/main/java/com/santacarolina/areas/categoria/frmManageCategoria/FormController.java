package com.santacarolina.areas.categoria.frmManageCategoria;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.santacarolina.areas.categoria.frmCategoria.CategoriaForm;
import com.santacarolina.dao.CategoriaDAO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ManageController;
import com.santacarolina.model.CategoriaContabil;
import com.santacarolina.ui.ManageControllerImpl;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;

public class FormController implements ManageController {

    private FormView view;
    private CategoriaTableModel tableModel;

    @SuppressWarnings("rawtypes")
    private RowSorter sorter;

    public FormController(FormView view, CategoriaTableModel tableModel) {
        this.view = view;
        this.tableModel = tableModel;
        ManageControllerImpl<CategoriaContabil> baseController = new ManageControllerImpl<>(tableModel, view, this);
        this.sorter = baseController.getSorter();
        init();
    }
    
    private void init() {
        view.getTable().setModel(tableModel.getBaseModel());
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        TableColumnModel columnModel = view.getTable().getColumnModel();
        columnModel.getColumn(0).setCellRenderer(cellRenderer);
        columnModel.getColumn(1).setCellRenderer(cellRenderer);
    }

    @Override
    public void showView() { ViewInvoker.showMaximizedView(view.getDialog()); }

    @Override
    public void table_onDoubleClick(MouseEvent e) {
        EventQueue.invokeLater(() -> {
            int row = view.getTable().rowAtPoint(e.getPoint());
            int modelRow = sorter.convertRowIndexToModel(row);
            CategoriaContabil cat = tableModel.getObject(modelRow);
            CategoriaForm.open(cat);
        });
    }

    @Override
    public void addButton_onClick() { 
        EventQueue.invokeLater(() -> {
            try {
                CategoriaForm.openNew();
                tableModel.requeryTable();
            } catch (FetchFailException e) {
                CustomErrorThrower.throwError(e);
            }
        }); 
    }

    @Override
    public void deleteButton_onClick() {
        try {
            int[] rows = view.getTable().getSelectedRows();
            int result = OptionDialog.showDeleteCascadeDialog(rows.length);
            if (result != JOptionPane.YES_OPTION) return;
            for (int i = rows.length; i >= 0; i--) {
                int row = sorter.convertRowIndexToModel(rows[i]);
                CategoriaContabil cat = tableModel.getObject(row);
                new CategoriaDAO().deleteById(cat.getId());
            }
            tableModel.requeryTable();
        } catch (FetchFailException | DeleteFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}

