package com.santacarolina.areas.duplicatas.pgDuplicatasPagas;

import com.santacarolina.areas.mainFrame.common.MainPaneView;
import com.santacarolina.areas.duplicatas.common.DupTableModel;
import com.santacarolina.areas.duplicatas.common.DupView;
import com.santacarolina.dao.DuplicataDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.CustomErrorThrower;

public class DupPagaPane {

    private DupView view;

    public DupPagaPane() {
        try {
            view = new DupView();
            DupTableModel model = new DupTableModel(new DuplicataDAO().findPagas());
            FormController controller = new FormController(view, model);
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    public MainPaneView getView() { return view; }

}
