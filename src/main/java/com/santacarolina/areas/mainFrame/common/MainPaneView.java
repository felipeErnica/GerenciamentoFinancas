package com.santacarolina.areas.mainFrame.common;

import javax.swing.*;

public interface MainPaneView {
    JPanel getPane();
    JTable getTable();
    void formatColumns();
    MainPaneViewImpl getBaseView();
    JScrollPane getScrollPane();
}
