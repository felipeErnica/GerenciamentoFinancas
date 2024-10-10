package com.santacarolina.areas.bancario.banco.frmEditBanco;

import com.santacarolina.areas.bancario.banco.common.BancoView;

public class FormView {

    private BancoView baseView;

    public FormView() {
        this.baseView = new BancoView();
        baseView.getDialog().setTitle("Editar Banco");
        baseView.getAddBanco().setText("Salvar Banco");
    }

    public BancoView getBaseView() { return baseView; }

}
