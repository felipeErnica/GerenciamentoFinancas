/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.santacarolina.mavenproject1;

import com.formdev.flatlaf.FlatLightLaf;
import com.santacarolina.mavenproject1.view.dialogs.AddUserFolderDialog;
import com.santacarolina.mavenproject1.view.dialogs.ImportNFEDialog;
import com.santacarolina.mavenproject1.view.overview.OverviewFrame;

public class Mavenproject1 {

    public static void main(String[] args) {
        FlatLightLaf.setup();
        OverviewFrame overviewFrame = new OverviewFrame();
        ImportNFEDialog importNFEDialog = new ImportNFEDialog();
    }
}
