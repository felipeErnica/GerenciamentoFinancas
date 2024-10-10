package com.santacarolina.areas.bancario.banco.frmEditBanco;

import com.santacarolina.areas.bancario.banco.common.BancoController;
import com.santacarolina.areas.bancario.banco.common.BancoModel;
import com.santacarolina.areas.bancario.banco.frmAddBanco.FormView;
import com.santacarolina.model.Banco;

public class EditBanco {

    public static void open(Banco banco) {
        Banco clone = banco.clone();
        FormView view = new FormView();
        BancoModel model = new BancoModel(clone);
        BancoController controller = new BancoController(view.getBaseView(), model);
        model.addPropertyChangeListener(view.getBaseView());
        controller.showView();
    }

}
