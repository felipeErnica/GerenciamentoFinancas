package com.santacarolina.ui;

import com.santacarolina.interfaces.*;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class ManageControllerImpl<T> implements Controller {

    private CustomTableModel<T> model;
    private ManageView view;
    private RowSorter<? extends TableModel> sorter;
    private ManageController childController;

    public ManageControllerImpl(CustomTableModel<T> model, ManageView view, ManageController childController) {
        this.model = model;
        this.view = view;
        this.childController = childController;
        initComponents();
    }

    private void initComponents() {
        view.getTable().setModel(model.getBaseModel());
        sorter = new TableRowSorter<>(model.getBaseModel());
        view.getTable().setRowSorter(sorter);
        view.getDialog().addComponentListener((OnResize) e -> dialog_onResize());
        view.getDeleteButton().addActionListener(e -> deleteButton_onClick());
        view.getAddButton().addActionListener(e -> addButton_onClick());
        view.getTable().addMouseListener((DoubleClickListener) this::table_onDoubleClick);
    }

    private void dialog_onResize() { view.formatColumns(); }
    private void table_onDoubleClick(MouseEvent e) { childController.table_onDoubleClick(e); }
    private void addButton_onClick() { childController.addButton_onClick(); }
    
    @SuppressWarnings("unchecked")
    private void deleteButton_onClick() { 
        int[] rows = view.getTable().getSelectedRows();
        if (rows.length == 0) return;
        if (OptionDialog.showDeleteCascadeDialog(rows.length) != JOptionPane.YES_OPTION) return; 
        List<T> deleteList = new ArrayList<>();
        for (int i = rows.length - 1; i >= 0; i--) {
            int modelRow = sorter.convertRowIndexToModel(rows[i]);
            T t = model.getObject(modelRow);
            deleteList.add(t);
            model.getBaseModel().removeRow(modelRow);
        }
        childController.callDeleteDAO(deleteList); 
    }

    @Override
    public void showView() { ViewInvoker.showMaximizedView(view.getDialog()); }

    public RowSorter getSorter() { return sorter; }

}
