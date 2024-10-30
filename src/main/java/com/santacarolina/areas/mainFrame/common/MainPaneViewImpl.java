package com.santacarolina.areas.mainFrame.common;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.santacarolina.areas.duplicatas.common.DupTableModel;
import com.santacarolina.ui.TablePanel;

public class MainPaneViewImpl implements PropertyChangeListener {

    private MainPaneView childView;
    private TablePanel tablePanel;
    private JScrollPane scrollPane;
    private JPanel container;
    private JTable table;

    public MainPaneViewImpl(MainPaneView childView) {
        this.childView = childView;
        initComponents();
    }

    private void initComponents() {
        this.tablePanel = new TablePanel();
        table = tablePanel.getTable();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollPane = tablePanel.getTableScrollPane();
        container = new JPanel(new BorderLayout());
        container.add(scrollPane, BorderLayout.CENTER);
    }

    public JPanel getPane() { return container; }
    public JTable getTable() { return table; }
    public JScrollPane getScrollPane() { return scrollPane; }
    public TablePanel getTablePanel() { return tablePanel; }
    public void formatColumns() { childView.formatColumns(); }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(DupTableModel.TABLE)) {
            container.remove(scrollPane);
            container.add(scrollPane, BorderLayout.CENTER);
            container.revalidate();
            container.repaint();
        }
    }


}
