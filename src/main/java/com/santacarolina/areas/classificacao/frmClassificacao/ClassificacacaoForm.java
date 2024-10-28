package com.santacarolina.areas.classificacao.frmClassificacao;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.ClassificacaoContabil;
import com.santacarolina.util.CustomErrorThrower;

/**
 * ClassificacacaoForm
 */
public class ClassificacacaoForm {

    public static void open(ClassificacaoContabil classificacao) {
        ClassificacaoContabil copy = classificacao.generateCopy();
        FormView view = new FormView("Nova Classificação Contábil", "Adicionar Classificação");
        buildForm(view, copy);
    }

    public static void openNew() {
        FormView view = new FormView("Nova Classificação Contábil", "Adicionar Classificação");
        buildForm(view, new ClassificacaoContabil());
    }

    private static void buildForm(FormView view, ClassificacaoContabil classificacao) {
        try {
            FormModel model = new FormModel(classificacao);
            FormController controller = new FormController(view, model);
            model.addPropertyChangeListener(view);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }
}
