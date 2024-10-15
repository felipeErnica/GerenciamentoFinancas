package com.santacarolina.areas.duplicatas.pgDuplicatasNaoPagas;

import com.santacarolina.areas.duplicatas.common.DupTableModel;
import com.santacarolina.areas.duplicatas.common.DupView;
import com.santacarolina.areas.mainFrame.common.MainPaneView;
import com.santacarolina.dao.DuplicataDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.CustomErrorThrower;

public class DupNaoPagaPane {

    private DupView view;

    public DupNaoPagaPane() {
        try {
            view = new DupView();
            DupTableModel model = new DupTableModel(new DuplicataDAO().findNaoPagas());
            new FormController(view, model);
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    public MainPaneView getView() { return view; }

}
