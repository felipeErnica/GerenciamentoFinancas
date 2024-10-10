package com.santacarolina.areas.documentos.frmDoc.frmAddPix;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.Duplicata;
import com.santacarolina.util.CustomErrorThrower;

import java.util.List;

public class AddPixDup {

    public static void open(List<Duplicata> list) {
        try {
            FormView view = new FormView();
            FormModel model = new FormModel(list);
            FormController controller = new FormController(view, model);
            model.addPropertyChangeListener(view);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
