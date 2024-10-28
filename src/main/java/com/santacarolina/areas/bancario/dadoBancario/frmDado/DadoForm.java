package com.santacarolina.areas.bancario.dadoBancario.frmDado;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.DadoBancario;
import com.santacarolina.util.CustomErrorThrower;

/**
 * DadoForm
 */
public class DadoForm {

    public static void open(DadoBancario dadoBancario) {
        DadoBancario copy = dadoBancario.generateCopy();
        FormView view = new FormView("Editar Dado Bancário", "Salvar Dado Bancário");
        buildForm(view, copy);
    }
    
    public static void openNew() {
        FormView view = new FormView("Novo Dado Bancário", "Adicionar Dado Bancário");
        buildForm(view, new DadoBancario());
    }

    private static void buildForm(FormView view, DadoBancario dadoBancario) {
        try {
            FormModel model = new FormModel(dadoBancario);
            FormController controller;
            controller = new FormController(view, model);
            model.addPropertyChangeListener(view);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }
}
