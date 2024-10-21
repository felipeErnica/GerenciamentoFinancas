package com.santacarolina.areas.mainFrame.mainPage;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.areas.homePage.HomePage;
import com.santacarolina.areas.mainFrame.common.MainPaneView;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainView implements PropertyChangeListener {

    private JFrame frame;
    private Container pane;
    private JPanel centerPane;
    private SideMenu sideMenu;

    public MainView() { initComponents(); }

    private void initComponents() {
        frame = new JFrame("Gerenciamento de Finanças");
        frame.setIconImage(new FlatSVGIcon("icon/main_icon.svg").getImage());
        centerPane = new JPanel();
        sideMenu = new SideMenu();
        pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());
        pane.add(sideMenu.getPane(), BorderLayout.WEST);
        pane.add(centerPane, BorderLayout.CENTER);
    }

    public JFrame getFrame() { return frame; }

    public JButton getHomeButton() { return sideMenu.getHomeButton(); }
    public JButton getNfeButton() { return sideMenu.getNfeButton(); }
    public JButton getNewDocButton() { return sideMenu.getNewDocButton(); }
    public JButton getDocButton() { return sideMenu.getDocButton(); }
    public JButton getDupPagaButton() { return sideMenu.getDupPagaButton(); }
    public JButton getDupNaoPagaButton() { return sideMenu.getDupNaoPagaButton(); }
    public JButton getProdButton() { return sideMenu.getProdButton(); }

    public JButton getConciliacaoButton() { return sideMenu.getConciliacaoButton(); }
    public JButton getContasButton() { return sideMenu.getContasButton(); }

    public JButton getAddContatoButton() { return sideMenu.getAddContatoButton(); }
    public JButton getManageContatoButton() { return sideMenu.getManageContatoButton(); }
    public JButton getAddDadoButton() { return sideMenu.getAddDadoButton(); }
    public JButton getManageDadoButton() { return sideMenu.getManageDadoButton(); }
    public JButton getAddChavePixButton() { return sideMenu.getAddChavePix(); }
    public JButton getManageChavePixButton() { return sideMenu.getManageChavePix(); }

    public JButton getAddPastaButton() { return sideMenu.getAddPastaButton(); }
    public JButton getManagePastaButton() { return sideMenu.getManagePastaButton(); }

    public JButton getManageClassificacaoButton() { return sideMenu.getManageClassificacaoButton(); }
    public JButton getAddCategoriaButton() { return sideMenu.getAddCategoriaButton(); }
    public JButton getManageCategoriaButton() { return sideMenu.getManageCategoriaButton(); }
    public JButton getAddClassificacaoButton() { return sideMenu.getAddClassificacaoButton(); }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(MainFrameModel.CENTER_PANEL)) {
        pane.remove(centerPane);
            MainPaneView view = (MainPaneView) evt.getNewValue();
            centerPane = view.getPane();
            pane.add(centerPane, BorderLayout.CENTER);
            frame.revalidate();
            view.formatColumns();
        } else if (evt.getPropertyName().equals(MainFrameModel.HOME_PAGE)) {
            pane.remove(centerPane);
            centerPane = HomePage.getMainPanel();
            pane.add(centerPane, BorderLayout.CENTER);
            frame.revalidate();
        }
    }

}
