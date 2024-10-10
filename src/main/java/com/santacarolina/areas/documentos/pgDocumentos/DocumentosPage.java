package com.santacarolina.areas.documentos.pgDocumentos;

import com.santacarolina.areas.mainFrame.common.MainPaneCaller;
import com.santacarolina.areas.mainFrame.common.MainPaneView;
import com.santacarolina.dao.DocumentoDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.CustomErrorThrower;

public class DocumentosPage implements MainPaneCaller {

    private PageView view;

    public DocumentosPage() {
        try {
            DocumentoDAO dao = new DocumentoDAO();
            view = new PageView();
            DocTableModel model = new DocTableModel(dao.findAll());
            PageController controller = new PageController(model, view);
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    @Override
    public MainPaneView getView() { return view; }

}
