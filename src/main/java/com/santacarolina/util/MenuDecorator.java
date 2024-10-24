package com.santacarolina.util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.AbstractButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.knowm.xchart.internal.chartpart.Chart;

import com.formdev.flatlaf.FlatClientProperties;

public class MenuDecorator {

    private static final Color GRAPH_FOREGROUND = UIManager.getColor("Graph.foreground");
    private static final Color GRAPH_BACKGROUND = UIManager.getColor("Graph.background");
    private static final Color MENU_BACKGROUND = UIManager.getColor("SideBar.backgroundColor");
    private static final Color MENU_FOREGROUND = UIManager.getColor("SideBar.foregroundColor");

    public static void paintChart(@SuppressWarnings("rawtypes") Chart chart) {
        chart.getStyler()
                .setPlotBackgroundColor(GRAPH_BACKGROUND)
                .setPlotBorderVisible(false)
                .setAnnotationTextFontColor(GRAPH_FOREGROUND)
                .setToolTipsEnabled(true)
                .setToolTipHighlightColor(GRAPH_FOREGROUND)
                .setToolTipBackgroundColor(GRAPH_BACKGROUND)
                .setLegendBackgroundColor(GRAPH_BACKGROUND)
                .setLegendBorderColor(GRAPH_BACKGROUND)
                .setXAxisTitleColor(GRAPH_FOREGROUND)
                .setYAxisTitleColor(GRAPH_FOREGROUND)
                .setChartTitleBoxBackgroundColor(GRAPH_BACKGROUND)
                .setChartTitleBoxVisible(false)
                .setChartFontColor(GRAPH_FOREGROUND)
                .setChartBackgroundColor(GRAPH_BACKGROUND);
    }

    public static void paintPanel (JPanel panel) {
        panel.setBackground(MENU_BACKGROUND);
        panel.setOpaque(true);
    }

    public static void paintButton (AbstractButton button) {
        button.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_SQUARE);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(10);
        button.setBackground(MENU_BACKGROUND);
        button.setForeground(MENU_FOREGROUND);
        button.setHorizontalAlignment(SwingConstants.LEADING);
        button.setMinimumSize(new Dimension(0,80));
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

}
