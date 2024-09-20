package com.santacarolina.view;

import com.santacarolina.util.ImageIconConfig;

import javax.swing.*;
import java.awt.*;

public class OverviewFrame extends JFrame {

    public OverviewFrame() throws HeadlessException {
        initComponents();
    }

    private void initComponents(){

        ImageIconConfig iconConfig = new ImageIconConfig("src/main/resources/icon/Money Transfer.png");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280,720);
        setTitle("Gerencimento de Finanças");
        setIconImage(iconConfig.getBufferedImage());
        setLayout(new BorderLayout());

        JTabbedPane mainPanel = new OverviewMainPanel(this);
        JMenuBar menuBar = new OverviewMenuBar(this);

        add(mainPanel,BorderLayout.CENTER);
        add(menuBar,BorderLayout.NORTH);

    }

}
