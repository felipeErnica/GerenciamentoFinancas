package com.santacarolina.areas.bancario.pix.frmPix;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.ChavePix;
import com.santacarolina.util.CustomErrorThrower;

public class PixForm {

    public static void openNew() {
        FormView view = new FormView("Nova Chave Pix", "Adicionar Chave Pix");
        buildForm(new ChavePix(), view);
    }

    public static void open(ChavePix chavePix) {
        ChavePix copy = chavePix.clone();
        FormView view = new FormView("Editar Chave Pix", "Salvar Chave Pix");
        buildForm(copy, view);
    }

    private static void buildForm(ChavePix chavePix, FormView view) {
        try {
            FormModel model = new FormModel(chavePix);
            FormController controller = new FormController(view, model);
            model.addPropertyChangeListener(view);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
