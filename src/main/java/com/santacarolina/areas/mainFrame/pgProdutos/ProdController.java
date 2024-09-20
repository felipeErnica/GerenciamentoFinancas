package com.santacarolina.areas.mainFrame.pgProdutos;

import com.santacarolina.areas.mainFrame.common.MainPaneController;
import com.santacarolina.areas.mainFrame.common.MainPaneControllerImpl;
import com.santacarolina.ui.CurrencyCellRenderer;
import com.santacarolina.ui.DateCellRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

public class ProdController implements MainPaneController {

    private MainPaneControllerImpl controller;
    private ProdView view;
    private ProdTableModel model;

    public ProdController(ProdView view, ProdTableModel model) {
        this.controller = new MainPaneControllerImpl(view, model);
        this.view = view;
        this.model = model;
        initComponents();
    }

    private void initComponents() {
        TableColumnModel columnModel = view.getTable().getColumnModel();

        DateCellRenderer dateCellRenderer = new DateCellRenderer();
        dateCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        CurrencyCellRenderer currencyCellRenderer = new CurrencyCellRenderer();

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        columnModel.getColumn(0).setCellRenderer(dateCellRenderer);
        columnModel.getColumn(5).setCellRenderer(renderer);
        columnModel.getColumn(6).setCellRenderer(renderer);
        columnModel.getColumn(7).setCellRenderer(currencyCellRenderer);
        columnModel.getColumn(8).setCellRenderer(currencyCellRenderer);
    }

}
