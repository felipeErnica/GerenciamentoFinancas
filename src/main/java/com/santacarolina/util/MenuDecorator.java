package com.santacarolina.util;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;

public class MenuDecorator {

    private static final Color MENU_BACKGROUND = UIManager.getColor("SideBar.backgroundColor");
    private static final Color MENU_FOREGROUND = UIManager.getColor("SideBar.foregroundColor");

    public static void paintPanel (JPanel panel) { panel.setBackground(MENU_BACKGROUND); }

    public static void paintButton (JButton button, FlatSVGIcon icon) {
        FlatSVGIcon svgIcon = new FlatSVGIcon(icon);
        FlatSVGIcon.ColorFilter colorFilter = new FlatSVGIcon.ColorFilter().add(Color.decode("#666666"), MENU_FOREGROUND);
        button.setIcon(svgIcon);
        paintButton(button);
    }

    public static void paintButton (JButton button) {
        button.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_SQUARE);
        button.setBackground(MENU_BACKGROUND);
        button.setForeground(MENU_FOREGROUND);
        button.setHorizontalAlignment(SwingConstants.LEADING);
        button.setMinimumSize(new Dimension(0,80));
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void paintTree (JTree tree) {
        tree.setBackground(MENU_BACKGROUND);
        tree.setForeground(MENU_FOREGROUND);
        tree.setRowHeight(80);
        tree.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        TreePath rootPath = tree.getPathForRow(0);
        tree.collapsePath(rootPath);
    }

}
