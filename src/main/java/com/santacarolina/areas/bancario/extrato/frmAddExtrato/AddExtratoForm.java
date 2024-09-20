package com.santacarolina.areas.bancario.extrato.frmAddExtrato;

import com.formdev.flatlaf.FlatLightLaf;
import com.santacarolina.dao.ContaDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.beans.ContaBancaria;
import com.santacarolina.util.CustomErrorThrower;

public class AddExtratoForm {

    public static void main(String[] args) {
        try {
            FlatLightLaf.setup();
            new AddExtratoForm(new ContaDAO().findById(8).get());
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    public AddExtratoForm(ContaBancaria conta) {
        AddExtratoView view = new AddExtratoView();
        AddExtratoModel model = new AddExtratoModel(conta);
        AddExtratoController controller = new AddExtratoController(view, model);
        controller.showView();
    }
}
