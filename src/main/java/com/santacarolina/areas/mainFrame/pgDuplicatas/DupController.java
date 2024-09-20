package com.santacarolina.areas.mainFrame.pgDuplicatas;

import com.santacarolina.areas.mainFrame.common.MainPaneControllerImpl;
import com.santacarolina.ui.CurrencyCellRenderer;
import com.santacarolina.ui.DateCellRenderer;

import javax.swing.*;
import javax.swing.table.*;

public class DupController  {

    private MainPaneControllerImpl controller;
    private DupView view;
    private DupTableModel model;
    private RowSorter<TableModel> sorter;

    public DupController(DupView view, DupTableModel model) {
        controller = new MainPaneControllerImpl(view, model);
        this.view = view;
        this.model = model;
        initComponents();
    }

    private void initComponents() {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        DateCellRenderer dateRenderer = new DateCellRenderer();
        dateRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        CurrencyCellRenderer currencyCellRenderer = new CurrencyCellRenderer();

        TableColumnModel columnModel = view.getTable().getColumnModel();
        columnModel.getColumn(0).setCellRenderer(renderer);
        columnModel.getColumn(1).setCellRenderer(dateRenderer);
        columnModel.getColumn(2).setCellRenderer(renderer);
        columnModel.getColumn(3).setCellRenderer(renderer);
        columnModel.getColumn(5).setCellRenderer(currencyCellRenderer);
    }

}
