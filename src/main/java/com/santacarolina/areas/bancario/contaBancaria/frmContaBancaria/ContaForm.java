package com.santacarolina.areas.bancario.contaBancaria.frmContaBancaria;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.ContaBancaria;
import com.santacarolina.util.CustomErrorThrower;

public class ContaForm {

    public static void openNew () {
        FormView view = new FormView("Nova Conta Bancária", "Adicionar Conta");
        buildForm(view, new ContaBancaria());
    }

    public static void open(ContaBancaria contaBancaria) {
        FormView view = new FormView("Editar Conta Bancária", "Salvar Conta");
        ContaBancaria clone = contaBancaria.generateCopy();
        buildForm(view, clone);
    }

    private static void buildForm(FormView view, ContaBancaria contaBancaria) {
        try {
            FormModel model = new FormModel(contaBancaria);
            FormController controller = new FormController(view, model);
            model.addPropertyChangeListener(view);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
