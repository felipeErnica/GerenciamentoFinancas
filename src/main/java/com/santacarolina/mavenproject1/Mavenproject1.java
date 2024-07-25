/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.santacarolina.mavenproject1;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.santacarolina.mavenproject1.view.overview.OverviewFrame;
import javax.swing.*;
import java.util.logging.Logger;

public class Mavenproject1 {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(OverviewFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        OverviewFrame overviewFrame = new OverviewFrame();
    }
}
