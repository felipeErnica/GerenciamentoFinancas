package com.santacarolina.areas.bancario.pix.frmAddPix;

import com.santacarolina.areas.bancario.pix.formModel.PixFormModel;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.ChavePix;
import com.santacarolina.util.CustomErrorThrower;

public class AddPixForm {

    private PixFormModel model;

    public AddPixForm() {
        try {
            AddPixView view = new AddPixView();
            model = new PixFormModel(new ChavePix());
            AddPixController controller = new AddPixController(view, model);
            model.addPropertyChangeListener(view);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    public boolean isUpdated() { return model.isUpdated(); }

}
