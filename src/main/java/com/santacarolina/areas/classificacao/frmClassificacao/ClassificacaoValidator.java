package com.santacarolina.areas.classificacao.frmClassificacao;

import java.util.Optional;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

import com.santacarolina.dao.ClassificacaoDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.ClassificacaoContabil;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ValidatorViolations;

/**
 * CategoriaValidator
 */
public class ClassificacaoValidator {

    public static boolean validate(FormModel model) throws FetchFailException {
        
        if (StringUtils.isBlank(model.getNome())) {
            ValidatorViolations.violateEmptyFields("nome");
            return false;
        } else if (StringUtils.isBlank(model.getNumero())) {
            ValidatorViolations.violateEmptyFields("número");
            return false;
        } else if (model.getCategoriaContabil() == null) {
            ValidatorViolations.violateEmptyFields("Categoria Contábil");
            return false;
        } else if (numeroInvalid(model)) {
            OptionDialog.showErrorDialog("O número da classificação deve começar com o número da categoria!", "Formato Inválido!");
            return false;
        } else if (numeroExists(model)) {
            return false;
        } else if (nomeExists(model)) {
            return false;
        }

        return true;

    }

    private static boolean numeroExists(FormModel model) throws FetchFailException {
        Optional<ClassificacaoContabil> optional = new ClassificacaoDAO().findByNumero(model.getNumero());
        if (optional.isPresent()) {
            ClassificacaoContabil classificacao = optional.get();
            if (classificacao.getId() == model.getClassificacao().getId()) return false;
            if (model.getClassificacao().getId() != 0) {
                ValidatorViolations.violateRecordExists("Este número já pertence a classificação " + classificacao.getNomeClassificacao() + "!");
                return true;
            }
            int result = OptionDialog.showReplaceDialog("Este número já pertence a classificação " + classificacao.getNomeClassificacao() + "!");
            if (result == JOptionPane.YES_OPTION) {
                model.getClassificacao().setId(classificacao.getId());
                return false;
            }
            return true;
        }
        return false;
    }

    private static boolean nomeExists(FormModel model) throws FetchFailException {
        Optional<ClassificacaoContabil> optional = new ClassificacaoDAO().findByNome(model.getNome());
        if (optional.isPresent()) {
            ClassificacaoContabil classificacao = optional.get();
            if (classificacao.getId() == model.getClassificacao().getId()) return false;
            if (model.getClassificacao().getId() != 0) {
                ValidatorViolations.violateRecordExists("Este nome já existe!");
                return true;
            }
            int result = OptionDialog.showReplaceDialog("Este nome já existe!");
            if (result == JOptionPane.YES_OPTION) {
                model.getClassificacao().setId(classificacao.getId());
                return false;
            }
            return true;
        }
        return false;
    }

    private static boolean numeroInvalid(FormModel model) {
        if (!model.getNumero().startsWith(model.getCategoriaContabil().getNumeroCategoria())) return true;
        else return false;
    }
    
}
