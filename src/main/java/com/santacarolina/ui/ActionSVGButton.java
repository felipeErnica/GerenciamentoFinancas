package com.santacarolina.ui;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.FlatSVGIcon.ColorFilter;

import javax.swing.*;
import java.awt.*;

public class ActionSVGButton extends JButton {

    private final Color MENU_FOREGROUND = UIManager.getColor("SideBar.foregroundColor");

    public ActionSVGButton(String text, FlatSVGIcon icon) {
        super(text);
        setIcon(icon);
    }

    public ActionSVGButton(FlatSVGIcon flatSVGIcon) {
        setIcon(flatSVGIcon);
    }

    @Override
    public void setText(String text) { super.setText(text); }

    public void setIcon(FlatSVGIcon icon) {
        ColorFilter colorFilter = new ColorFilter().add(Color.decode("#666666"), MENU_FOREGROUND);
        icon.setColorFilter(colorFilter);
        super.setIcon(icon);
    }
}
