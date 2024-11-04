package com.santacarolina.areas.mainFrame.common;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public interface MainPaneView {
    JPanel getPane();
    JTable getTable();
    void formatColumns();
    MainPaneViewImpl getBaseView();
    JScrollPane getScrollPane();
}
