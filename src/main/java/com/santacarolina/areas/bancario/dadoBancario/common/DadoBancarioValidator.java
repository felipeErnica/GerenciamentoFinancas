package com.santacarolina.areas.bancario.dadoBancario.common;

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

    private static final DadoDAO dadoDAO = new DadoDAO();
    private static final PixDAO pixDAO = new PixDAO();

    private DadoBancarioFormModel model;

    public DadoBancarioValidator(DadoBancarioFormModel model) {
        this.model = model;
    }

    public boolean validate() {
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
        return true;
    }

    public boolean dadoExists() throws FetchFailException, DeleteFailException {
        Optional<DadoBancario> optionalEqual = dadoDAO.getEqual(model.getDadoBancario());
        if (optionalEqual.isPresent() && optionalEqual.get().getId() != model.getDadoBancario().getId()) {
            DadoBancario dadoEqual = optionalEqual.get();
            int result = OptionDialog.showReplaceDialog("Esta conta já está registrada em nome de " + dadoEqual.getContato() + "!" +
                    " Deseja substituí-la por esta?" );
            if (result == JOptionPane.YES_OPTION) {
                model.getDadoBancario().setId(dadoEqual.getId());
                if (dadoEqual.getPixId() != null) pixDAO.deleteById(dadoEqual.getPixId());
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean pixExists() throws FetchFailException {
        if (model.getChavePix() == null) return false;
        Optional<ChavePix> pixEncontrado = pixDAO.getByChave(model.getChavePix().getChave());
        if (pixEncontrado.isPresent() && pixEncontrado.get().getId() != model.getChavePix().getId()) {
            ChavePix pixEqual = pixEncontrado.get();
            int result = OptionDialog.showOptionDialog(
                    "Esta chave pix já existe e pertence a " + pixEqual.getContato() + "! Deseja substituí-lo por este?",
                    "ATENÇÃO: Pix já existe!");
            if (result == JOptionPane.YES_OPTION) {
                model.getDadoBancario().getChavePix().setId(pixEqual.getId());
                return false;
            }
            return true;
        }
        return false;
    }

}
