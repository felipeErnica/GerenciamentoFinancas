package com.santacarolina.areas.pastaContabil.frmAddPastaContabil;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.PastaContabil;
import com.santacarolina.util.CustomErrorThrower;

public class AddUserFolderForm {

    public AddUserFolderForm(PastaContabil p) {
        try {
            AddUserFolderView view = new AddUserFolderView();
            AddUserFolderModel model = new AddUserFolderModel(p);
            AddUserFolderController controller = new AddUserFolderController(view, model);
            model.addListener(controller);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
