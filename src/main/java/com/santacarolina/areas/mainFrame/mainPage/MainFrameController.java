package com.santacarolina.areas.mainFrame.mainPage;

import com.santacarolina.areas.bancario.conciliacao.frmConciliacao.ConciliacaoForm;
import com.santacarolina.areas.mainFrame.pgBanco.ExtratoPane;
import com.santacarolina.areas.mainFrame.pgDuplicatas.DupPane;
import com.santacarolina.areas.mainFrame.pgProdutos.ProdPane;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.util.ViewInvoker;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class MainFrameController implements Controller {

    private MainFrameModel model;
    private MainView view;

    public MainFrameController(MainFrameModel model, MainView view) {
        this.model = model;
        this.view = view;
        initComponents();
    }

    private void initComponents() {
        view.getDupButton().addActionListener(e -> dupButton_onClick());
        view.getProdButton().addActionListener(e -> prodButton_onClick());
        view.getBankTree().addFocusListener((AfterUpdateListener) e -> bankTree_onLostFocus());
        view.getBankTree().addTreeSelectionListener(e -> bankTree_onClick());

    }

    private void bankTree_onClick() {
        EventQueue.invokeLater(() -> {
            DefaultMutableTreeNode lastNode = (DefaultMutableTreeNode) view.getBankTree().getLastSelectedPathComponent();
            if (lastNode == null) return;
            if (lastNode.getUserObject().equals(view.getContasButton().getUserObject())) contasButton_onClick();
            else if (lastNode.getUserObject().equals(view.getConciliacaoButton().getUserObject())) conciliacaoButton_onClick();
        });
    }

    private void contasButton_onClick() { model.setCenterPanel(new ExtratoPane().getView()); }
    private void conciliacaoButton_onClick() { new ConciliacaoForm(); }
    private void bankTree_onLostFocus() { view.getBankTree().clearSelection(); }
    private void prodButton_onClick() { EventQueue.invokeLater(() -> model.setCenterPanel(new ProdPane().getView())); }
    private void dupButton_onClick() { EventQueue.invokeLater(() -> model.setCenterPanel(new DupPane().getView())); }

    @Override
    public void showView() {
        ViewInvoker.showFrame(view.getFrame());
    }

}
