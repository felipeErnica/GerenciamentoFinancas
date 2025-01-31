package com.santacarolina.areas.login;

import org.apache.commons.lang3.StringUtils;

import com.santacarolina.util.ValidatorViolations;

public class LoginValidator {

    public static boolean validate(FormModel model) {
        if (StringUtils.isBlank(model.getPassword())) {
            ValidatorViolations.violateEmptyFields("senha");
            return false;
        } else if (StringUtils.isBlank(model.getUsername())) {
            ValidatorViolations.violateEmptyFields("nome de usuário");
            return false;
        } 
        return true;
    }

}
