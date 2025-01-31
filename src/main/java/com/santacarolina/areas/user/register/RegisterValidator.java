package com.santacarolina.areas.user.register;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

import com.santacarolina.dao.UserDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.ValidatorViolations;

public class RegisterValidator {

    public static boolean validate(FormModel model) throws FetchFailException {
        if (StringUtils.isBlank(model.getPassword())) {
            ValidatorViolations.violateEmptyFields("senha");
            return false;
        } else if (StringUtils.isBlank(model.getUsername())) {
            ValidatorViolations.violateEmptyFields("nome de usuário");
            return false;
        } else if (model.getPassword().equals(model.getConfirmPassword())) {
            JOptionPane.showMessageDialog(null, 
                "As senhas registradas não são iguais!", 
                "ERRO - Senhas diferentes!", 
                JOptionPane.ERROR
            );
            return false;
        }

        if (userExists(model.getUsername())) return false;

        return true;
    }

    private static boolean userExists(String username) throws FetchFailException {
        if (new UserDAO().findByUsername(username).isPresent()) {
            ValidatorViolations.violateRecordExists("Já existe um usuário com este nome!");
            return true;
        }
        return false;
    }

}
