package com.santacarolina.areas.pgProdutos;

import com.santacarolina.dao.ProdutoDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.Opener;
import com.santacarolina.util.CustomErrorThrower;

public class ProdPane implements Opener {

    private ProdView view;
    private ProdTableModel model;
    @SuppressWarnings("unused")
    private ProdController controller;

    public ProdView open() {
        try {
            this.view = new ProdView();
            model = new ProdTableModel(new ProdutoDAO().findAll());
            controller = new ProdController(view, model);
            return view;
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
            return null;
        }
    }

}
