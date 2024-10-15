package com.santacarolina.areas.documentos.pgDocumentos;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import com.santacarolina.areas.mainFrame.common.MainPaneView;
import com.santacarolina.areas.mainFrame.common.MainPaneViewImpl;

public class PageView implements MainPaneView {

    private MainPaneViewImpl mainView;

    public PageView() { mainView = new MainPaneViewImpl(this); }

    @Override
    public MainPaneViewImpl getBaseView() { return mainView; }

    @Override
    public JScrollPane getScrollPane() { return mainView.getScrollPane(); }

    @Override
    public JPanel getPane() { return mainView.getPane(); }

    @Override
    public JTable getTable() { return mainView.getTable(); }

    @Override
    public void formatColumns() {
        int width = getScrollPane().getWidth()/100;
        TableColumnModel model = getTable().getColumnModel();
        model.getColumn(0).setPreferredWidth(width*5);
        model.getColumn(1).setPreferredWidth(width*15);
        model.getColumn(2).setPreferredWidth(width*15);
        model.getColumn(3).setPreferredWidth(width*15);
        model.getColumn(4).setPreferredWidth(width*10);
        model.getColumn(5).setPreferredWidth(width*40);
        model.getColumn(6).setPreferredWidth(width*10);
    }

}
