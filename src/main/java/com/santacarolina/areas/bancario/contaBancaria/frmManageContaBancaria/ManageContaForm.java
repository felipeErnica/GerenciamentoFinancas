package com.santacarolina.areas.bancario.contaBancaria.frmManageContaBancaria;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.CustomErrorThrower;

/**
 * ManageContaForm
 */
public class ManageContaForm {

    public static void open() {
        try {
            FormView view = new FormView();
            ContaTableModel model = new ContaTableModel();
            FormController controller = new FormController(view, model);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
