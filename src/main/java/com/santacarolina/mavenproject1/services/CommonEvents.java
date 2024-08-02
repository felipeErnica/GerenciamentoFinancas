package com.santacarolina.mavenproject1.services;

import javax.swing.*;
import java.awt.event.*;

public class CommonEvents {

    public static KeyListener nextComponentOnEnter(JComponent nextComponent){
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    nextComponent.requestFocus();
                }
            }
        };
    }

    public static FocusListener afterUpdateToUpperCase() {
        return new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                JTextField textField = (JTextField) e.getSource();
                textField.setText(textField.getText().toUpperCase());
            }
        };
    }

}
