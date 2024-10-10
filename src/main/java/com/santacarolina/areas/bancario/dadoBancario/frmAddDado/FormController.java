package com.santacarolina.areas.bancario.dadoBancario.frmAddDado;

import com.santacarolina.areas.bancario.dadoBancario.common.DadoBancarioController;
import com.santacarolina.areas.bancario.dadoBancario.common.DadoBancarioFormModel;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.util.ViewInvoker;

public class FormController implements Controller {

    private DadoBancarioController baseController;
    private FormView view;
    private DadoBancarioFormModel model;

    public FormController(FormView view, DadoBancarioFormModel model) throws FetchFailException {
        baseController = new DadoBancarioController(view.getBaseView(), model);
        this.view = view;
        this.model = model;
    }

    @Override
    public void showView() { ViewInvoker.showView(view.getDialog()); }

}
