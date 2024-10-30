package com.santacarolina.areas.mainFrame.mainPage;

import java.awt.EventQueue;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.santacarolina.areas.bancario.banco.frmBanco.BancoForm;
import com.santacarolina.areas.bancario.banco.frmManageBanco.ManageBancoForm;
import com.santacarolina.areas.bancario.conciliacao.frmConciliacao.ConciliacaoForm;
import com.santacarolina.areas.bancario.conciliacao.frmManageConciliados.ManageConciliadosForm;
import com.santacarolina.areas.bancario.contaBancaria.frmContaBancaria.ContaForm;
import com.santacarolina.areas.bancario.contaBancaria.frmManageContaBancaria.ManageContaForm;
import com.santacarolina.areas.bancario.dadoBancario.frmDado.DadoForm;
import com.santacarolina.areas.bancario.dadoBancario.frmManageDado.ManageDadoForm;
import com.santacarolina.areas.bancario.extrato.pgExtrato.ExtratoPane;
import com.santacarolina.areas.bancario.pix.frmManagePix.ManagePixForm;
import com.santacarolina.areas.bancario.pix.frmPix.PixForm;
import com.santacarolina.areas.categoria.frmCategoria.CategoriaForm;
import com.santacarolina.areas.categoria.frmManageCategoria.ManageCategoriaForm;
import com.santacarolina.areas.classificacao.frmClassificacao.ClassificacacaoForm;
import com.santacarolina.areas.classificacao.frmManageClassificacao.ManageClassificacaoForm;
import com.santacarolina.areas.contato.common.ContatoForm;
import com.santacarolina.areas.contato.frmManageContato.ManageContatoForm;
import com.santacarolina.areas.documentos.frmDoc.DocForm;
import com.santacarolina.areas.documentos.frmImportNFe.ImportNFeForm;
import com.santacarolina.areas.documentos.pgDocumentos.DocumentosPage;
import com.santacarolina.areas.duplicatas.pgDuplicatasNaoPagas.DupNaoPagaPane;
import com.santacarolina.areas.duplicatas.pgDuplicatasPagas.DupPagaPane;
import com.santacarolina.areas.pastaContabil.frmManagePasta.ManagePastaForm;
import com.santacarolina.areas.pastaContabil.frmPastaContabil.PastaContabilForm;
import com.santacarolina.areas.pgProdutos.ProdPane;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.util.AppIcon;
import com.santacarolina.util.MenuDecorator;
import com.santacarolina.util.ViewInvoker;

public class MainFrameController implements Controller {

    private MainFrameModel model;
    private MainView view;

    public MainFrameController(MainFrameModel model, MainView view) {
        this.model = model;
        this.view = view;
        initComponents();
    }

    private void initComponents() {
        view.getChangeMode().addActionListener(e -> changeMode_onClick());

        view.getHomeButton().addActionListener(e -> homeButton_onClick());

        view.getNfeButton().addActionListener(e -> nfeButton_onClick());
        view.getNewDocButton().addActionListener(e -> newDocButton_onClick());
        view.getDocButton().addActionListener(e -> docButton_onClick());
        view.getDupPagaButton().addActionListener(e -> dupPagaButton_onClick());
        view.getDupNaoPagaButton().addActionListener(e -> dupNaoPagaButton_onClick());
        view.getProdButton().addActionListener(e -> prodButton_onClick());

        view.getAddBanco().addActionListener(e -> addBanco_onClick());
        view.getManageBancos().addActionListener(e -> manageBancos_onClick());
        view.getExtratosButton().addActionListener(e -> extratosButton_onClick());
        view.getManageConciliacaoButton().addActionListener(e -> manageConciliacao_onClick());
        view.getConciliacaoButton().addActionListener(e -> conciliacaoButton_onClick());
        view.getAddContaBancariaButton().addActionListener(e -> addContaBancaria_onClick());
        view.getManageContaBancariaButton().addActionListener(e -> manageContaBancaria_onClick());

        view.getManageContatoButton().addActionListener(e -> manageContatoButton_onClick());
        view.getAddContatoButton().addActionListener(e -> addContatoButton_onClick());
        view.getAddDadoButton().addActionListener(e -> addDadoButton_onClick());
        view.getManageDadoButton().addActionListener(e -> manageDadoButton_onClick());
        view.getAddChavePixButton().addActionListener(e -> addChavePixButton_onClick());
        view.getManageChavePixButton().addActionListener(e -> manageChavePixButton_onClick());

        view.getAddPastaButton().addActionListener(e -> addPastaButton_onClick());
        view.getManagePastaButton().addActionListener(e -> managePastaButton_onClick());

        view.getAddCategoriaButton().addActionListener(e -> addCategoriaButton_onClick());
        view.getManageCategoriaButton().addActionListener(e -> manageCategoriaButton_onClick());
        view.getAddClassificacaoButton().addActionListener(e -> addClassificacaoButton_onClick());
        view.getManageClassificacaoButton().addActionListener(e -> manageClassificaoButton_onClick());
    }

    private void changeMode_onClick() {
        if (FlatLaf.isLafDark()) FlatLightLaf.setup();
        else FlatDarkLaf.setup();
        MenuDecorator.setColors();
        AppIcon.setColors();
        view.getSideMenu().changeColors();
        view.getSideMenu().revalidate();
        model.setHomePage();
    }

    private void homeButton_onClick() { model.setHomePage(); }
    private void nfeButton_onClick() { EventQueue.invokeLater(ImportNFeForm::open); }
    private void newDocButton_onClick() { EventQueue.invokeLater(DocForm::openNew); }
    private void docButton_onClick() { EventQueue.invokeLater(() -> model.setCenterPanel(new DocumentosPage().getView())); }
    private void prodButton_onClick() { EventQueue.invokeLater(() -> model.setCenterPanel(new ProdPane().getView())); }
    private void dupPagaButton_onClick() { EventQueue.invokeLater(() -> model.setCenterPanel(new DupPagaPane().getView())); }
    private void dupNaoPagaButton_onClick() { EventQueue.invokeLater(() ->model.setCenterPanel(new DupNaoPagaPane().getView())); }

    private void manageBancos_onClick() { EventQueue.invokeLater(ManageBancoForm::open); }
    private void addBanco_onClick() { EventQueue.invokeLater(BancoForm::openNew); }
    private void extratosButton_onClick() { EventQueue.invokeLater(() -> model.setCenterPanel(new ExtratoPane().getView())); }
    private void manageConciliacao_onClick() { EventQueue.invokeLater(ManageConciliadosForm::open); }
    private void conciliacaoButton_onClick() { EventQueue.invokeLater(ConciliacaoForm::new); }
    private void addContaBancaria_onClick() { EventQueue.invokeLater(ContaForm::openNew); }
    private void manageContaBancaria_onClick() { EventQueue.invokeLater(ManageContaForm::open); }

    private void manageContatoButton_onClick() { EventQueue.invokeLater(ManageContatoForm::new);   }
    private void addContatoButton_onClick() { EventQueue.invokeLater(ContatoForm::openNew); }
    private void addDadoButton_onClick() { EventQueue.invokeLater(DadoForm::openNew); }
    private void manageDadoButton_onClick() { EventQueue.invokeLater(ManageDadoForm::new); }
    private void manageChavePixButton_onClick() { EventQueue.invokeLater(ManagePixForm::open); }
    private void addChavePixButton_onClick() { EventQueue.invokeLater(PixForm::openNew); }

    private void addPastaButton_onClick() { EventQueue.invokeLater(PastaContabilForm::openNew); }
    private void managePastaButton_onClick() { EventQueue.invokeLater(ManagePastaForm::open); }

    private void addCategoriaButton_onClick() { EventQueue.invokeLater(CategoriaForm::openNew); }
    private void manageCategoriaButton_onClick() { EventQueue.invokeLater(ManageCategoriaForm::open); }
    private void addClassificacaoButton_onClick() { EventQueue.invokeLater(ClassificacacaoForm::openNew); }
    private void manageClassificaoButton_onClick() { EventQueue.invokeLater(ManageClassificacaoForm::open); }

    @Override
    public void showView() { ViewInvoker.showFrame(view.getFrame()); }

}
