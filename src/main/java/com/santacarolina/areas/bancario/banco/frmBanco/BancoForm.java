package com.santacarolina.areas.bancario.banco.frmBanco;

import com.santacarolina.model.Banco;

public class BancoForm {

    public static void open(Banco banco) {
        Banco copy = banco.generateCopy();
        FormView view = new FormView("Editar Banco", "Salvar Banco");
        buildForm(view, copy);
    }

    public static void openNew() {
        FormView view = new FormView("Novo Banco", "Adicionar Banco");
        buildForm(view, new Banco());
    }

    private static void buildForm(FormView view, Banco banco) {
        FormModel model = new FormModel(banco);
        FormController controller = new FormController(view, model);
        model.addPropertyChangeListener(view);
        controller.showView();
    }

}
