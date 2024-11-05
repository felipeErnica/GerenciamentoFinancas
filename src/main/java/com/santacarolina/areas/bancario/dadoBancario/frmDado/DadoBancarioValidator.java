package com.santacarolina.areas.bancario.dadoBancario.frmDado;

import com.santacarolina.dao.DadoDAO;
import com.santacarolina.dao.PixDAO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.ChavePix;
import com.santacarolina.model.DadoBancario;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ValidatorViolations;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.util.Optional;

public class DadoBancarioValidator {

    public static boolean validate(FormModel model) throws FetchFailException, DeleteFailException {
        if (model.getContato() == null) {
            ValidatorViolations.violateEmptyFields("Contato");
            return false;
        } else if (model.getBanco() == null) {
            ValidatorViolations.violateEmptyFields("Banco");
            return false;
        } else if (StringUtils.isBlank(model.getNumConta())) {
            ValidatorViolations.violateEmptyFields("Número da Conta");
            return false;
        } else if (StringUtils.isBlank(model.getAgencia())) {
            ValidatorViolations.violateEmptyFields("Agência");
            return false;
        }
        if (model.isPixEnabled()) {
            if (model.isPixInvalidFormat()) {
                ValidatorViolations.violateInvalidFields("Chave Pix");
                return false;
            } else if (model.getChavePix() == null) {
                ValidatorViolations.violateEmptyFields("Chave Pix");
                return false;
            }
        }

        if (dadoExists(model)) return false;
        if (pixExists(model)) return false;

        return true;
    }

    private static boolean dadoExists(FormModel model) throws FetchFailException, DeleteFailException {
        Optional<DadoBancario> optionalEqual = new DadoDAO().getEqual(model.getDadoBancario());
        if (optionalEqual.isPresent()) {
            DadoBancario dadoEqual = optionalEqual.get();
            if (dadoEqual.getId() == model.getDadoBancario().getId()) return false;
            if (model.getDadoBancario().getId() != 0) {
                ValidatorViolations.violateRecordExists("Esta conta já está registrada em nome de " + dadoEqual.getContato() + "!");
                return true;
            }
            int result = OptionDialog.showReplaceDialog("Esta conta já está registrada em nome de " + dadoEqual.getContato() + "!");
            if (result == JOptionPane.YES_OPTION) {
                model.getDadoBancario().setId(dadoEqual.getId());
                model.getDadoBancario().setPixId(dadoEqual.getPixId());
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean pixExists(FormModel model) throws FetchFailException {
        if (model.getChavePix() == null) return false;
        Optional<ChavePix> pixEncontrado = new PixDAO().findByChave(model.getChavePix().getChave());
        if (pixEncontrado.isPresent()) {
            ChavePix pixEqual = pixEncontrado.get();
            if (model.getDadoBancario().getPixId() != null && pixEqual.getId() == model.getDadoBancario().getPixId()) return false;
            ValidatorViolations.violateRecordExists("Esta chave pix já existe e pertence a " + pixEqual.getContato() + "!");
            return true;
        }
        return false;
    }

}
