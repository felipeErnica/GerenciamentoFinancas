package com.santacarolina.areas.contato.common;

import com.santacarolina.model.Contato;

/**
 * ContatoForm
 */
public class ContatoForm {

    public static void open(Contato contato) {
        Contato copy = contato.generateCopy();
        FormView view = new FormView("Editar Contato", "Salvar Contato");
        buildForm(view, copy);
    }

    public static void openNew() {
        FormView view = new FormView("Novo Contato", "Adicionar Contato");
        buildForm(view, new Contato());
    }

    public static void saveNew(Contato contato) {
        FormView view = new FormView("Novo Contato", "Adicionar Contato");
        buildForm(view, contato);
    }

    private static void buildForm(FormView view, Contato contato) {
        FormModel model = new FormModel(contato);
        FormController controller = new FormController(view, model);
        model.addPropertyChangeListener(view);
        controller.showView();
    }
}
