package com.santacarolina.areas.user.login;

public class LoginForm {

   public static void open() {
        FormView view = new FormView();
        FormModel model = new FormModel();
        FormController controller = new FormController(view, model);
        controller.showView();
   } 
}
