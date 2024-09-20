package com.santacarolina.areas.mainFrame.common;

import com.santacarolina.areas.mainFrame.pgDuplicatas.DupTableModel;
import com.santacarolina.ui.TablePanel;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
        TableFilterHeader header = new TableFilterHeader(table, AutoChoices.ENABLED);
        header.setPosition(TableFilterHeader.Position.TOP);
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
