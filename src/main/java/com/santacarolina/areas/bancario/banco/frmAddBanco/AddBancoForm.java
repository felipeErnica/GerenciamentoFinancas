package com.santacarolina.areas.bancario.banco.frmAddBanco;

import com.santacarolina.GerenciamentoFinancas;
import com.santacarolina.areas.bancario.banco.common.BancoController;
import com.santacarolina.areas.bancario.banco.common.BancoModel;
import com.santacarolina.model.Banco;

public class AddBancoForm {

    public static void main(String[] args) {
        GerenciamentoFinancas.setStyle();
        open();
    }

    public static void open() {
        BancoModel model = new BancoModel(new Banco());
        FormView view = new FormView();
        BancoController controller = new BancoController(view.getBaseView(), model);
        model.addPropertyChangeListener(view.getBaseView());
        controller.showView();
    }

}
