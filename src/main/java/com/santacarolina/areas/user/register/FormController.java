package com.santacarolina.areas.user.register;

import com.santacarolina.dao.UserDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;

public class FormController {

    private FormView view;
    private FormModel model;

    public FormController(FormView view, FormModel model) {
        this.view = view;
        this.model = model;
        init();
    }

    private void init() {
        view.getRegiserButton().addActionListener(e -> loginButton_onClick());
    }

    private void loginButton_onClick() {
        model.setPassword(new String(view.getPasswordField().getPassword()));
        model.setUsername(view.getUsernameField().getText());
        model.setConfirmPassword(new String(view.getConfirmPasswordField().getPassword()));

        try {
            if (!RegisterValidator.validate(model)) return;
            new UserDAO().registerUser(model.getUser());
            OptionDialog.showSuccessSaveMessage();
            view.getDialog().dispose();
        } catch (FetchFailException | SaveFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    
}
