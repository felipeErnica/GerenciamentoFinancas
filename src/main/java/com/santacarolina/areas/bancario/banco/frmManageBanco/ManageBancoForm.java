package com.santacarolina.areas.bancario.banco.frmManageBanco;

import com.santacarolina.GerenciamentoFinancas;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.CustomErrorThrower;

public class ManageBancoForm {

    public static void main(String[] args) {
        GerenciamentoFinancas.setStyle();
        open();
    }

    public static void open() {
        try {
            FormView view = new FormView();
            TableModel model = new TableModel();
            FormController controller = new FormController(view, model);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
