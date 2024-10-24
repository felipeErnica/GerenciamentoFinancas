package com.santacarolina.areas.bancario.pix.frmManagePix;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.CustomErrorThrower;

public class ManagePixForm {

    public static void open() {
        try {
            PixTableModel model = new PixTableModel();
            ManagePixView view = new ManagePixView();
            ManagePixController controller = new ManagePixController(model, view);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }
}
