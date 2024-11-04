package com.santacarolina.areas.documentos.pgDocumentos;

import com.santacarolina.dao.DocumentoDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.Opener;
import com.santacarolina.util.CustomErrorThrower;

public class DocumentosPage implements Opener {

    private PageView view;
    @SuppressWarnings("unused")
    private PageController controller;
    private DocTableModel model;

    public PageView open() {
        try {
            DocumentoDAO dao = new DocumentoDAO();
            view = new PageView();
            model = new DocTableModel(dao.findAll());
            new PageController(model, view);
            return view;
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
            return null;
        }
    }

}
