package com.santacarolina.areas.bancario.conciliacao.frmOutrosMovimentos;

import com.formdev.flatlaf.FlatLightLaf;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.CustomErrorThrower;

public class OutrosMovimentosForm {

    public static void main(String[] args) {
        FlatLightLaf.setup();
        new OutrosMovimentosForm();
    }

    public OutrosMovimentosForm() {
        try {
            OutrosMovimentosView view = new OutrosMovimentosView();
            OutrosMovimentosModel model = new OutrosMovimentosModel();
            OutrosMovimentosController controller = new OutrosMovimentosController(view, model);
            model.addPropertyChangeListener(view);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
