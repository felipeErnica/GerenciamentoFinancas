package com.santacarolina.areas.categoria.frmCategoria;

import com.santacarolina.model.CategoriaContabil;

/**
 * CategoriaForm
 */
public class CategoriaForm {

    public static void openNew() {
        FormView view = new FormView();
        FormModel model = new FormModel(new CategoriaContabil());
        FormController controller = new FormController(view, model);
        model.addPropertyChangeListener(view);
        controller.showView();
    }
}
