package com.santacarolina.areas.documentos.frmSelectNFe;

import com.santacarolina.model.DocumentoFiscal;

import java.util.List;

public class SelectNFeForm {

    public static void openListNFe(List<DocumentoFiscal> nfeList) {
        FormView view = new FormView();
        FormModel model = new FormModel(nfeList);
        FormController controller = new FormController(view, model);
        controller.showView();
    }

}
