package com.santacarolina.util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.Styler.ToolTipType;

import com.formdev.flatlaf.FlatClientProperties;

public class MenuDecorator {

    private static Color GRAPH_FOREGROUND = UIManager.getColor("Graph.foreground");
    private static Color GRAPH_BACKGROUND = UIManager.getColor("Graph.background");
    private static Color MENU_BACKGROUND = UIManager.getColor("SideBar.backgroundColor");
    private static Color MENU_FOREGROUND = UIManager.getColor("SideBar.foregroundColor");

    public static void setColors() {
        GRAPH_FOREGROUND = UIManager.getColor("Graph.foreground");
        GRAPH_BACKGROUND = UIManager.getColor("Graph.background");
        MENU_BACKGROUND = UIManager.getColor("SideBar.backgroundColor");
        MENU_FOREGROUND = UIManager.getColor("SideBar.foregroundColor");
    }

    public static void paintChart(@SuppressWarnings("rawtypes") Chart chart) {
        chart.getStyler()
                .setPlotBackgroundColor(GRAPH_BACKGROUND)
                .setPlotBorderVisible(false)
                .setAnnotationTextFontColor(GRAPH_FOREGROUND)
                .setToolTipsEnabled(true)
                .setToolTipType(ToolTipType.xAndYLabels)
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

    public static void paintChangeModeButton(JButton button) {
        button.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        button.setBackground(MENU_BACKGROUND);
        button.setForeground(MENU_FOREGROUND);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setPreferredSize(new Dimension(40,40));
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void paintControlButton(JButton button) {
        button.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        button.setBackground(GRAPH_BACKGROUND);
        button.setForeground(MENU_FOREGROUND);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setPreferredSize(new Dimension(30,30));
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }


    public static void paintControlPanel(JPanel controlPane) {
        controlPane.setBackground(GRAPH_BACKGROUND);
        controlPane.setOpaque(true);
    }

}   
