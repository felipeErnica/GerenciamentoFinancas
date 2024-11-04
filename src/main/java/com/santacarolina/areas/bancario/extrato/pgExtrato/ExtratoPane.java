package com.santacarolina.areas.bancario.extrato.pgExtrato;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.Opener;
import com.santacarolina.util.CustomErrorThrower;

public class ExtratoPane implements Opener {

    private ExtratoView view;
    private ExtratoModel model;
    @SuppressWarnings("unused")
    private ExtratoController controller;

    public ExtratoView open() {
        try {
            view = new ExtratoView();
            model = new ExtratoModel();
            controller = new ExtratoController(model, view);
            model.addPropertyChangeListener(view);
            return view;
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
            return null;
        }
    }

}
