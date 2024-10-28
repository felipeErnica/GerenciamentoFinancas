package com.santacarolina.areas.bancario.banco.frmBanco;

import com.santacarolina.dao.BancoDAO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;

public class FormController implements Controller {

    private FormModel model;
    private FormView view;

    public FormController(FormView view, FormModel model) {
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
            if (!BancoValidator.validate(model))
            new BancoDAO().save(model.getBanco());
            OptionDialog.showSuccessSaveMessage();
        } catch (SaveFailException | FetchFailException | DeleteFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }


    private void nomeBancoTextField_afterUpdate() { model.setNomeBanco(view.getNomeBancoTextField().getText()); }
    private void apelidoBancoTextField_afterUpdate() { model.setApelidoBanco(view.getApelidoBancoTextField().getText()); }

    @Override
    public void showView() { ViewInvoker.showView(view.getDialog()); }

}
