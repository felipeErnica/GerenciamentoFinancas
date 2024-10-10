package com.santacarolina.util;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import org.knowm.xchart.internal.ChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.awt.*;

public class MenuDecorator {

    private static final Color GRAPH_FOREGROUND = UIManager.getColor("Graph.foreground");
    private static final Color GRAPH_BACKGROUND = UIManager.getColor("Graph.background");
    private static final Color MENU_BACKGROUND = UIManager.getColor("SideBar.backgroundColor");
    private static final Color MENU_FOREGROUND = UIManager.getColor("SideBar.foregroundColor");

    public static void paintChart(Chart chart) {
        chart.getStyler()
                .setPlotBackgroundColor(GRAPH_BACKGROUND)
                .setPlotBorderVisible(false)
                .setAnnotationTextFontColor(GRAPH_FOREGROUND)
                .setToolTipBackgroundColor(GRAPH_FOREGROUND)
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
