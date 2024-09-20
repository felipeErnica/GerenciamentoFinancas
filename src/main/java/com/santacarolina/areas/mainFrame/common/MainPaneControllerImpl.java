package com.santacarolina.areas.mainFrame.common;

import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.interfaces.OnPaneUpdate;
import com.santacarolina.ui.CustomTableModelImpl;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class MainPaneControllerImpl {

    private MainPaneViewImpl view;
    private CustomTableModelImpl<?> model;
    private RowSorter<TableModel> sorter;

    public MainPaneControllerImpl(MainPaneView view, CustomTableModel<?> model) {
        this.view = view.getBaseView();
        this.model = model.getBaseModel();
        this.sorter = new TableRowSorter<>(model.getBaseModel());
        initComponents();
    }

    private void initComponents() {
        view.getTable().setModel(model);
        view.getTablePanel().setDelAction(e -> deleteMenu_onClick());
        view.getPane().addContainerListener((OnPaneUpdate) e -> view.formatColumns());
    }

    private void deleteMenu_onClick() {
        int[] rows = view.getTable().getSelectedRows();
        model.removeRows(rows);
    }

}
