package com.santacarolina.areas.duplicatas.common;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import com.santacarolina.areas.mainFrame.common.MainPaneView;
import com.santacarolina.areas.mainFrame.common.MainPaneViewImpl;
import com.santacarolina.exceptions.FetchFailException;

public class DupView implements MainPaneView {

    private MainPaneViewImpl mainPaneView;
    private FilterView filterView;

    public DupView() throws FetchFailException { 
        this.mainPaneView = new MainPaneViewImpl(this); 
        filterView = new FilterView(mainPaneView.getFilterPanel());
    }

    public FilterView getFilterView() { return filterView; }

    @Override
    public JPanel getPane() { return mainPaneView.getPane(); }

    @Override
    public JTable getTable() { return mainPaneView.getTable(); }

    @Override
    public MainPaneViewImpl getBaseView() { return mainPaneView; }

    @Override
    public JScrollPane getScrollPane() { return mainPaneView.getScrollPane(); }

    @Override
    public void formatColumns() {
        int dupWidth = getScrollPane().getWidth()/100;
        TableColumnModel dupModel = getTable().getColumnModel();
        dupModel.getColumn(0).setPreferredWidth(dupWidth*10);
        dupModel.getColumn(1).setPreferredWidth(dupWidth*15);
        dupModel.getColumn(2).setPreferredWidth(dupWidth*15);
        dupModel.getColumn(3).setPreferredWidth(dupWidth*20);
        dupModel.getColumn(4).setPreferredWidth(dupWidth*30);
        dupModel.getColumn(5).setPreferredWidth(dupWidth*10);
    }

}
