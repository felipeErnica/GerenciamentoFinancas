package com.santacarolina.areas.bancario.extrato.pgExtrato;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.CustomErrorThrower;

public class ExtratoPane {

    private ExtratoView view;

    public ExtratoPane() {
        try {
            view = new ExtratoView();
            ExtratoModel model = new ExtratoModel();
            new ExtratoController(model, view);
            model.addPropertyChangeListener(view);
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    public ExtratoView getView() { return view; }

}
