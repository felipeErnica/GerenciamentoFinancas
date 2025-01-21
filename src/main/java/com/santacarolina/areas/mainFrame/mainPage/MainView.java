package com.santacarolina.areas.mainFrame.mainPage;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.santacarolina.util.MenuDecorator;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.areas.homePage.HomePage;
import com.santacarolina.areas.mainFrame.common.MainPaneView;

public class MainView implements PropertyChangeListener {

    private JFrame frame;
    private Container pane;
    private JPanel centerPane;
    private SideMenu sideMenu;
    private JPanel controlPane;
    private JPanel contentPane;

    private JButton closeButton;
    private JButton minimizeButton;

    public MainView() { initComponents(); }

    private void initComponents() {
        frame = new JFrame("Gerenciamento de Finanças");
        frame.setIconImage(new FlatSVGIcon("icon/main_icon.svg").getImage());

        closeButton = new JButton(new FlatSVGIcon("icon/close_icon.svg"));
        MenuDecorator.paintChangeModeButton(closeButton);

        minimizeButton = new JButton(new FlatSVGIcon("icon/minimize_icon.svg"));
        MenuDecorator.paintChangeModeButton(minimizeButton);

        controlPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        controlPane.add(closeButton);
        controlPane.add(minimizeButton);

        contentPane = new JPanel();

        centerPane = new JPanel(new BorderLayout());
        centerPane.add(controlPane, BorderLayout.NORTH);
        centerPane.add(contentPane, BorderLayout.CENTER);

        sideMenu = new SideMenu();
        pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());
        pane.add(sideMenu.getPane(), BorderLayout.WEST);
        pane.add(centerPane, BorderLayout.CENTER);
    }

    public JFrame getFrame() { return frame; }
    public SideMenu getSideMenu() { return sideMenu; }
    public JPanel getCenterPane() { return centerPane; }

    public JButton getCloseButton() { return closeButton; }
    public JButton getMinimizeButton() { return minimizeButton; }

    public JButton getChangeMode() { return sideMenu.getChangeMode(); }
    
    public JButton getHomeButton() { return sideMenu.getHomeButton(); }
    
    public JButton getNfeButton() { return sideMenu.getNfeButton(); }
    public JButton getNewDocButton() { return sideMenu.getNewDocButton(); }
    public JButton getDocButton() { return sideMenu.getDocButton(); }
    public JButton getDupPagaButton() { return sideMenu.getDupPagaButton(); }
    public JButton getDupNaoPagaButton() { return sideMenu.getDupNaoPagaButton(); }
    public JButton getProdButton() { return sideMenu.getProdButton(); }

    public JButton getAddBanco() { return sideMenu.getAddBanco(); }
    public JButton getManageBancos() { return sideMenu.getManageBancos(); }
    public JButton getConciliacaoButton() { return sideMenu.getAddConciliacaoButton(); }
    public JButton getManageConciliacaoButton() { return sideMenu.getManageConciliacaoButton(); }
    public JButton getManageContaBancariaButton() { return sideMenu.getManageContaBancariaButton(); }
    public JButton getAddContaBancariaButton() { return sideMenu.getAddContaBancariaButton(); }
    public JButton getExtratosButton() { return sideMenu.getExtratosButton(); }

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
            centerPane.remove(contentPane);
            MainPaneView view = (MainPaneView) evt.getNewValue();
            contentPane = view.getPane();
            centerPane.add(contentPane, BorderLayout.CENTER);
            centerPane.revalidate();
            frame.revalidate();
            view.formatColumns();
        } else if (evt.getPropertyName().equals(MainFrameModel.HOME_PAGE)) {
            centerPane.remove(contentPane);
            contentPane = HomePage.getMainPanel();
            centerPane.add(contentPane, BorderLayout.CENTER);
            centerPane.revalidate();
            frame.revalidate();
        }
    }

}
