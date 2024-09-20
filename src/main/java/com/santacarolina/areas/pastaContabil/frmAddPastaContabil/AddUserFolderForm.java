package com.santacarolina.areas.pastaContabil.frmAddPastaContabil;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.beans.PastaContabil;
import com.santacarolina.util.CustomErrorThrower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddUserFolderForm {

    private final Logger logger = LogManager.getLogger();

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
