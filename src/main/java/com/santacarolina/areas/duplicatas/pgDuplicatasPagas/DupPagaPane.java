package com.santacarolina.areas.duplicatas.pgDuplicatasPagas;

import com.santacarolina.areas.mainFrame.common.MainPaneView;
import com.santacarolina.areas.duplicatas.common.DupTableModel;
import com.santacarolina.areas.duplicatas.common.DupView;
import com.santacarolina.areas.homePage.HomePage;
import com.santacarolina.dao.DuplicataDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.Opener;
import com.santacarolina.util.CustomErrorThrower;

@SuppressWarnings("unused")
public class DupPagaPane implements Opener {

    private DupView view;
    private DupTableModel model;
    private FormController controller;

    public DupView open() {
        try {
            view = new DupView();
            model = new DupTableModel(new DuplicataDAO().findPagas());
            controller = new FormController(view, model);
            return view;
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
            return null;
        }
    }

    public MainPaneView getView() { return view; }

}
