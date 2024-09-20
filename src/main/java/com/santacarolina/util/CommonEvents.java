package com.santacarolina.util;

import com.formdev.flatlaf.FlatClientProperties;

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

    public static FocusListener transformDates(){
        return new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                JTextField textField = (JTextField) e.getSource();
                if (textField.getText().isEmpty()) return;
                try {
                    textField.setText(StringConversor.transformDate(textField.getText()).toString());
                    textField.putClientProperty(FlatClientProperties.OUTLINE,null);
                } catch (RuntimeException ex) {
                    textField.putClientProperty(FlatClientProperties.OUTLINE, FlatClientProperties.OUTLINE_ERROR);
                    textField.setToolTipText("Formato de Data inválido!");
                    ex.printStackTrace();
                }
            }
        };
    }


    public static FocusListener transformCurrency(){
        return new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                JTextField textField = (JTextField) e.getSource();
                if (textField.getText().isEmpty()) {return;}
                try {
                    double input = StringConversor.getDoubleFromLocalFormat(textField.getText());
                    textField.setText(StringConversor.getCurrency(input));
                    textField.putClientProperty(FlatClientProperties.OUTLINE,null);
                } catch (Exception ex) {
                    textField.putClientProperty(FlatClientProperties.OUTLINE, FlatClientProperties.OUTLINE_ERROR);
                    textField.setToolTipText("Formato inválido!");
                    ex.printStackTrace();
                }
            }
        };
    }


}
