package com.santacarolina.areas.pgProdutos;

import com.santacarolina.areas.mainFrame.common.MainPaneCaller;
import com.santacarolina.areas.mainFrame.common.MainPaneView;
import com.santacarolina.dao.ProdutoDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.CustomErrorThrower;

public class ProdPane implements MainPaneCaller {

    private ProdView view;

    public ProdPane() {
        try {
            this.view = new ProdView();
            ProdTableModel model = new ProdTableModel(new ProdutoDAO().findAll());
            new ProdController(view, model);
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    @Override
    public MainPaneView getView() { return view; }

}
