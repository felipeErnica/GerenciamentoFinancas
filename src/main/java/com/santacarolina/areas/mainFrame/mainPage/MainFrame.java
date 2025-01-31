package com.santacarolina.areas.mainFrame.mainPage;

public class MainFrame {

    public static void open() {
        MainView view = new MainView();
        MainFrameModel model = new MainFrameModel();
        MainFrameController controller = new MainFrameController(model, view);
        model.addPropertyChangeListener(view);
        controller.showView();
    }
}
