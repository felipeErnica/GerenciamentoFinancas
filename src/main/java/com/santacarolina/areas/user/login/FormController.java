package com.santacarolina.areas.user.login;

import com.santacarolina.areas.mainFrame.mainPage.MainFrame;
import com.santacarolina.dao.UserDAO;
import com.santacarolina.exceptions.AuthenticationException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.model.AuthToken;
import com.santacarolina.util.ApiRequest;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.ViewInvoker;

public class FormController implements Controller {

    private FormView view;
    private FormModel model;

    public FormController(FormView view, FormModel model) {
        this.view = view;
        this.model = model;
        init();
    }

    private void init() {
        view.getLoginButton().addActionListener(e -> loginButton_onClick());
    }

    private void loginButton_onClick() {
        model.setPassword(new String(view.getPasswordField().getPassword()));
        model.setUsername(view.getUsernameField().getText());

        if (!LoginValidator.validate(model)) return;
        
        try {
            AuthToken authToken = new UserDAO().login(model.getUser()).get();
            ApiRequest.setAuthenticationToken(authToken.token());
            view.getDialog().dispose();
            MainFrame.open();
        } catch (FetchFailException | AuthenticationException e) {
            CustomErrorThrower.throwError(e);
        }
        
    }

    @Override
    public void showView() {
        ViewInvoker.showView(view.getDialog());
    }
    
}
