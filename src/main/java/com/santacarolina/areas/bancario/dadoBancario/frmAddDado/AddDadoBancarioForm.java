package com.santacarolina.areas.bancario.dadoBancario.frmAddDado;

import com.santacarolina.areas.bancario.dadoBancario.common.DadoBancarioFormModel;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.DadoBancario;
import com.santacarolina.util.CustomErrorThrower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddDadoBancarioForm {

    private final Logger logger = LogManager.getLogger();

    public static DadoBancario open() {
        try {
            FormView view = new FormView();
            DadoBancarioFormModel model  = new DadoBancarioFormModel(new DadoBancario());
            FormController controller = new FormController(view, model);
            model.addPropertyChangeListener(view);
            controller.showView();
            return model.getDadoBancario();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
            return null;
        }
    }

}
