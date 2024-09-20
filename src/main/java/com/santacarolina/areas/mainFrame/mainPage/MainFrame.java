package com.santacarolina.areas.mainFrame.mainPage;

import com.formdev.flatlaf.FlatDarkLaf;

public class MainFrame {

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        new MainFrame();
    }

    public MainFrame() {
        MainView view = new MainView();
        MainFrameModel model = new MainFrameModel();
        MainFrameController controller = new MainFrameController(model, view);
        model.addPropertyChangeListener(view);
        controller.showView();
    }
}
