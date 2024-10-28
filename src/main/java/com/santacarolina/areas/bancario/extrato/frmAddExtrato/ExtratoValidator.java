package com.santacarolina.areas.bancario.extrato.frmAddExtrato;

import com.santacarolina.model.Extrato;
import com.santacarolina.util.ValidatorViolations;

public class ExtratoValidator {

    public static boolean validate (FormModel model) {
        if (model.getList().isEmpty()) {
            ValidatorViolations.violateEmptyList("extratos");
            return false;
        }
        for (int i = 0; i < model.getList().size(); i++) {
            Extrato e = model.getList().get(i);
            if (e.getDataTransacao() == null) {
                ValidatorViolations.violateEmptyFieldList("Data de Transação", "extratos", i + 1);
                return false;
            }
        }
        return true;
    }

}
