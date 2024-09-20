package com.santacarolina.areas.bancario.conciliacao.frmConciliacao;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.CustomErrorThrower;

public class ConciliacaoForm {

    public ConciliacaoForm() {
        try {
            ConciliacaoView view = new ConciliacaoView();
            ConciliacaoModel model = new ConciliacaoModel();
            ConciliacaoController controller = new ConciliacaoController(view, model);
            model.addPropertyChangeListener(view);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
