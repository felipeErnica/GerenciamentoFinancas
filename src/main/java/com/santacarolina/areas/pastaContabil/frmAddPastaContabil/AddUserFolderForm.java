package com.santacarolina.areas.pastaContabil.frmAddPastaContabil;

import com.santacarolina.areas.pastaContabil.common.PastaContabilController;
import com.santacarolina.areas.pastaContabil.common.PastaContabilModel;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.PastaContabil;
import com.santacarolina.util.CustomErrorThrower;

public class AddUserFolderForm {

    public AddUserFolderForm(PastaContabil p) {
        try {
            AddPastaContabilView view = new AddPastaContabilView();
            PastaContabilModel model = new PastaContabilModel(p);
            PastaContabilController controller = new PastaContabilController(view.getBaseView(), model);
                model.addPropertyChangeListener(view.getBaseView());
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
