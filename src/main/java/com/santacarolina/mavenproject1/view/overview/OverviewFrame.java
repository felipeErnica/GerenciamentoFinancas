package com.santacarolina.mavenproject1.view.overview;

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
        ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource("icon/IconePrincipal.png"));

        mainPanel = new OverviewMainPanel();
        menuBar = new OverviewMenuBar();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280,720);
        setTitle("Gerencimento de Finanças");
        setIconImage(imageIcon.getImage());
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
        add(menuBar,BorderLayout.NORTH);
        setVisible(true);

    }

}
