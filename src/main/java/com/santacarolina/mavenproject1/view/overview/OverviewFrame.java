package com.santacarolina.mavenproject1.view.overview;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.santacarolina.mavenproject1.services.ImageIconConfig;
import com.santacarolina.mavenproject1.view.overview.mainpanel.OverviewMainPanel;

import javax.swing.*;
import java.awt.*;

public class OverviewFrame extends JFrame {

    private OverviewMainPanel mainPanel;
    private OverviewMenuBar menuBar;

    public OverviewFrame() throws HeadlessException {
        initComponents();
    }

    private void initComponents(){

        ImageIconConfig iconConfig = new ImageIconConfig("src/main/resources/icon/Money Transfer.png");

        mainPanel = new OverviewMainPanel();
        menuBar = new OverviewMenuBar();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280,720);
        setTitle("Gerencimento de Finanças");
        setIconImage(iconConfig.getBufferedImage());
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
        add(menuBar,BorderLayout.NORTH);
        setVisible(true);

    }

}
