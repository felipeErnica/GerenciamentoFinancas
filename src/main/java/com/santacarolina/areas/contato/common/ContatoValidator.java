package com.santacarolina.areas.contato.common;

import com.santacarolina.dao.ContatoDAO;
import com.santacarolina.util.ValidatorViolations;
import org.apache.commons.lang3.StringUtils;

public class ContatoValidator {

    private ContatoDAO dao;
    private FormContatoModel model;

    public ContatoValidator(FormContatoModel model) {
        this.model = model;
    }

    public boolean validate() {
        return validateEmptyFields() && validateInvalidFields();
    }

    private boolean validateEmptyFields() {
        if (StringUtils.isBlank(model.getName())) {
            ValidatorViolations.violateEmptyFields("Nome");
            return false;
        } else if (model.isDocsEnabled() && model.getContato().isEmptyDocuments()) {
            ValidatorViolations.violateEmptyFields("Documentos");
            return false;
        } else return true;
    }

    private boolean validateInvalidFields() {
        if (model.isCnpjInvalidFormat()) {
            ValidatorViolations.violateInvalidFields("CNPJ");
            return false;
        } else if (model.isCpfInvalidFormat()) {
            ValidatorViolations.violateInvalidFields("CPF");
            return false;
        }
        else if (model.isIeInvalidFormat()) {
            ValidatorViolations.violateInvalidFields("IE");
            return false;
        } else return true;
    }

}
