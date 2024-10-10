package com.santacarolina.areas.documentos.frmImportNFe;

import com.santacarolina.GerenciamentoFinancas;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.CustomErrorThrower;

public class ImportNFeForm {

    public static void main(String[] args) {
        GerenciamentoFinancas.setStyle();
        ImportNFeForm.open();
    }

    public static void open() {
        try {
            FormView view = new FormView();
            FormModel model = new FormModel();
            FormController controller = new FormController(view, model);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
