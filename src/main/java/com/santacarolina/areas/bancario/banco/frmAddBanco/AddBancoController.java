package com.santacarolina.areas.bancario.banco.frmAddBanco;

import com.santacarolina.dao.BancoDAO;
import com.santacarolina.enums.Replacement;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.interfaces.FormListener;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;

import java.awt.*;

public class AddBancoController implements Controller, FormListener {

    private AddBancoModel model;
    private AddBancoView view;

    public AddBancoController(AddBancoView view, AddBancoModel model) {
        this.model = model;
        this.view = view;
        initComponents();
    }

    private void initComponents() {
        view.getNomeBancoTextField().addFocusListener((AfterUpdateListener) e -> nomeBancoTextField_afterUpdate());
        view.getApelidoBancoTextField().addFocusListener((AfterUpdateListener) e -> apelidoBancoTextField_afterUpdate());
        view.getAddBanco().addActionListener(e -> addBanco_onClick());
    }

    private void addBanco_onClick() {
        try {
            if (model.updatingNotAllowed()) return;
            if (model.replaceBanco() == Replacement.REPLACE_REJECTED) return;
            new BancoDAO().save(model.getBanco());
            OptionDialog.showSuccessSaveMessage();
        } catch (SaveFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    private void nomeBancoTextField_afterUpdate() {
        EventQueue.invokeLater(() -> model.setNomeBanco(view.getNomeBancoTextField().getText()));
    }

    private void apelidoBancoTextField_afterUpdate() {
        EventQueue.invokeLater(() -> model.setApelidoBanco(view.getApelidoBancoTextField().getText()));
    }

    @Override
    public void showView() { ViewInvoker.showView(view.getDialog()); }

    @Override
    public void update(String property) {
        switch (property) {
            case AddBancoModel.NOME_BANCO -> view.getNomeBancoTextField().setText(model.getNomeBanco());
            case AddBancoModel.APELIDO -> view.getApelidoBancoTextField().setText(model.getApelidoBanco());
        }
    }

}
