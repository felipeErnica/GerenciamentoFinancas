package com.santacarolina.areas.bancario.banco.frmBanco;

import java.util.Optional;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

import com.santacarolina.dao.BancoDAO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.Banco;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ValidatorViolations;

/**
 * BancoValidator
 */
public class BancoValidator {

    public static boolean validate(FormModel model) throws FetchFailException, DeleteFailException {
        if (StringUtils.isBlank(model.getNomeBanco())) {
            ValidatorViolations.violateEmptyFields("Nome do Banco");
            return false;
        } else if (bancoExists(model)) {
            return false;
        } else if (apelidoExists(model)) {
            return false;
        }

        return true;
    
    } 

    private static boolean bancoExists(FormModel model) throws FetchFailException, DeleteFailException {
        Optional<Banco> optionalEqual = new BancoDAO().findByNome(model.getNomeBanco());
        if (optionalEqual.isPresent()) {
            if (model.getBanco().getId() != 0 && optionalEqual.get().getId() != model.getBanco().getId()) {
                ValidatorViolations.violateRecordExists("Já há um banco com este nome!");
                return true;
            }
            int result = OptionDialog.showReplaceDialog("Este banco já existe!");
            if (result == JOptionPane.YES_OPTION) {
                model.getBanco().setId(optionalEqual.get().getId());
                return false;
            }
            return true;
        }
        return false;
    }

    private static boolean apelidoExists(FormModel model) throws FetchFailException, DeleteFailException {
        Optional<Banco> optionalEqual = new BancoDAO().findByApelido(model.getApelidoBanco());
        if (optionalEqual.isPresent()) {
            Banco banco = optionalEqual.get();
            if (banco.getId() == model.getBanco().getId()) return false;
            if (model.getBanco().getId() != 0) {
                ValidatorViolations.violateRecordExists("O banco " + banco.getNomeBanco() + " já utiliza este apelido!");
                return true;
            }
            int result = OptionDialog.showReplaceDialog("O banco " + banco.getNomeBanco() + " já utiliza este apelido!");
            if (result == JOptionPane.YES_OPTION) {
                model.getBanco().setId(optionalEqual.get().getId());
                return false;
            }
            return true;
        }
        return false;
    }

}
