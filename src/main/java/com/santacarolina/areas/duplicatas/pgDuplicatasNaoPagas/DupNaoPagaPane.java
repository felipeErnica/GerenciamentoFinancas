package com.santacarolina.areas.duplicatas.pgDuplicatasNaoPagas;

import com.santacarolina.areas.duplicatas.common.DupTableModel;
import com.santacarolina.areas.duplicatas.common.DupView;
import com.santacarolina.dao.DuplicataDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.Opener;
import com.santacarolina.util.CustomErrorThrower;

@SuppressWarnings("unused")
public class DupNaoPagaPane implements Opener {

    private DupView view;
    private DupTableModel model;
    private FormController controller;

    public DupView open() {
        try {
            view = new DupView();
            model = new DupTableModel(new DuplicataDAO().findNaoPagas());
            controller = new FormController(view, model);
            return view;
            
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
            return null;
        }
    }

}
