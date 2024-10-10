package com.santacarolina.areas.contato.frmAddContato;

import com.santacarolina.model.Contato;

public class AddContatoForm {

    public static void openNew() {
        FormModel model = new FormModel(new Contato());
        buildForm(model);
    }

    public static Contato addContato(Contato contato) {
        System.out.println(contato.print());
        Contato clone = contato.clone();
        FormModel model = new FormModel(clone);
        buildForm(model);
        return model.getContatoSaved();
    }

    private static void buildForm(FormModel model) {
        FormView view = new FormView();
        FormController controller = new FormController(view, model);
        model.addPropertyChangeListener(view);
        controller.showView();
    }

}
