package com.santacarolina.areas.mainFrame.common;

import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.interfaces.OnPaneUpdate;
import com.santacarolina.ui.CustomTableModelImpl;

import javax.swing.*;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
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

    public RowSorter<TableModel> getSorter() { return sorter; }

    private void initComponents() {
        view.getTable().setModel(model);
        view.getTable().setRowSorter(sorter);
        sorter.addRowSorterListener(e -> view.getTable().repaint());
        view.getTablePanel().setDelAction(e -> deleteMenu_onClick());
        view.getPane().addContainerListener((OnPaneUpdate) e -> view.formatColumns());
    }

    private void deleteMenu_onClick() {
        int[] rows = view.getTable().getSelectedRows();
        int[] modelRows = new int[rows.length];
        for (int i = 0; i < rows.length; i++) {
            int modelRow = sorter.convertRowIndexToModel(rows[i]);
            modelRows[i] = modelRow;
        }
        model.removeRows(modelRows);
    }

}
