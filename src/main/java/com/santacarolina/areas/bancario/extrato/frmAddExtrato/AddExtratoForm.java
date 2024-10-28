package com.santacarolina.areas.bancario.extrato.frmAddExtrato;

import com.formdev.flatlaf.FlatLightLaf;
import com.santacarolina.dao.ContaDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.ContaBancaria;
import com.santacarolina.model.Extrato;
import com.santacarolina.util.CustomErrorThrower;

import java.util.ArrayList;
import java.util.List;

public class AddExtratoForm {

    public static void main(String[] args) {
        try {
            FlatLightLaf.setup();
            new AddExtratoForm(new ContaDAO().findById(8).get(), new ArrayList<>());
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    public AddExtratoForm(ContaBancaria conta, List<Extrato> list) {
        FormView view = new FormView();
        FormModel model = new FormModel(conta, list);
        FormController controller = new FormController(view, model);
        controller.showView();
    }
}
