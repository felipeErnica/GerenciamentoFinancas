package com.santacarolina.areas.categoria.frmCategoria;

import com.santacarolina.model.CategoriaContabil;

/**
 * CategoriaForm
 */
public class CategoriaForm {

    public static void openNew() {
        FormView view = new FormView("Adicionar Nova Categoria Contábil", "Adicionar Categoria");
        FormModel model = new FormModel(new CategoriaContabil());
        build(view, model);
    }

    public static void open(CategoriaContabil categoria) {
        CategoriaContabil clone = categoria.generateCopy();
        FormView view = new FormView("Editar Categoria Contábil", "Salvar Categoria");
        FormModel model = new FormModel(clone);
        build(view, model);
    }

    private static void build(FormView view, FormModel model) {
        FormController controller = new FormController(view, model);
        model.addPropertyChangeListener(view);
        controller.showView();
    }

}
