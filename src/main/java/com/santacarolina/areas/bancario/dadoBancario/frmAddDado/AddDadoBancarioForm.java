package com.santacarolina.areas.bancario.dadoBancario.frmAddDado;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.CustomErrorThrower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddDadoBancarioForm {

    private final Logger logger = LogManager.getLogger();

    private AddDadoBancarioModel model;

    public AddDadoBancarioForm() {
        try {
            AddDadoBancarioView view = new AddDadoBancarioView();
            model = new AddDadoBancarioModel();
            AddDadoBancarioController controller = new AddDadoBancarioController(view, model);
            model.addListener(controller);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    public boolean isUpdated() {
        return model.isUpdated();
    }

}
