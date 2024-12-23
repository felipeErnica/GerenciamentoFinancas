package com.santacarolina.areas.bancario.pix.frmPix;

import java.util.Optional;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

import com.santacarolina.dao.PixDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.ChavePix;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ValidatorViolations;

/**
 * PixValidator
 */
public class PixValidator {

    public static boolean validate(FormModel model) throws FetchFailException {
        if (StringUtils.isBlank(model.getChave())) {
            ValidatorViolations.violateEmptyFields("chave");
            return false;
        } else if (model.getTipoPix() == null) {
            ValidatorViolations.violateEmptyFields("Tipo de Chave");
            return false;
        } else if (model.isInvalidFormat()) {
            ValidatorViolations.violateInvalidFields("chave");
        } else if (model.isContaEnabled() && model.getDadoBancario() == null) {
            ValidatorViolations.violateEmptyFields("conta");
            return false;
        }   

        if (chaveExiste(model)) return false;
        if (contaPossuiPix(model)) return false;

        return true;
    
    }

    private static boolean contaPossuiPix(FormModel model) {
        if (model.getDadoBancario() != null) return false;
        
        Optional<ChavePix> pixFromDado;
        try {
            pixFromDado = new PixDAO().findByDadoId(model.getDadoBancario().getId());
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
            return true;
        }

        if (!pixFromDado.isPresent()) {
            if (model.getChavePix().getId() != 0) {
                ValidatorViolations.violateRecordExists("Esta conta já possui uma chave Pix! Não é possível adicionar outra!");
                return true;
            }
            int result = OptionDialog.showReplaceDialog("Esta conta possuí uma chave registrada!");
            if (result == JOptionPane.YES_OPTION) {
                model.getChavePix().setId(pixFromDado.get().getId());
                return false;
            }
        }
        return false;
    }

    private static boolean chaveExiste(FormModel model) throws FetchFailException {
        Optional<ChavePix> optional = new PixDAO().findByChave(model.getChave());
        if (optional.isPresent()) {
            ChavePix chave = optional.get();
            if (model.getChavePix().getId() != 0) {
                ValidatorViolations.violateRecordExists("Este pix já existe e pertence a " + chave.getContato() + "!");
                return true;
            }
            int result = OptionDialog.showReplaceDialog("Esta chave já existe e pertence a " + chave.getContato() + "!");
            if (result == JOptionPane.YES_OPTION) {
                model.getChavePix().setId(chave.getId());
                return false;
            }
            return true;
        }
        return false;
    }
}
