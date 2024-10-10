package com.santacarolina.areas.pgProdutos;

import com.santacarolina.areas.mainFrame.common.MainPaneView;
import com.santacarolina.areas.mainFrame.common.MainPaneViewImpl;
import javax.swing.*;
import javax.swing.table.TableColumnModel;

public class ProdView implements MainPaneView {

    private MainPaneViewImpl baseView;

    public ProdView() { this.baseView = new MainPaneViewImpl(this); }

    @Override
    public JPanel getPane() { return baseView.getPane(); }

    @Override
    public JTable getTable() { return baseView.getTable(); }

    @Override
    public MainPaneViewImpl getBaseView() { return baseView; }

    @Override
    public JScrollPane getScrollPane() { return baseView.getScrollPane(); }

    @Override
    public void formatColumns() {
        int tableWidth = (getScrollPane().getWidth())/20;
        TableColumnModel columnModel = getTable().getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(tableWidth*2);
        columnModel.getColumn(1).setPreferredWidth(tableWidth*2);
        columnModel.getColumn(2).setPreferredWidth(tableWidth*5);
        columnModel.getColumn(3).setPreferredWidth(tableWidth*4);
        columnModel.getColumn(4).setPreferredWidth(tableWidth*5);
        columnModel.getColumn(5).setPreferredWidth(tableWidth);
        columnModel.getColumn(6).setPreferredWidth(tableWidth*2);
        columnModel.getColumn(7).setPreferredWidth(tableWidth*2);
        columnModel.getColumn(8).setPreferredWidth(tableWidth + 50);
    }

}
