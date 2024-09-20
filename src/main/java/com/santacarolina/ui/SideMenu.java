package com.santacarolina.ui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SideMenu extends JPanel {

    private final Color MENU_BACKGROUND = UIManager.getColor("SideBar.backgroundColor");

    private JPanel controlPanel = new JPanel();
    private JPanel southPanel = new JPanel();
    private JPanel northPanel = new JPanel();

    private Dimension componentSize;

    private Dimension size;
    private Map<String,JButton> buttonMap = new HashMap<>();

    public SideMenu(Dimension size) {
        this.size = size;
        setBackground(MENU_BACKGROUND);
        setPreferredSize(size);
        setLayout(new BorderLayout());
    }

    public SideMenu put(JButton button, String icon){

        int factor = buttonMap.size() + 3;
        int componentHeight = size.height/factor;

        componentSize = new Dimension(size.width,componentHeight);

        JButton newButton = SideMenu_Button.buildButton(button, icon);
        buttonMap.put(button.getText(),newButton);
        return this;
    }

    public SideMenu build(){

        northPanel.setPreferredSize(componentSize);
        northPanel.setBackground(MENU_BACKGROUND);
        southPanel.setPreferredSize(componentSize);
        southPanel.setBackground(MENU_BACKGROUND);
        controlPanel.setBackground(MENU_BACKGROUND);

        buttonMap.values().forEach(b -> {
            b.setPreferredSize(componentSize);
            controlPanel.add(b);
        });

        controlPanel.setLayout(new BoxLayout(controlPanel,BoxLayout.Y_AXIS));

        add(northPanel,BorderLayout.NORTH);
        add(controlPanel,BorderLayout.CENTER);
        add(southPanel,BorderLayout.SOUTH);

        return this;

    }

}
