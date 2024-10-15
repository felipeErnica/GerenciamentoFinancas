
package com.santacarolina;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.santacarolina.areas.mainFrame.mainPage.MainFrame;

import javax.swing.*;
import java.awt.*;

public class GerenciamentoFinancas {

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new GerenciamentoFinancas().runApp()); }

    private void runApp(){
        setStyle();
        new MainFrame();
    }

    public static void setStyle() {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("themes");
        FlatLightLaf.setup();
        UIManager.put("defaultFont",new Font(FlatRobotoFont.FAMILY_LIGHT, Font.PLAIN,13));
    }

}
