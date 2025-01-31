package com.santacarolina.areas.login;

import com.github.weisj.jsvg.nodes.View;
import com.santacarolina.interfaces.Controller;
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
        model.setPassword(view.getPasswordField().getPassword().toString());
        model.setUsername(view.getUsernameField().getText());
        if (!LoginValidator.validate(model)) return;
        
    }

    @Override
    public void showView() {
        ViewInvoker.showView(view.getDialog());
    }
    
}
