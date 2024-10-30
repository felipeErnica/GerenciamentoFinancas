package com.santacarolina.util;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;

public class AppIcon {

    private static Color MENU_FOREGROUND = UIManager.getColor("SideBar.foregroundColor");

    public static void setColors() { MENU_FOREGROUND = UIManager.getColor("SideBar.foregroundColor"); }

    public static FlatSVGIcon paintIcon (FlatSVGIcon svgIcon) {
        FlatSVGIcon.ColorFilter colorFilter = new FlatSVGIcon.ColorFilter().add(Color.decode("#666666"), MENU_FOREGROUND);
        svgIcon.setColorFilter(colorFilter);
        return svgIcon;
    }

    public static FlatSVGIcon paintIcon (String iconPath) {
        FlatSVGIcon svgIcon = new FlatSVGIcon(iconPath);
        FlatSVGIcon.ColorFilter colorFilter = new FlatSVGIcon.ColorFilter().add(Color.decode("#666666"), MENU_FOREGROUND);
        svgIcon.setColorFilter(colorFilter);
        return svgIcon;
    }

}
