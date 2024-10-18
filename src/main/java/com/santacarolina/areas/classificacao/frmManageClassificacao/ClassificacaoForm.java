package com.santacarolina.areas.classificacao.frmManageClassificacao;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.CustomErrorThrower;

/**
 * ClassificacaoForm
 */
public class ClassificacaoForm {

    public static void open() {
        try {
            FormView view = new FormView();
            ClassificacaoTableModel tableModel = new ClassificacaoTableModel();
            FormController controller = new FormController(view, tableModel);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    } 

}
