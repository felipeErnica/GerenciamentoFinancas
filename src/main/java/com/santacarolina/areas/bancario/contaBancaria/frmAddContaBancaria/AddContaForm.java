package com.santacarolina.areas.pastaContabil.frmAddContaBancaria;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.ContaBancaria;
import com.santacarolina.util.CustomErrorThrower;

public class AddContaForm {

    public AddContaForm(ContaBancaria contaBancaria) {
        try {
            AddContaModel model = new AddContaModel(contaBancaria);
            AddContaView view = new AddContaView();
            AddContaController controller = new AddContaController(view, model);
            model.addListener(controller);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
