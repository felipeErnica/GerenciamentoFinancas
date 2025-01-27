package com.santacarolina.areas.relatorio;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;

import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ValidatorViolations;

public class RelatorioValidator {

    public static boolean validate(FormModel model) {
        if (model.getDataFim() == null) {
            ValidatorViolations.violateEmptyFields("Data Final");
            return false;
        } else if (model.getDataInicio() == null) {
            ValidatorViolations.violateEmptyFields("Data Inicio");
            return false;
        } else if (StringUtils.isBlank(model.getCaminho())) {
            ValidatorViolations.violateEmptyFields("Caminho");
            return false;
        } else if (caminhoInvalido(model.getCaminho())) {
            OptionDialog.showErrorDialog("O caminho selecionado não existe!", 
                "ERRO - Caminho Inválido!");
            return false;
        } else if (model.getDataInicio().isAfter(model.getDataFim())) {
            OptionDialog.showErrorDialog("A data de início deve ser anterior a data final!", "ERRO - Data Inválida!");
            return false;
        }
        
        return true;
    }

    private static boolean caminhoInvalido(String caminho) {
        Path path = Paths.get(caminho);
        return Files.notExists(path);
    }
}
