package com.santacarolina.areas.bancario.banco.frmAddBanco;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.areas.bancario.banco.common.BancoView;
import com.santacarolina.util.AppIcon;

public class FormView {

    private BancoView baseView;

    public FormView() {
        this.baseView = new BancoView();
        baseView.getDialog().setTitle("Adicionar Novo Banco");
        baseView.getAddBanco().setText("Adicionar Banco");
    }

    public BancoView getBaseView() { return baseView; }
}
