package com.santacarolina.areas.bancario.pix.frmEditPix;

import com.santacarolina.areas.bancario.pix.formModel.PixFormModel;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.beans.ChavePix;
import com.santacarolina.util.CustomErrorThrower;

public class EditPixForm {

    PixFormModel model;

    public EditPixForm(ChavePix pix) {
        try {

            ChavePix editaPix = new ChavePix.Builder()
                    .setId(pix.getId())
                    .setTipoPix(pix.getTipoPix())
                    .setChave(pix.getChave())
                    .setContato(pix.getContato())
                    .setDadoId(pix.getDadoId())
                    .build();

            EditPixView view = new EditPixView();
            model = new PixFormModel(editaPix);
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
