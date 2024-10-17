package com.santacarolina.areas.bancario.banco.common;

import com.santacarolina.dao.BancoDAO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.model.Banco;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ValidatorViolations;
import com.santacarolina.util.ViewInvoker;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.util.Optional;

public class BancoController implements Controller {

    private BancoModel model;
    private BancoView view;

    public BancoController(BancoView view, BancoModel model) {
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
            if (StringUtils.isBlank(model.getNomeBanco())) {
                ValidatorViolations.violateEmptyFields("Nome do Banco");
                return;
            }
            if (bancoExists()) return;
            new BancoDAO().save(model.getBanco());
            OptionDialog.showSuccessSaveMessage();
        } catch (SaveFailException | FetchFailException | DeleteFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    private boolean bancoExists() throws FetchFailException, DeleteFailException {
        Optional<Banco> optionalEqual = new BancoDAO().findByNome(model.getNomeBanco());
        if (optionalEqual.isPresent() && optionalEqual.get().getId() != model.getBanco().getId()) {
            int result = OptionDialog.showOptionDialog(
                    "Este banco já existe. Deseja substituí-lo por este?",
                    "Banco já Existe!");
            if (result == JOptionPane.YES_OPTION) {
                new BancoDAO().deleteById(model.getBanco().getId());
                Banco bancoEqual = optionalEqual.get();
                model.getBanco().setId(bancoEqual.getId());
                return false;
            }
            return true;
        }
        return false;
    }

    private void nomeBancoTextField_afterUpdate() { model.setNomeBanco(view.getNomeBancoTextField().getText()); }
    private void apelidoBancoTextField_afterUpdate() { model.setApelidoBanco(view.getApelidoBancoTextField().getText()); }

    @Override
    public void showView() { ViewInvoker.showView(view.getDialog()); }

}
