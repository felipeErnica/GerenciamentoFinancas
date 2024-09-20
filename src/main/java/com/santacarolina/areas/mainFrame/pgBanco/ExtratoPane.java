package com.santacarolina.areas.mainFrame.pgBanco;

import com.santacarolina.dao.ExtratoDao;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.CustomErrorThrower;

import java.util.ArrayList;

public class ExtratoPane {

    private ExtratoView view;

    public ExtratoPane() {
        try {
            view = new ExtratoView();
            ExtratoTableModel model = new ExtratoTableModel(new ArrayList<>());
            ExtratoController controller = new ExtratoController(model, view);
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    public ExtratoView getView() { return view; }

}
