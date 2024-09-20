package com.santacarolina.areas.bancario.banco.frmAddBanco;

import com.formdev.flatlaf.FlatDarkLaf;
import com.santacarolina.model.beans.Banco;

public class AddBancoForm {

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        new AddBancoForm();
    }

    public AddBancoForm() {
        AddBancoModel model = new AddBancoModel(new Banco());
        AddBancoView view = new AddBancoView();
        AddBancoController controller = new AddBancoController(view, model);
        model.addListener(controller);
        controller.showView();
    }
}
