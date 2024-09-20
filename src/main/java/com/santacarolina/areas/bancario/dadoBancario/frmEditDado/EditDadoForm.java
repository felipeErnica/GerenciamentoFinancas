package com.santacarolina.areas.bancario.dadoBancario.frmEditDado;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.beans.DadoBancario;
import com.santacarolina.util.CustomErrorThrower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EditDadoForm {

    private final Logger logger = LogManager.getLogger();

    public EditDadoForm(DadoBancario d) {
        try {
            EditDadoView view = new EditDadoView();
            EditDadoModel model = new EditDadoModel();
            EditDadoController controller = new EditDadoController(view, model);
            model.addListener(controller);
            model.setDadoBancario(d);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
