package com.santacarolina.areas.mainFrame.mainPage;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.ui.SubMenuImpl;
import com.santacarolina.util.AppIcon;
import com.santacarolina.util.MenuDecorator;

import net.miginfocom.swing.MigLayout;

public class SideMenu {

    private JPanel mainPane;
    private JPanel pane;
    private JPanel northPanel;
    private JScrollPane scrollPane;
    private List<SubMenuImpl> listSubMenus;

    private JButton changeMode;

    private SubMenuImpl docMenu;
    private JButton homeButton;
    private JButton nfeButton;
    private JButton newDocButton;
    private JButton docButton;
    private JButton dupPagaButton;
    private JButton dupNaoPagaButton;
    private JButton prodButton;
    private JButton relatorioButton;

    private SubMenuImpl infoBancoSubMenu;
    private JButton addBanco;
    private JButton manageBancos;
    private JButton addConciliacaoButton;
    private JButton manageConciliacaoButton;
    private JButton extratosButton;
    private JButton addContaBancariaButton;
    private JButton manageContaBancariaButton;

    private SubMenuImpl contatosSubMenu;
    private JButton addContatoButton;
    private JButton manageContatoButton;
    private JButton addDadoButton;
    private JButton manageDadoButton;
    private JButton addChavePix;
    private JButton manageChavePix;

    private SubMenuImpl pastasSubMenu;
    private JButton addPastaButton;
    private JButton managePastaButton;

    private SubMenuImpl classificacaoSubMenu;
    private JButton addCategoriaButton;
    private JButton manageCategoriaButton;
    private JButton manageClassificacaoButton;
    private JButton addClassificacaoButton;

    public SideMenu() { init(); }

    private void init() {
        listSubMenus = new ArrayList<>();

        homeButton = new JButton("Início", AppIcon.paintIcon(new FlatSVGIcon("icon/inicio_icon.svg")));
        MenuDecorator.paintButton(homeButton);

        docMenu = new SubMenuImpl(this, "Documentos");
        docMenu.setMainIcon(AppIcon.paintIcon(new FlatSVGIcon("icon/doc_menu_icon.svg")));
        nfeButton = new JButton("Importar NFe", AppIcon.paintIcon(new FlatSVGIcon("icon/nfe_icon.svg")));
        docMenu.addButton(nfeButton);
        newDocButton = new JButton("Novo Documento", AppIcon.paintIcon(new FlatSVGIcon("icon/new_doc_icon.svg")));
        docMenu.addButton(newDocButton);
        docButton = new JButton("Documentos Fiscais", AppIcon.paintIcon(new FlatSVGIcon("icon/documento_icon.svg")));
        docMenu.addButton(docButton);
        dupNaoPagaButton = new JButton("Duplicatas a Vencer", AppIcon.paintIcon(new FlatSVGIcon("icon/duplicata_icon.svg")));
        docMenu.addButton(dupNaoPagaButton);
        dupPagaButton = new JButton("Pagamentos e Recebimentos", AppIcon.paintIcon(new FlatSVGIcon("icon/dup_paga_icon.svg")));
        docMenu.addButton(dupPagaButton);
        prodButton = new JButton("Produtos e Serviços", AppIcon.paintIcon(new FlatSVGIcon("icon/produtos_icon.svg")));
        docMenu.addButton(prodButton);
        relatorioButton = new JButton("Gerar Relatório", AppIcon.paintIcon(new FlatSVGIcon("icon/categoria_icon.svg")));
        docMenu.addButton(relatorioButton);
        listSubMenus.add(docMenu);

        infoBancoSubMenu = new SubMenuImpl(this, "Informações Bancárias");
        infoBancoSubMenu.setMainIcon(AppIcon.paintIcon(new FlatSVGIcon("icon/banco_menu_icon.svg")));
        addBanco = new JButton("Novo Banco", AppIcon.paintIcon("icon/bank_add_icon.svg"));
        infoBancoSubMenu.addButton(addBanco);
        manageBancos = new JButton("Gerenciar Bancos", AppIcon.paintIcon("icon/bank_icon.svg"));
        infoBancoSubMenu.addButton(manageBancos);
        addConciliacaoButton = new JButton("Conciliar Extratos", AppIcon.paintIcon(new FlatSVGIcon("icon/conciliacao_icon.svg")));
        infoBancoSubMenu.addButton(addConciliacaoButton);
        manageConciliacaoButton = new JButton("Gerenciar Conciliações", AppIcon.paintIcon("icon/bank_reconciliation_icon.svg"));
        infoBancoSubMenu.addButton(manageConciliacaoButton);
        extratosButton = new JButton("Mostrar Extratos", AppIcon.paintIcon(new FlatSVGIcon("icon/extrato_icon.svg")));
        infoBancoSubMenu.addButton(extratosButton);
        addContaBancariaButton = new JButton("Nova Conta Bancária", AppIcon.paintIcon("icon/bank_account_add_icon.svg"));
        infoBancoSubMenu.addButton(addContaBancariaButton);
        manageContaBancariaButton = new JButton("Gerenciar Contas Bancárias", AppIcon.paintIcon("icon/bank_account_icon.svg"));
        infoBancoSubMenu.addButton(manageContaBancariaButton);
        listSubMenus.add(infoBancoSubMenu);

        contatosSubMenu = new SubMenuImpl(this, "Contatos");
        contatosSubMenu.setMainIcon(AppIcon.paintIcon(new FlatSVGIcon("icon/contato_menu_icon.svg")));
        addContatoButton = new JButton("Novo Contato", AppIcon.paintIcon( new FlatSVGIcon("icon/contato_add_icon.svg")));
        contatosSubMenu.addButton(addContatoButton);
        manageContatoButton = new JButton("Gerenciar Contatos", AppIcon.paintIcon(new FlatSVGIcon("icon/contato_icon.svg")));
        contatosSubMenu.addButton(manageContatoButton);
        addDadoButton = new JButton("Novo Dado Bancário", AppIcon.paintIcon(new FlatSVGIcon("icon/dado_bancario_add_icon.svg")));
        contatosSubMenu.addButton(addDadoButton);
        manageDadoButton = new JButton("Gerenciar Dados Bancários", AppIcon.paintIcon(new FlatSVGIcon("icon/dado_bancario_icon.svg")));
        contatosSubMenu.addButton(manageDadoButton);
        addChavePix = new JButton("Nova Chave Pix", AppIcon.paintIcon(new FlatSVGIcon("icon/pix_add_icon.svg")));
        contatosSubMenu.addButton(addChavePix);
        manageChavePix = new JButton("Gerenciar Chaves", AppIcon.paintIcon(new FlatSVGIcon("icon/pix_icon.svg")));
        contatosSubMenu.addButton(manageChavePix);
        listSubMenus.add(contatosSubMenu);

        pastasSubMenu = new SubMenuImpl(this, "Pastas Contábeis");
        pastasSubMenu.setMainIcon(AppIcon.paintIcon(new FlatSVGIcon("icon/pasta_menu_icon.svg")));
        addPastaButton = new JButton("Nova Pasta", AppIcon.paintIcon(new FlatSVGIcon("icon/pasta_add_icon.svg")));
        pastasSubMenu.addButton(addPastaButton);
        managePastaButton = new JButton("Gerenciar Pastas", AppIcon.paintIcon(new FlatSVGIcon("icon/pasta_icon.svg")));
        pastasSubMenu.addButton(managePastaButton);
        listSubMenus.add(pastasSubMenu);

        classificacaoSubMenu = new SubMenuImpl(this, "Classificações Contábeis");
        classificacaoSubMenu.setMainIcon(AppIcon.paintIcon(new FlatSVGIcon("icon/classificacao_menu_icon.svg")));
        addCategoriaButton = new JButton("Nova Categoria Contábil", AppIcon.paintIcon("icon/categoria_add_icon.svg"));
        classificacaoSubMenu.addButton(addCategoriaButton);
        manageCategoriaButton = new JButton("Gerenciar Categoria", AppIcon.paintIcon("icon/categoria_icon.svg"));
        classificacaoSubMenu.addButton(manageCategoriaButton);
        addClassificacaoButton = new JButton("Nova Classificação Contábil", AppIcon.paintIcon("icon/classificacao_add_icon.svg"));
        classificacaoSubMenu.addButton(addClassificacaoButton);
        manageClassificacaoButton = new JButton("Gerenciar Classificações Contábeis", AppIcon.paintIcon("icon/classificacao_icon.svg"));
        classificacaoSubMenu.addButton(manageClassificacaoButton);
        listSubMenus.add(classificacaoSubMenu); 

        changeMode = new JButton(AppIcon.paintIcon("icon/dark_icon.svg"));
        MenuDecorator.paintChangeModeButton(changeMode);

        northPanel = new JPanel(new MigLayout("insets 10"));
        northPanel.add(changeMode);
        MenuDecorator.paintPanel(northPanel);

        pane = new JPanel(new MigLayout("insets, gap 0, flowy",
                "fill, grow",
                "fill"));

        pane.add(northPanel);
        pane.add(homeButton, "growx, gaptop 150");
        pane.add(docMenu.getPane());
        pane.add(infoBancoSubMenu.getPane());
        pane.add(contatosSubMenu.getPane());
        pane.add(pastasSubMenu.getPane());
        pane.add(classificacaoSubMenu.getPane());

        MenuDecorator.paintPanel(pane);
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(pane);

        mainPane = new JPanel(new BorderLayout());
        mainPane.add(scrollPane, BorderLayout.CENTER);
        MenuDecorator.paintPanel(mainPane);
    }

    public void changeColors() {
        MenuDecorator.paintPanel(pane);
        MenuDecorator.paintPanel(northPanel);
        MenuDecorator.paintButton(homeButton);
        MenuDecorator.paintChangeModeButton(changeMode);
        listSubMenus.forEach(SubMenuImpl::changeTheme);
    }

    public JPanel getPane() { return mainPane; }
    public JScrollPane getScrollPane() { return scrollPane; }

    public JButton getChangeMode() { return changeMode; }

    public JButton getHomeButton() { return homeButton; }
    public JButton getNfeButton() { return nfeButton; }
    public JButton getNewDocButton() { return newDocButton; }
    public JButton getDocButton() { return docButton; }
    public JButton getDupPagaButton() { return dupPagaButton; }
    public JButton getDupNaoPagaButton() { return dupNaoPagaButton; }
    public JButton getProdButton() { return prodButton; }
    public JButton getRelatorioButton() { return relatorioButton; }

    public JButton getAddBanco() { return addBanco; }
    public JButton getManageBancos() { return manageBancos; }
    public JButton getAddConciliacaoButton() { return addConciliacaoButton; }
    public JButton getManageConciliacaoButton() { return manageConciliacaoButton; }
    public JButton getExtratosButton() { return extratosButton; }
    public JButton getManageContaBancariaButton() { return manageContaBancariaButton; }
    public JButton getAddContaBancariaButton() { return addContaBancariaButton; }

    public JButton getAddContatoButton() { return addContatoButton; }
    public JButton getManageContatoButton() { return manageContatoButton; }
    public JButton getAddDadoButton() { return addDadoButton; }
    public JButton getManageDadoButton() { return manageDadoButton; }
    public JButton getAddChavePix() { return addChavePix; }
    public JButton getManageChavePix() { return manageChavePix; }

    public JButton getAddPastaButton() { return addPastaButton; }
    public JButton getManagePastaButton() { return managePastaButton; }

    public JButton getAddCategoriaButton() { return addCategoriaButton; }
    public JButton getManageClassificacaoButton() { return manageClassificacaoButton; }
    public JButton getManageCategoriaButton() { return manageCategoriaButton; }
    public JButton getAddClassificacaoButton() { return addClassificacaoButton; }

    public void revalidate() {
        mainPane.removeAll(); 
        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        scrollPane.setViewportView(pane);
        mainPane.add(scrollPane, BorderLayout.CENTER);
        mainPane.revalidate();
    }

}

