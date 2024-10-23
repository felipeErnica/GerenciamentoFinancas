package com.santacarolina.areas.homePage;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.CustomErrorThrower;

import javax.swing.*;

public class HomePage {

    public static JPanel getMainPanel() {
        try {
            FormView view = new FormView();
            FormModel model = new FormModel();
            new FormController(view, model);
            model.addPropertyChangeListener(view);
            return view.getMainPanel();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
            return null;
        }
    }

}
