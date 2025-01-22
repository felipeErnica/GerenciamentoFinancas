package com.santacarolina.areas.pastaContabil.frmPastaContabil;

import java.util.Optional;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

import com.santacarolina.dao.PastaDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.PastaContabil;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ValidatorViolations;

public class PastaContabilValidator {

    public static boolean validate(FormModel model) throws FetchFailException {

        if (StringUtils.isBlank(model.getNomePasta())) {
            ValidatorViolations.violateEmptyFields("Nome da Pasta");
            return false;
        } else if (StringUtils.isBlank(model.getFolderPath())) {
            ValidatorViolations.violateEmptyFields("Caminho da Pasta");
            return false;
        } else if (model.getContaBancaria() == null) {
            ValidatorViolations.violateEmptyFields("Conta Bancária");
            return false;
        } 

        if (pastaExists(model)) return false;
    
        return true;
    }

    //Verifica se há uma pasta com mesmo nome, se sim, oferece opção de substituir.
    private static boolean pastaExists(FormModel model) throws FetchFailException {
        Optional<PastaContabil> optionalPasta = new PastaDAO().findByNome(model.getNomePasta());
        if (optionalPasta.isPresent()) {
            PastaContabil pastaContabil = optionalPasta.get();
            if (pastaContabil.getId() == model.getPastaContabil().getId()) return false;
            if (model.getPastaContabil().getId() != 0) {
                ValidatorViolations.violateRecordExists("Esta pasta já existe!");
                return true;
            }
            int result = OptionDialog.showReplaceDialog("Esta pasta já existe!");
            if (result == JOptionPane.YES_OPTION) {
                model.getPastaContabil().setId(optionalPasta.get().getId());
                return false;
            }
            return true;
        }
        return false;
    }

}
