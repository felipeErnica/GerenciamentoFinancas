package com.santacarolina;

import java.awt.Font;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.santacarolina.areas.login.LoginForm;

public class GerenciamentoFinancas {

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new GerenciamentoFinancas().runApp()); }

    private void runApp() {
        setStyle();
        LoginForm.open();
    }

    public static void setStyle() {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("themes");
        FlatLightLaf.setup();
        UIManager.put("defaultFont",new Font(FlatRobotoFont.FAMILY_LIGHT, Font.PLAIN,13));
    }

}
