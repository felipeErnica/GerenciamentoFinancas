package com.santacarolina.view;

import javax.swing.*;

public class OverviewMainPanel extends JTabbedPane {

    private JFrame mainFrame;

    private DuplicatasAbertasPanel duplicatasAbertasPanel;
    private DuplicatasPagasPanel duplicatasPagasPanel;
    private ProductsPanel productsPanel;
    private BankingPanel bankingPanel;

    public OverviewMainPanel(JFrame mainFrame){
        this.mainFrame = mainFrame;
        initComponents();
    }

    private void initComponents(){
        duplicatasAbertasPanel = new DuplicatasAbertasPanel();
        duplicatasPagasPanel = new DuplicatasPagasPanel();
        productsPanel = new ProductsPanel();
        bankingPanel = new BankingPanel(mainFrame);

        addTab("Duplicatas a Vencer", duplicatasAbertasPanel);
        addTab("Pagamentos e Recebimentos", duplicatasPagasPanel);
        addTab("Produtos e Serviços",productsPanel);
        addTab("Informações Bancárias",bankingPanel);
    }

}
