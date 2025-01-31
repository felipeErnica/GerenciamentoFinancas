package com.santacarolina.areas.user.register;

public class RegisterForm {

    public static void open() {
        FormView view = new FormView();
        FormModel model = new FormModel();
        FormController controller = new FormController(view, model);
        controller.showView();
    }
    
}
