package com.santacarolina.areas.bancario.dadoBancario.frmEditDado;

import com.santacarolina.areas.bancario.dadoBancario.common.DadoBancarioFormModel;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.DadoBancario;
import com.santacarolina.util.CustomErrorThrower;

public class EditDadoForm {

    public static DadoBancario open(DadoBancario d) {
        try {
            DadoBancario clone = d.clone();
            FormView view = new FormView();
            DadoBancarioFormModel model = new DadoBancarioFormModel(clone);
            FormController controller = new FormController(view, model);
            model.addPropertyChangeListener(view);
            controller.showView();
            return model.getDadoSaved();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
            return null;
        }
    }

    public EditDadoForm(DadoBancario d) {
    }

}
