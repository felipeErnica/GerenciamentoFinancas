package com.santacarolina.areas.categoria.frmManageCategoria;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;

import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.santacarolina.areas.categoria.frmCategoria.CategoriaForm;
import com.santacarolina.interfaces.ManageController;
import com.santacarolina.model.CategoriaContabil;
import com.santacarolina.ui.ManageControllerImpl;
import com.santacarolina.util.ViewInvoker;

public class FormController implements ManageController {

    private FormView view;
    private CategoriaTableModel tableModel;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'table_onDoubleClick'");
    }

    @Override
    public void addButton_onClick() { EventQueue.invokeLater(CategoriaForm::openNew); }

    @Override
    public void deleteButton_onClick() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteButton_onClick'");
    }

}

