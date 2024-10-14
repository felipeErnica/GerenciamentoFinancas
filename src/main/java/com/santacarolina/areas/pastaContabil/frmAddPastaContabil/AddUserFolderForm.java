package com.santacarolina.areas.pastaContabil.frmAddPastaContabil;

import com.santacarolina.areas.pastaContabil.common.PastaContabilController;
import com.santacarolina.areas.pastaContabil.common.PastaContabilModel;
import com.santacarolina.areas.pastaContabil.common.PastaContabilView;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.PastaContabil;
import com.santacarolina.util.CustomErrorThrower;

public class AddUserFolderForm {

    public AddUserFolderForm(PastaContabil p) {
        try {
            PastaContabilView view = new PastaContabilView("Adicionar Pasta", "Nova Pasta Contábil");
            PastaContabilModel model = new PastaContabilModel(p);
            PastaContabilController controller = new PastaContabilController(view, model);
            model.addPropertyChangeListener(view);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
