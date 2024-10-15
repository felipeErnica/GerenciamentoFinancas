package com.santacarolina.areas.documentos.frmDoc;

import com.santacarolina.GerenciamentoFinancas;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.DocumentoFiscal;
import com.santacarolina.util.CustomErrorThrower;

public class DocForm {

    public static void main(String[] args) {
        GerenciamentoFinancas.setStyle();
        openNew();
    }

    public static void openNew() { generateForm(new DocumentoFiscal()); }

    public static void open(DocumentoFiscal d) {
        DocumentoFiscal clone = d.clone();
        generateForm(clone);
    }

    private static void generateForm(DocumentoFiscal d) {
        try {
            MainView view = new MainView();
            MainModel model = new MainModel(d);
            MainController controller = new MainController(view, model);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
