package com.santacarolina.areas.contato.common;

import java.util.Optional;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

import com.santacarolina.dao.ContatoDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.Contato;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ValidatorViolations;

public class ContatoValidator {

    private final static int DOC_REPLACED = 0;
    private final static int DOC_NOT_REPLACED = 1;
    private final static int DOC_NOT_FOUND = 2;

    public static boolean validate(FormModel model) throws FetchFailException {
        if (StringUtils.isBlank(model.getName())) {
            ValidatorViolations.violateEmptyFields("Nome");
            return false;
        } else if (model.isDocsEnabled() && model.getContato().isEmptyDocuments()) {
            ValidatorViolations.violateEmptyFields("Documentos");
            return false;
        } else if (model.isCnpjInvalidFormat()) {
            ValidatorViolations.violateInvalidFields("CNPJ");
            return false;
        } else if (model.isCpfInvalidFormat()) {
            ValidatorViolations.violateInvalidFields("CPF");
            return false;
        } else if (model.isIeInvalidFormat()) {
            ValidatorViolations.violateInvalidFields("IE");
            return false;
        }

        if (nameExists(model))
            return false;
        if (model.isDocsEnabled() && docExists(model))
            return false;

        return true;
    }

    private static boolean docExists(FormModel model) throws FetchFailException {
        ContatoDAO dao = new ContatoDAO();

        if (cpfExists(model, dao) == DOC_NOT_REPLACED)
            return true;
        else if (cnpjExists(model, dao) == DOC_NOT_REPLACED)
            return true;
        else if (ieExists(model, dao) == DOC_NOT_REPLACED)
            return true;
        else
            return false;
    }

    private static int cpfExists(FormModel model, ContatoDAO dao) throws FetchFailException {
        if (StringUtils.isBlank(model.getCpf()))
            return DOC_NOT_REPLACED;
        Optional<Contato> optional = dao.findByCpf(model.getContato().getCpf());
        if (optional.isPresent()) {
            Contato contato = optional.get();
            if (contato.getId() == model.getContato().getId())
                return DOC_NOT_FOUND;
            if (model.getContato().getId() != 0) {
                ValidatorViolations.violateRecordExists("Este CPF pertece a " + contato.getNome() + "!");
                return DOC_NOT_REPLACED;
            }
            int result = OptionDialog.showReplaceDialog("Este CPF pertece a " + contato.getNome() + "!");
            if (result == JOptionPane.YES_OPTION) {
                model.getContato().setId(optional.get().getId());
                return DOC_REPLACED;
            }
            return DOC_NOT_REPLACED;
        }
        return DOC_NOT_FOUND;
    }

    private static int cnpjExists(FormModel model, ContatoDAO dao) throws FetchFailException {
        if (StringUtils.isBlank(model.getCnpj()))
            return DOC_NOT_FOUND;
        Optional<Contato> optional = dao.findByCnpj(model.getContato().getCnpj());
        if (optional.isPresent()) {
            Contato contato = optional.get();
            if (contato.getId() == model.getContato().getId())
                return DOC_NOT_FOUND;
            if (model.getContato().getId() != 0) {
                ValidatorViolations.violateRecordExists("Este CNPJ pertece a " + contato.getNome() + "!");
                return DOC_NOT_REPLACED;
            }
            int result = OptionDialog.showReplaceDialog("Este CNPJ pertece a " + contato.getNome() + "!");
            if (result == JOptionPane.YES_OPTION) {
                model.getContato().setId(optional.get().getId());
                return DOC_REPLACED;
            }
            return DOC_NOT_REPLACED;
        }
        return DOC_NOT_FOUND;
    }

    private static int ieExists(FormModel model, ContatoDAO dao) throws FetchFailException {
        if (StringUtils.isBlank(model.getIe()))
            return DOC_NOT_FOUND;
        Optional<Contato> optional = dao.findByIe(model.getContato().getIe());
        if (optional.isPresent()) {
            Contato contato = optional.get();
            if (contato.getId() == model.getContato().getId())
                return DOC_NOT_FOUND;
            if (model.getContato().getId() != 0) {
                ValidatorViolations.violateRecordExists("Este IE pertece a " + contato.getNome() + "!");
                return DOC_NOT_REPLACED;
            }
            int result = OptionDialog.showReplaceDialog("Este IE pertece a " + contato.getNome() + "!");
            if (result == JOptionPane.YES_OPTION) {
                model.getContato().setId(optional.get().getId());
                return DOC_REPLACED;
            }
            return DOC_NOT_REPLACED;
        }
        return DOC_NOT_FOUND;
    }

    private static boolean nameExists(FormModel model) throws FetchFailException {
        Optional<Contato> optional = new ContatoDAO().findByNome(model.getName());
        if (optional.isPresent()) {
            if (optional.get().getId() == model.getContato().getId())
                return false;
            if (model.getContato().getId() != 0) {
                ValidatorViolations.violateRecordExists("Já existe um contato com este nome!");
                return true;
            }
            int result = OptionDialog.showReplaceDialog("Já existe um contato com este nome!");
            if (result == JOptionPane.YES_OPTION) {
                model.getContato().setId(optional.get().getId());
                return false;
            }
            return true;
        }
        return false;
    }

}
