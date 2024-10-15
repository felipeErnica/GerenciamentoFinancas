package com.santacarolina.areas.pastaContabil.frmManagePasta;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.CustomErrorThrower;

public class ManagePastaForm {

    public static void open() {
        try {
            FormView view = new FormView();
            PastaTableModel model = new PastaTableModel();
            FormController controller = new FormController(view, model);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
