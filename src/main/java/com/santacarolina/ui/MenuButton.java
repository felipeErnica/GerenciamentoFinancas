package com.santacarolina.ui;

import javax.swing.*;
import java.awt.*;

public class MenuButton extends JButton {

    private Color mainColor;

    public MenuButton(String text, Color color) {
        super(text);
        mainColor = color;
        initButton();
    }

    private void initButton(){
        setBackground(mainColor);
        setFont(new Font("Segoe UI", Font.PLAIN, 12));
        setForeground(Color.WHITE);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setHorizontalTextPosition(SwingConstants.LEADING);
    }

}
