package com.santacarolina.areas.homePage;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.CustomErrorThrower;

import javax.swing.*;

public class HomePage {

    public static JPanel getMainPanel() {
        try {
            FormView view = new FormView();
            FormModel model = new FormModel();
            FormController controller = new FormController(view, model);
            return view.getMainPanel();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
            return null;
        }
    }

}
