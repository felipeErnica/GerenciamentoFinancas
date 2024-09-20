package com.santacarolina.areas.mainFrame.pgDuplicatas;

import com.santacarolina.areas.mainFrame.common.MainPaneView;
import com.santacarolina.dao.DuplicataDao;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.CustomErrorThrower;

public class DupPane {

    private DupView view;

    public DupPane() {
        try {
            view = new DupView();
            DupTableModel model = new DupTableModel(new DuplicataDao().findAllHomePage());
            DupController controller = new DupController(view, model);
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    public MainPaneView getView() {
        return view;
    }

}
