package com.santacarolina.areas.categoria.frmCategoria;

import java.util.Optional;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

import com.santacarolina.dao.CategoriaDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.CategoriaContabil;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ValidatorViolations;

/**
 * CategoriaValidator
 */
public class CategoriaValidator {

    public static boolean validate(FormModel model) throws FetchFailException {
        if (model.getFluxoCaixa() == null) {
            ValidatorViolations.violateEmptyFields("Fluxo de Caixa");
            return false;
        } else if (StringUtils.isBlank(model.getNumeroEtiqueta())) {
            ValidatorViolations.violateEmptyFields("Número da Etiqueta");
            return false;
        } else if (StringUtils.isBlank(model.getNomeCategoria())) {
            ValidatorViolations.violateEmptyFields("Nome da Categoria");
            return false;
        } else if (!numeroValido(model)) {
            OptionDialog.showErrorDialog("O Número da Etiqueta deve ser começar com o Número do Fluxo de Caixa correspondente!", "Número inválido");
            return false;
        } 

        if (nomeExiste(model)) return false;
        if (numeroExiste(model)) return false;
        
        return true;
    }

    private static boolean nomeExiste(FormModel model) throws FetchFailException {
        Optional<CategoriaContabil> optional = new CategoriaDAO().findByNome(model.getNomeCategoria());
        if (optional.isPresent() && optional.get().getId() != model.getCategoriaContabil().getId()) {
            int result = OptionDialog.showReplaceDialog("Esta categoria já existe! Deseja substituir os dados existentes por este?");
            if (result == JOptionPane.YES_OPTION) {
                model.getCategoriaContabil().setId(optional.get().getId());
                return false;
            }
            return true;
        }
        return false;
    }

    private static boolean numeroExiste(FormModel model) throws FetchFailException {
        Optional<CategoriaContabil> optional = new CategoriaDAO().findByNumero(model.getNumeroEtiqueta());
        if (optional.isPresent() && optional.get().getId() != model.getCategoriaContabil().getId()) {
            int result = OptionDialog.showReplaceDialog("Este número já existe e pertence a categoria " + optional.get().getNome() + "!" + 
                "Deseja substituir os dados existentes por estes?");
            if (result == JOptionPane.YES_OPTION) {
                model.getCategoriaContabil().setId(optional.get().getId());
                return false;
            }
            return true;
        }
        return false;
    }

    private static boolean numeroValido(FormModel model) { 
        return model.getNumeroEtiqueta().startsWith(Integer.toString(model.getFluxoCaixa().getValue() + 1)); 
    }
    
}
