package com.santacarolina.areas.mainFrame.mainPage;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.ui.SubMenuImpl;
import com.santacarolina.util.AppIcon;
import com.santacarolina.util.MenuDecorator;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class SideMenu {

    private JPanel mainPane;
    private JPanel pane;
    private JScrollPane scrollPane;

    private SubMenuImpl docMenu;
    private JButton homeButton;
    private JButton nfeButton;
    private JButton newDocButton;
    private JButton docButton;
    private JButton dupPagaButton;
    private JButton dupNaoPagaButton;
    private JButton prodButton;

    private SubMenuImpl infoBancoSubMenu;
    private JButton conciliacaoButton;
    private JButton contasButton;

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

    public SideMenu() { init(); }

    private void init() {
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

        infoBancoSubMenu = new SubMenuImpl(this, "Informações Bancárias");
        infoBancoSubMenu.setMainIcon(AppIcon.paintIcon(new FlatSVGIcon("icon/banco_menu_icon.svg")));
        conciliacaoButton = new JButton("Conciliar Extratos", AppIcon.paintIcon(new FlatSVGIcon("icon/conciliacao_icon.svg")));
        infoBancoSubMenu.addButton(conciliacaoButton);
        contasButton = new JButton("Mostrar Extratos", AppIcon.paintIcon(new FlatSVGIcon("icon/extrato_icon.svg")));
        infoBancoSubMenu.addButton(contasButton);

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

        pastasSubMenu = new SubMenuImpl(this, "Pastas Contábeis");
        pastasSubMenu.setMainIcon(AppIcon.paintIcon(new FlatSVGIcon("icon/pasta_menu_icon.svg")));
        addPastaButton = new JButton("Nova Pasta", AppIcon.paintIcon(new FlatSVGIcon("icon/pasta_add_icon.svg")));
        pastasSubMenu.addButton(addPastaButton);
        managePastaButton = new JButton("Gerenciar Pastas", AppIcon.paintIcon(new FlatSVGIcon("icon/pasta_icon.svg")));
        pastasSubMenu.addButton(managePastaButton);

        classificacaoSubMenu = new SubMenuImpl(this, "Classificações Contábeis");
        classificacaoSubMenu.setMainIcon(AppIcon.paintIcon(new FlatSVGIcon("icon/classificacao_menu_icon.svg")));

        pane = new JPanel(new MigLayout("insets 0, gap 0, flowy",
                "fill, grow",
                "fill"));
        pane.add(homeButton, "gaptop 150, growx");
        pane.add(docMenu.getPane());
        pane.add(infoBancoSubMenu.getPane());
        pane.add(contatosSubMenu.getPane());
        pane.add(pastasSubMenu.getPane());
        pane.add(classificacaoSubMenu.getPane());

        MenuDecorator.paintPanel(pane);
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(pane);
        mainPane = new JPanel(new MigLayout("insets 0","fill, grow","fill, grow"));
        mainPane.add(scrollPane);
    }

    public JPanel getPane() { return mainPane; }
    public JScrollPane getScrollPane() { return scrollPane; }

    public JButton getHomeButton() { return homeButton; }
    public JButton getNfeButton() { return nfeButton; }
    public JButton getNewDocButton() { return newDocButton; }
    public JButton getDocButton() { return docButton; }
    public JButton getDupPagaButton() { return dupPagaButton; }
    public JButton getDupNaoPagaButton() { return dupNaoPagaButton; }
    public JButton getProdButton() { return prodButton; }

    public JButton getConciliacaoButton() { return conciliacaoButton; }
    public JButton getContasButton() { return contasButton; }

    public JButton getAddContatoButton() { return addContatoButton; }
    public JButton getManageContatoButton() { return manageContatoButton; }
    public JButton getAddDadoButton() { return addDadoButton; }
    public JButton getManageDadoButton() { return manageDadoButton; }
    public JButton getAddChavePix() { return addChavePix; }
    public JButton getManageChavePix() { return manageChavePix; }

    public JButton getAddPastaButton() { return addPastaButton; }
    public JButton getManagePastaButton() { return managePastaButton; }

    public void revalidate() {
        mainPane.removeAll(); scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        scrollPane.setViewportView(pane);
        mainPane.add(scrollPane);
        mainPane.revalidate();
    }

}

