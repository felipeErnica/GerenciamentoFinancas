package com.santacarolina.areas.mainFrame.mainPage;

import com.santacarolina.areas.mainFrame.common.MainPaneView;
import com.santacarolina.util.MenuDecorator;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainView implements PropertyChangeListener {

    private JFrame frame;
    private Container pane;
    private JPanel sideMenu;
    private JPanel centerPane;
    private JButton dupButton;
    private JButton prodButton;
    private JTree bankTree;
    private DefaultMutableTreeNode contasButton;
    private DefaultMutableTreeNode conciliacaoButton;

    public MainView() {
        initComponents();
    }

    private void initComponents() {
        frame = new JFrame("Gerenciamento de Finanças");
        centerPane = new JPanel();

        pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());

        dupButton = new JButton("Duplicatas");
        MenuDecorator.paintButton(dupButton);

        prodButton = new JButton("Produtos e Serviços");
        MenuDecorator.paintButton(prodButton);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Informações Bancárias");

        conciliacaoButton = new DefaultMutableTreeNode("Conciliação de Extratos");
        contasButton = new DefaultMutableTreeNode("Ver Contas Bancárias");

        root.add(conciliacaoButton);
        root.add(contasButton);

        bankTree = new JTree(root);
        bankTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        bankTree.setShowsRootHandles(true);
        MenuDecorator.paintTree(bankTree);

        sideMenu = new JPanel(new MigLayout("insets 0, flowy",
                "[grow, fill]",
                "[150][][][]"));
        MenuDecorator.paintPanel(sideMenu);

        sideMenu.add(dupButton, "skip");
        sideMenu.add(prodButton);
        sideMenu.add(bankTree);

        pane.add(sideMenu, BorderLayout.WEST);
        pane.add(centerPane, BorderLayout.CENTER);
    }

    public JFrame getFrame() { return frame; }
    public JButton getDupButton() { return dupButton; }
    public JButton getProdButton() { return prodButton; }
    public JTree getBankTree() { return bankTree; }
    public DefaultMutableTreeNode getContasButton() { return contasButton; }
    public DefaultMutableTreeNode getConciliacaoButton() { return conciliacaoButton; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(MainFrameModel.CENTER_PANEL)) {
            pane.remove(centerPane);
            MainPaneView view = (MainPaneView) evt.getNewValue();
            centerPane = view.getPane();
            pane.add(centerPane, BorderLayout.CENTER);
            frame.revalidate();
            view.formatColumns();
        }
    }

}
