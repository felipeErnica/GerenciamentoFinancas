package com.santacarolina.mavenproject1.services;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
                try {
                    textField.setText(transformDate(textField.getText()));
                    textField.putClientProperty(FlatClientProperties.OUTLINE, FlatClientProperties.TAB_BUTTON_SELECTED_BACKGROUND);
                } catch (RuntimeException ex) {
                    textField.putClientProperty(FlatClientProperties.OUTLINE,FlatClientProperties.OUTLINE_ERROR);
                    textField.requestFocus();
                    textField.setToolTipText("Formato de Data inválido!");
                }
            }
        };
    }

    private static String transformDate(String input) throws DateTimeParseException, NumberFormatException {

        int lenght = 0;

        int day = 0;
        int month = 0;
        int year = 0;

        for (int i = lenght; i < input.length(); i++) {
            Character c = input.charAt(i);
            if (c.equals('-') || c.equals('/')) {
                day = Integer.parseInt(input.substring(lenght, i));
                lenght = i + 1;
                break;
            }
        }

        for (int i = lenght; i < input.length(); i++) {
            Character c = input.charAt(i);
            if (c.equals('-') || c.equals('/')) {
                month = Integer.parseInt(input.substring(lenght, i));
                lenght = i + 1;
                break;
            } else if (i == input.length() - 1) {
                month = Integer.parseInt(input.substring(lenght, i + 1));
                lenght = input.length();
            }
        }

        if (lenght < input.length()) {
            year = Integer.parseInt(input.substring(lenght));
        }

        if (year == 0) {
            year = LocalDate.now().getYear();
        } else if (year < 1000) {
            year += 2000;
        }

        LocalDate date = LocalDateTime.of(year, month, day, 0, 0).toLocalDate();
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
