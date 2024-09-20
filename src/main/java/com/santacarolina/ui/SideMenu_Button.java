package com.santacarolina.ui;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.FlatSVGIcon.*;

import javax.swing.*;
import java.awt.*;


public class SideMenu_Button extends JButton {

    private static final Dimension MINIMUM_SIZE =new Dimension(100,30);
    private static final Dimension MAXIMUM_SIZE =new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE);

    private static final Color MENU_BACKGROUND = UIManager.getColor("SideBar.backgroundColor");
    private static final Color MENU_FOREGROUND = UIManager.getColor("SideBar.foregroundColor");

    public static JButton buildButton (JButton button, String icon) {

        FlatSVGIcon svgIcon = new FlatSVGIcon(icon);
        ColorFilter colorFilter = new ColorFilter().add(Color.decode("#666666"),MENU_FOREGROUND);

        button.setIcon(svgIcon);
        button.putClientProperty(FlatClientProperties.BUTTON_TYPE,FlatClientProperties.BUTTON_TYPE_SQUARE);
        button.setMinimumSize(MINIMUM_SIZE);
        button.setMaximumSize(MAXIMUM_SIZE);
        button.setBackground(MENU_BACKGROUND);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEADING);
        button.setForeground(MENU_FOREGROUND);

        return button;
    }

}
