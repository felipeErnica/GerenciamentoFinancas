package com.santacarolina.ui;

import com.santacarolina.interfaces.*;
import com.santacarolina.util.ViewInvoker;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.MouseEvent;

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
    private void deleteButton_onClick() { childController.deleteButton_onClick(); }

    @Override
    public void showView() { ViewInvoker.showMaximizedView(view.getDialog()); }

    public RowSorter getSorter() { return sorter; }

}
