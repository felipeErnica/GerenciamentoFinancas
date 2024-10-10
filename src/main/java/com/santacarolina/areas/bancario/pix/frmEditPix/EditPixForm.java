package com.santacarolina.areas.bancario.pix.frmEditPix;

import com.santacarolina.areas.bancario.pix.formModel.PixFormModel;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.ChavePix;
import com.santacarolina.util.CustomErrorThrower;

public class EditPixForm {

    PixFormModel model;

    public EditPixForm(ChavePix pix) {
        try {

            ChavePix clonePix = pix.clone();

            EditPixView view = new EditPixView();
            model = new PixFormModel(clonePix);
            EditPixController controller = new EditPixController(view, model);
            model.addPropertyChangeListener(view);
            controller.showView();

        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    public boolean isUpdated() {
        return model.isUpdated();
    }

}
