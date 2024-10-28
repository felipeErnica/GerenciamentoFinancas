package com.santacarolina.areas.bancario.contaBancaria.frmContaBancaria;

import java.util.Optional;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

import com.santacarolina.dao.ContaDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.ContaBancaria;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ValidatorViolations;

/**
 * ContaValidator
 */
public class ContaValidator {

    public static boolean validate(FormModel model) throws FetchFailException {
        if (model.getBanco() == null){
            ValidatorViolations.violateEmptyFields("banco");
            return false;
        } else if (StringUtils.isBlank(model.getAgencia())) {
            ValidatorViolations.violateEmptyFields("agência");
            return false;
        } else if (StringUtils.isBlank(model.getNumeroConta())) {
            ValidatorViolations.violateEmptyFields("número da conta");
            return false;
        } else if (StringUtils.isBlank(model.getApelidoConta())) {
            ValidatorViolations.violateEmptyFields("apelido da conta");
            return false;
        } else if (StringUtils.isBlank(model.getAbreviacao())) {
            ValidatorViolations.violateEmptyFields("abreviação");
            return false;
        }

        if (contaExiste(model)) return false;

        return true;
    }

    private static boolean contaExiste(FormModel model) throws FetchFailException {
        Optional<ContaBancaria> optional = new ContaDAO().findEqual(model.getContaBancaria());
        if (optional.isPresent()) {
            ContaBancaria contaBancaria = optional.get();
            if (model.getContaBancaria().getId() != 0) {
                ValidatorViolations.violateRecordExists("Já há uma conta com este dados!");
                return true;
            }
            int result = OptionDialog.showReplaceDialog( "Esta conta já existe. Deseja substituir os dados existentes por estes?"); 
            if (result == JOptionPane.YES_OPTION) {
                model.getContaBancaria().setId(contaBancaria.getId());
                return false;
            } 
            return true;
        } 
        return true;
    }
    
}
