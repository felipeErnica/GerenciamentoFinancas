package com.santacarolina.areas.documentos.frmDoc.frmAddPix;

import java.util.List;

import com.santacarolina.dto.DuplicataDTO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.CustomErrorThrower;

public class AddPixDup {

    public static void open(List<DuplicataDTO> list) {
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
