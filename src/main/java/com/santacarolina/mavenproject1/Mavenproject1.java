/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.santacarolina.mavenproject1;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.ui.FlatColorChooserUI;
import com.santacarolina.mavenproject1.view.dialogs.AddContactBankAccountDialog;
import com.santacarolina.mavenproject1.view.dialogs.ManageContactAccountDialog;
import com.santacarolina.mavenproject1.view.dialogs.ManageContactsDialog;
import com.santacarolina.mavenproject1.view.overview.OverviewFrame;
import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class Mavenproject1 {

    public static void main(String[] args) {
        FlatLightLaf.setup();
        OverviewFrame overviewFrame = new OverviewFrame();
    }
}
