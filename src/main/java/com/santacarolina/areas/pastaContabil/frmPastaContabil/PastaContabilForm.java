package com.santacarolina.areas.pastaContabil.frmPastaContabil;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.PastaContabil;
import com.santacarolina.util.CustomErrorThrower;

public class PastaContabilForm {

    // Método para abrir o formulário em modo de adição de uma nova pasta contábil
    public static void openNew() {
        FormView view = new FormView("Adicionar Pasta", "Nova Pasta Contábil");
        buildForm(view, new PastaContabil());
    }

    // Método para abrir o formulário para editar uma pasta contábil existente
    public static void open(PastaContabil pasta) {
        FormView view = new FormView("Salvar Pasta", "Editar Pasta Contábil");
        PastaContabil clone = pasta.clone();
        buildForm(view, clone);
    }

    // Método privado para construir o formulário com a visão e o modelo da pasta contábil
    private static void buildForm(FormView view, PastaContabil pasta) {
        try {
            FormModel model = new FormModel(pasta);
            FormController controller = new FormController(view, model);
            model.addPropertyChangeListener(view);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
