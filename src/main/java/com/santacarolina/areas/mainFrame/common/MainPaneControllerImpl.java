package com.santacarolina.areas.mainFrame.common;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.interfaces.OnPaneUpdate;
import com.santacarolina.ui.CustomTableModelImpl;
import com.santacarolina.util.OptionDialog;

public class MainPaneControllerImpl<T> {

    private MainPaneViewImpl view;
    private CustomTableModelImpl<T> model;
    private RowSorter<TableModel> sorter;
    private MainPaneController<T> childController;

    public MainPaneControllerImpl(MainPaneView view, CustomTableModel<T> model, MainPaneController<T> childController) {
        this.view = view.getBaseView();
        this.model = model.getBaseModel();
        this.sorter = new TableRowSorter<>(model.getBaseModel());
        this.childController = childController;
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
        if (rows.length == 0) return;
        if (OptionDialog.showDeleteCascadeDialog(rows.length) != JOptionPane.YES_OPTION) return;
        List<T> list = new ArrayList<>();
        for (int i = 0; i < rows.length; i++) {
            int modelRow = sorter.convertRowIndexToModel(rows[i]);
            model.removeRow(modelRow);
            list.add(model.getObject(modelRow));
        }
        childController.deleteBatch(list);
    }

}
