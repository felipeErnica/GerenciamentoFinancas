package com.santacarolina.areas.relatorio;

import java.awt.EventQueue;

import com.santacarolina.dao.ProdutoDuplicataDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.CustomErrorThrower;

public class RelatorioForm {

    public static void open() {
        EventQueue.invokeLater(() -> {
            try {
                FormView view = new FormView();
                FormModel model = new FormModel(new ProdutoDuplicataDAO().findAll());
                FormController controller = new FormController(view, model);
                controller.showView();
            } catch (FetchFailException e) {
                CustomErrorThrower.throwError(e);
            }
        });
    }

}
