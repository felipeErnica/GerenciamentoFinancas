package com.santacarolina.mavenproject1.view.overview.mainpanel;

import javax.swing.*;

public class OverviewMainPanel extends JTabbedPane {

    private FuturePaymentsPanel futurePaymentsPanel;
    private PastPaymentsPanel pastPaymentsPanel;
    private ProductsPanel productsPanel;
    private BankingPanel bankingPanel;

    public OverviewMainPanel(){
        initComponents();
    }

    private void initComponents(){

        futurePaymentsPanel = new FuturePaymentsPanel();
        pastPaymentsPanel = new PastPaymentsPanel();
        productsPanel = new ProductsPanel();
        bankingPanel = new BankingPanel();

        addTab("Duplicatas a Vencer",futurePaymentsPanel);
        addTab("Pagamentos e Recebimentos",pastPaymentsPanel);
        addTab("Produtos e Serviços",productsPanel);
        addTab("Informações Bancárias",bankingPanel);
    }

}
