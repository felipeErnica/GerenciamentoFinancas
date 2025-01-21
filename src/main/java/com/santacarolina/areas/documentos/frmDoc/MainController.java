package com.santacarolina.areas.documentos.frmDoc;

import java.awt.Dialog;

import javax.swing.WindowConstants;

import com.santacarolina.areas.documentos.frmDoc.docPanel.DocumentoController;
import com.santacarolina.areas.documentos.frmDoc.dupPanel.DupController;
import com.santacarolina.areas.documentos.frmDoc.prodPanel.ProdController;
import com.santacarolina.dao.DocumentoDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.util.CustomErrorThrower;

public class MainController implements Controller {

    private MainView view;
    private MainModel model;

    public MainController(MainView view, MainModel model) throws FetchFailException {
        this.view = view;
        this.model = model;
        new DupController(view.getDuplicatasPanel(), model.getDupModel());
        new ProdController(view.getProdutosPanel(), model.getProdutoModel());
        new DocumentoController(model.getDocModel(), view.getInfoPanel());
        init();
    }

    private void init() { view.getUpdateDocButton().addActionListener(e -> updateDocButton_onClick()); }

    private void updateDocButton_onClick() {
        try {
            if (!DocValidator.validate(model)) return;
            new DocumentoDAO().save(model.getDocumentoFiscal());
            view.getDialog().dispose();
            DocForm.openNew();
        } catch (FetchFailException | SaveFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    @Override
    public void showView() {
        view.getDialog().setSize(1200, 400);
        view.getDialog().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        view.getDialog().setLocationRelativeTo(null);
        view.getDialog().setModal(true);
        view.getDialog().setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        view.getDialog().setVisible(true);
    }

}
