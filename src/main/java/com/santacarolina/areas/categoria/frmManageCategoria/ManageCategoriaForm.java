package com.santacarolina.areas.categoria.frmManageCategoria;

import com.santacarolina.GerenciamentoFinancas;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.CustomErrorThrower;

/**
 * ManageCategoriaForm
 */
public class ManageCategoriaForm {

    public static void main(String[] args) {
        GerenciamentoFinancas.setStyle();
        ManageCategoriaForm.open();
    }

    public static void open() {
        try {
            FormView view = new FormView();
            CategoriaTableModel model = new CategoriaTableModel();
            FormController controller = new FormController(view, model);
            controller.showView();
        } catch(FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
