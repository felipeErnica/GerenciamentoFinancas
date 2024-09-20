package com.santacarolina.view;

import com.santacarolina.ui.SideMenu;

import javax.swing.*;
import java.awt.*;

public class BankingPanel extends JPanel {

    private JFrame mainFrame;
    private JButton importOFXButton;
    private JButton importManualButton;

    //private ModelTablePane modelTablePane;

    public BankingPanel(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
    }

    private void initComponents(){

        importOFXButton = new JButton("Importar OFX");
        importManualButton = new JButton("Importar Manualmente");

        SideMenu bankSideBar = new SideMenu(new Dimension(300,700))
                .put(importOFXButton,"icon/import_ofx_icon.svg")
                .put(importManualButton,"icon/statment_icon.svg")
                .build();


        setLayout(new BorderLayout());
        add(bankSideBar, BorderLayout.WEST);
        //add(modelTablePane,BorderLayout.CENTER);

    }
}
