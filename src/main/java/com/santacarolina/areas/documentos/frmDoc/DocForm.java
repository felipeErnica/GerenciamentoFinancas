package com.santacarolina.areas.documentos.frmDoc;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.DocumentoFiscal;
import com.santacarolina.util.CustomErrorThrower;

public class DocForm {

    public static final String DOC_NFE = "nfe";
    public static final String DOC_NEW = "new";
    public static final String DOC_EXISTS = "exists";

    public static void openNew() { generateForm(new DocumentoFiscal(), DOC_NEW); }
    public static void openNfe(DocumentoFiscal d) { generateForm(d, DOC_NFE); }

    public static void open(DocumentoFiscal d) {
        DocumentoFiscal clone = d.clone();
        generateForm(clone, DOC_EXISTS);
    }

    private static void generateForm(DocumentoFiscal d, String statusDoc) {
        try {
            MainView view = new MainView();
            MainModel model = new MainModel(d, statusDoc);
            MainController controller = new MainController(view, model);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
